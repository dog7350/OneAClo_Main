package kr.kro.oneaclo.www.Service.Board;

import kr.kro.oneaclo.www.DTO.Board.BoardCmtDTO;
import kr.kro.oneaclo.www.Entity.Board.Board;
import kr.kro.oneaclo.www.Entity.Board.BoardCmt;
import kr.kro.oneaclo.www.Entity.Board.IdClass.BoardCmtId;
import kr.kro.oneaclo.www.Repository.Board.BoardCmtRepository;
import kr.kro.oneaclo.www.Repository.Board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardCmtServiceImpl implements BoardCmtService{

    private final BoardCmtRepository boardCmtRepository;
    private final BoardRepository boardRepository;

    public void CmtSave(BoardCmtDTO boardCmtDTO) {
        BoardCmt boardCmt = BoardCmt.builder()
                .content(boardCmtDTO.getContent())
                .firsttime(null)
                .lasttime(null)
                .writer(boardCmtDTO.getWriter())
                .bno(boardCmtDTO.getBno())
                .build();

        boardCmtRepository.save(boardCmt);
    }
    public void CmtModify(BoardCmtDTO boardCmtDTO) {
        Optional<BoardCmt> result = boardCmtRepository.findById(new BoardCmtId(boardCmtDTO.getBno().getBno(),boardCmtDTO.getCno()));
        BoardCmt boardCmt = result.orElseThrow();
        boardCmt.change(boardCmtDTO.getContent(),LocalDateTime.now());
        boardCmtRepository.save(boardCmt);
    }
    public List<BoardCmt> BoardCmtInfo(int bno) {
        Optional<Board> result = boardRepository.findByBno(bno);
        Board board = result.orElseThrow();
        Pageable pageable = PageRequest.of(0,100,Sort.by("cno").descending());
        return boardCmtRepository.findAllByBno(board,pageable);
    }
    public void CmtDel(int bno, int cno) {
        boardCmtRepository.deleteById(new BoardCmtId(bno,cno));
    }
}
