package kr.kro.oneaclo.www.Repository.Board.Search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import kr.kro.oneaclo.www.Entity.Board.Board;
import kr.kro.oneaclo.www.Entity.Board.QBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {
    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {
        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.or(board.title.contains("11")); // title like

        booleanBuilder.or(board.content.contains("11")); // content like

        query.where(booleanBuilder);
        query.where(board.bno.gt(0));

        query.where(board.title.contains("1"));
        //paging
        Objects.requireNonNull(this.getQuerydsl()).applyPagination(pageable,query);
        List<Board> list = query.fetch();
        long count = query.fetchCount();
        return null;
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);

        //search
        if((types != null && types.length > 0) && keyword != null) {
            //검색 조건 키워드

            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for(String type: types) {
                switch (type) {
                    case "title":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "content":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "writer":
                        booleanBuilder.or(board.writer.id.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);
        }
        query.where(board.bno.gt(0));

        //paging
        Objects.requireNonNull(this.getQuerydsl()).applyPagination(pageable,query);
        List<Board> list = query.fetch();
        long count = query.fetchCount();
        return new PageImpl<>(list,pageable,count);
    }
}
