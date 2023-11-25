package kr.kro.oneaclo.www.Repository.Board;

import kr.kro.oneaclo.www.Entity.Board.Board;
import kr.kro.oneaclo.www.Entity.Board.BoardCmt;
import kr.kro.oneaclo.www.Entity.Board.IdClass.BoardCmtId;
import kr.kro.oneaclo.www.Repository.Board.Search.BoardSearch;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface BoardCmtRepository extends JpaRepository<BoardCmt, BoardCmtId>, BoardSearch {
    List<BoardCmt> findByBno(Board bno);
    List<BoardCmt> findAllByBno(Board bno, Pageable pageable);
}
