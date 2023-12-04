package kr.kro.oneaclo.www.Repository.Board;

import kr.kro.oneaclo.www.Entity.Board.Board;
import kr.kro.oneaclo.www.Repository.Board.Search.BoardSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Integer>, BoardSearch {
    Optional<Board> findByBno(int bno);
    Optional<Board> findBoardByBno(int bno);

    @Query(value = "SELECT * FROM (SELECT * FROM board WHERE btype = 'f' AND step = 0 ORDER BY firsttime DESC) WHERE ROWNUM <= 10", nativeQuery = true)
    List<Board> mainNewNormalList();

    @Query(value = "SELECT * FROM (SELECT * FROM board WHERE btype = 'a' AND step = 0 ORDER BY firsttime DESC) WHERE ROWNUM <= 5", nativeQuery = true)
    List<Board> mainNotificationList();

}
