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

    //Override 필수
    @Override
    public Page<Board> search1(Pageable pageable) {
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
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.id.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);
        }
        query.where(board.bno.gt(0));
        query.orderBy(board.bnogroup.desc());
        query.orderBy(board.step.asc());
        query.orderBy(board.btype.asc());



        //paging
        Objects.requireNonNull(this.getQuerydsl()).applyPagination(pageable,query);
        List<Board> list = query.fetch();
        long count =  query.fetchCount();
        return new PageImpl<>(list,pageable,count);
    }

    @Override
    public Integer StepMax() {
        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);

        int num = query
                .select(board.step.max())
                .from(board)
                .fetchOne();
        return num;
    }

}
