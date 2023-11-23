package kr.kro.oneaclo.www.Service.Board;

import kr.kro.oneaclo.www.DTO.Board.BoardCmtDTO;
import kr.kro.oneaclo.www.DTO.Board.BoardDTO;
import kr.kro.oneaclo.www.DTO.Board.Page.PageRequestDTO;
import kr.kro.oneaclo.www.DTO.Board.Page.PageResponseDTO;
import kr.kro.oneaclo.www.Entity.Board.Board;
import kr.kro.oneaclo.www.Entity.Board.BoardCmt;
import kr.kro.oneaclo.www.Entity.Board.IdClass.BoardCmtId;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import kr.kro.oneaclo.www.Repository.Board.BoardCmtRepository;
import kr.kro.oneaclo.www.Repository.Board.BoardRepository;
import kr.kro.oneaclo.www.Repository.Mypage.MembersRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final BoardCmtRepository boardCmtRepository;
    private final MembersRepository membersRepository;
    private final ModelMapper modelMapper;
    public List<Board> BoardGetList() {
        return boardRepository.findAll();
    }

    public void BoardSave(String[] BoardUser,String title,String content) {
        Optional<Members> result = membersRepository.findById(BoardUser[0]);
        Members members = result.orElseThrow();
        Board board = Board.builder()
                .writer(members)
                .firsttime(null)
                .lasttime(null)
                .btype(BoardUser[1])
                .title(title)
                .content(content)
                .build();

        boardRepository.save(board);
    }
    public BoardDTO BoardInfo(int bno) {
        Optional<Board> result = boardRepository.findByBno(bno);
        Board board = result.orElseThrow();
        return modelMapper.map(board, BoardDTO.class);
    }
    public List<BoardCmt> BoardCmtInfo(int bno) {
        Optional<Board> result = boardRepository.findByBno(bno);
        Board board = result.orElseThrow();
        return boardCmtRepository.findByBno(board);
    }

    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");


        Page<Board> result = boardRepository.searchAll(types,keyword,pageable);
        List<BoardDTO> dtoList = result.getContent().stream().map(board -> modelMapper.map(board,BoardDTO.class)).collect(Collectors.toList());

        return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }
}
