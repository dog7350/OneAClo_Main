package kr.kro.oneaclo.www.Board.Repository;

import kr.kro.oneaclo.www.Entity.Board.*;
import kr.kro.oneaclo.www.Entity.Board.IdClass.BoardCmtId;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import kr.kro.oneaclo.www.Repository.Board.*;
import kr.kro.oneaclo.www.Repository.Mypage.MembersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class BoardTest {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    MembersRepository membersRepository;
    @Autowired
    BoardCmtRepository boardCmtRepository;
    @Autowired
    BoardFileRepository boardFileRepository;
    @Autowired
    BoardReportRepository boardReportRepository;
    @Autowired
    BoardCmtReportRepository boardCmtReportRepository;

    @Test
    public void BoardSave() {
        Optional<Members> result = membersRepository.findById("test");
        Members members = result.orElseThrow();

        Board board = Board.builder()
                .writer(members)
                .title("제목")
                .content("내용")
                .firsttime(null)
                .lasttime(null)
                .inquiry(1)
                .btype("d")
                .bnogroup(1)
                .step(1)
                .indent(1)
                .build();

        boardRepository.save(board);
    }
    @Test
    public void BoardCmtSave() {
        Optional<Board> result = boardRepository.findByBno(3);
        Board board = result.orElseThrow();

        Optional<Members> result2 = membersRepository.findById("test");
        Members members = result2.orElseThrow();

        BoardCmt boardCmt = BoardCmt.builder()
                .bno(board)
                .writer(members)
                .content("내용")
                .firsttime(null)
                .lasttime(null)
                .cnogroup(3)
                .step(0)
                .indent(0)
                .build();

        boardCmtRepository.save(boardCmt);
    }

    @Test
    public void BoardFileSave() {
        Optional<Board> result = boardRepository.findByBno(3);
        Board board = result.orElseThrow();

        BoardFile boardFile = BoardFile.builder()
                .bno(board)
                .filename("테스트용파일")
                .build();

        boardFileRepository.save(boardFile);
    }

    @Test
    public void BoardReportSave() {
        Optional<Board> result = boardRepository.findByBno(3);
        Board board = result.orElseThrow();

        Optional<Members> result2 = membersRepository.findById("test");
        Members members = result2.orElseThrow();

        BoardReport boardReport = BoardReport.builder()
                .bno(board)
                .reporter(members)
                .build();

        boardReportRepository.save(boardReport);
    }

    @Test
    public void BoardCmtReportSave() {
        Optional<Members> result2 = membersRepository.findById("test");
        Members members = result2.orElseThrow();

        Optional<BoardCmt> result3 = boardCmtRepository.findById(new BoardCmtId(3,1));
        BoardCmt boardCmt = result3.orElseThrow();

        BoardCmtReport boardCmtReport = BoardCmtReport.builder()
                .boardCmt(boardCmt)
                .reporter(members)
                .build();

        boardCmtReportRepository.save(boardCmtReport);
    }
}
