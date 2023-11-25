package kr.kro.oneaclo.www.Repository.Board.Search;

import kr.kro.oneaclo.www.Entity.Board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch {

    Page<Board> search1(Pageable pageable);
    Page<Board> searchAll(String[] types,String Keyword,Pageable pageable);
}
