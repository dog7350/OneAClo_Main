package kr.kro.oneaclo.www.Repository.Board;

import kr.kro.oneaclo.www.Entity.Board.Board;
import kr.kro.oneaclo.www.Entity.Board.BoardCmt;
import kr.kro.oneaclo.www.Entity.Board.IdClass.BoardCmtId;
import kr.kro.oneaclo.www.Repository.Board.Search.BoardCmtSearch;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BoardCmtRepository extends JpaRepository<BoardCmt, BoardCmtId>, BoardCmtSearch {
    List<BoardCmt> findByBno(Board bno);
    List<BoardCmt> findAllByBno(Board bno, Pageable pageable);
}
