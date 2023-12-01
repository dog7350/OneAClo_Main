package kr.kro.oneaclo.www.Repository.Board.Search;

import kr.kro.oneaclo.www.Entity.Board.BoardCmt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardCmtSearch {
    Page<BoardCmt> searchCmt(Pageable pageable,int bno);
}
