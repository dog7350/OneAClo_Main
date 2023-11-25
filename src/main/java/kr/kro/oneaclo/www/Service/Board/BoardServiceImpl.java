package kr.kro.oneaclo.www.Service.Board;

import kr.kro.oneaclo.www.DTO.Board.BoardDTO;
import kr.kro.oneaclo.www.DTO.Board.Page.PageRequestDTO;
import kr.kro.oneaclo.www.DTO.Board.Page.PageResponseDTO;
import kr.kro.oneaclo.www.Entity.Board.Board;
import kr.kro.oneaclo.www.Entity.Board.BoardCmt;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import kr.kro.oneaclo.www.Repository.Board.BoardCmtRepository;
import kr.kro.oneaclo.www.Repository.Board.BoardRepository;
import kr.kro.oneaclo.www.Repository.Mypage.MembersRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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


    public void BoardSave(String BoardUser,String title,String content,String btype) {
        Optional<Members> result = membersRepository.findById(BoardUser);
        Members members = result.orElseThrow();
        Board board = Board.builder()
                .writer(members)
                .firsttime(null)
                .lasttime(null)
                .btype(btype)
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
    public void BoardModify(BoardDTO boardDTO) {
        Optional<Board> result = boardRepository.findByBno(boardDTO.getBno());
        Board board = result.orElseThrow();
        board.BoardChange(boardDTO.getTitle(),boardDTO.getContent(), LocalDateTime.now());
        boardRepository.save(board);
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
