package kr.kro.oneaclo.www.Repository.Board;

import kr.kro.oneaclo.www.Entity.Board.Board;
import kr.kro.oneaclo.www.Repository.Board.Search.BoardSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Integer>, BoardSearch {
    Optional<Board> findByBno(int bno);
}
