package kr.kro.oneaclo.www.Repository.Board.Search;

import com.querydsl.core.annotations.QueryEntity;
import com.querydsl.core.types.QBean;
import kr.kro.oneaclo.www.Entity.Board.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {
    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {

        return null;
    }
}
