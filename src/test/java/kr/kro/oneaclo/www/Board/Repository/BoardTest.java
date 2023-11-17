package kr.kro.oneaclo.www.Board.Repository;

import kr.kro.oneaclo.www.Entity.Board.Board;
import kr.kro.oneaclo.www.Entity.Board.BoardCmt;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import kr.kro.oneaclo.www.Repository.Board.BoardCmtRepository;
import kr.kro.oneaclo.www.Repository.Board.BoardRepository;
import kr.kro.oneaclo.www.Repository.Mypage.MembersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class BoardTest {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    MembersRepository membersRepository;
    @Autowired
    BoardCmtRepository boardCmtRepository;
    @Test
    public void BoardCmtSave() {
        Optional<Board> result = boardRepository.findByBno(3);
        Board board = result.orElseThrow();

        BoardCmt boardCmt = BoardCmt.builder()
                .bnogroup(board)
                .cno(2)
                .writer("테스터")
                .content("내용")
                .firsttime(null)
                .lasttime(null)
                .cnogroup(3)
                .step(0)
                .indent(0)
                .build();

        boardCmtRepository.save(boardCmt);

    }
}
