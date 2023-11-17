package kr.kro.oneaclo.www.Repository.Board;

import kr.kro.oneaclo.www.Entity.Board.BoardCmt;
import kr.kro.oneaclo.www.Entity.Board.IdClass.BoardCmtId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCmtRepository extends JpaRepository<BoardCmt, BoardCmtId> {

}
