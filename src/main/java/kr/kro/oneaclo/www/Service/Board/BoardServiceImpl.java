package kr.kro.oneaclo.www.Service.Board;

import kr.kro.oneaclo.www.DTO.Board.BoardDTO;
import kr.kro.oneaclo.www.DTO.Page.PageRequestDTO;
import kr.kro.oneaclo.www.DTO.Page.PageResponseDTO;
import kr.kro.oneaclo.www.Entity.Board.Board;
import kr.kro.oneaclo.www.Entity.Board.BoardReport;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import kr.kro.oneaclo.www.Repository.Board.BoardReportRepository;
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
    private final BoardReportRepository boardReportRepository;
    private final MembersRepository membersRepository;
    private final ModelMapper modelMapper;
    
    //생성
    public int BoardSave(String BoardUser,String title,String content,String btype) {
        Optional<Members> result = membersRepository.findById(BoardUser);
        Members members = result.orElseThrow();

        Board board = Board.builder()
                .writer(members)
                .firsttime(null)
                .lasttime(null)
                .btype(btype)
                .inquiry(0)
                .step(0)
                .indent(0)
                .title(title)
                .content(content)
                .build();

        boardRepository.save(board);
        return board.getBno();
    }

    public void BoardReport(String id, int bno) {
        Optional<Board> BoardResult = boardRepository.findByBno(bno);
        Board board = BoardResult.orElseThrow();
        Optional<Members> MemberResult = membersRepository.findById(id);
        Members members = MemberResult.orElseThrow();
        BoardReport boardReport = BoardReport.builder()
                .reporter(members)
                .bno(board)
                .build();
        boardReportRepository.save(boardReport);
    }

    public void InqUp(int bno) {
        Optional<Board> result = boardRepository.findByBno(bno);
        Board board = result.orElseThrow();
        board.InqUp();
        boardRepository.save(board);
    }

    public void BoardReplySave(BoardDTO boardDTO, String id) {
        Optional<Members> result = membersRepository.findById(id);
        Members members = result.orElseThrow();
        Board board = Board.builder()
                .writer(members)
                .bnogroup(boardDTO.getBnogroup())
                .step(boardDTO.getStep()+1)
                .indent(boardDTO.getIndent()+1)
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .firsttime(null)
                .lasttime(null)
                .build();
        boardRepository.save(board);
    }

    @Override
    public void BoardGroup(int bno,String btype) {
        Optional<Board> result = boardRepository.findByBno(bno);
        Board board = result.orElseThrow();
        board.GroupUp(bno,LocalDateTime.now(),LocalDateTime.now(),btype);
        boardRepository.save(board);
    }

    //조회
    public BoardDTO BoardInfo(int bno) {
        Optional<Board> result = boardRepository.findByBno(bno);
        Board board = result.orElseThrow();
        return modelMapper.map(board, BoardDTO.class);
    }

    @Override
    public BoardDTO GetBoard(int bno) {
        Optional<Board> result = boardRepository.findByBno(bno);
        Board board = result.orElseThrow();
        return modelMapper.map(board, BoardDTO.class);
    }
    @Override
    public int StepMax() {
        return boardRepository.StepMax();
    }

    //수정
    public void BoardModify(BoardDTO boardDTO) {
        Optional<Board> result = boardRepository.findByBno(boardDTO.getBno());
        Board board = result.orElseThrow();
        board.BoardChange(boardDTO.getTitle(),boardDTO.getContent(), LocalDateTime.now());
        boardRepository.save(board);
    }
    public void BoardDel(int bno) {
        boardRepository.deleteById(bno);
    }

    //페이징
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

    public List<Board> MainNewNormalList() { return boardRepository.mainNewNormalList(); }
    public List<Board> MainNotificationList() { return boardRepository.mainNotificationList(); }

}
