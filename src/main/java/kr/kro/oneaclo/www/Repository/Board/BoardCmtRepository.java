package kr.kro.oneaclo.www.Repository.Board;

import kr.kro.oneaclo.www.Entity.Board.Board;
import kr.kro.oneaclo.www.Entity.Board.BoardCmt;
import kr.kro.oneaclo.www.Entity.Board.IdClass.BoardCmtId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface BoardCmtRepository extends JpaRepository<BoardCmt, BoardCmtId> {
    List<BoardCmt> findByBno(Board bno);
}
