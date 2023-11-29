package kr.kro.oneaclo.www.Repository.Board.Search;

import com.querydsl.jpa.JPQLQuery;
import kr.kro.oneaclo.www.Entity.Board.Board;
import kr.kro.oneaclo.www.Entity.Board.BoardCmt;
import kr.kro.oneaclo.www.Entity.Board.QBoard;
import kr.kro.oneaclo.www.Entity.Board.QBoardCmt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;

public class BoardCmtSearchImpl extends QuerydslRepositorySupport implements BoardCmtSearch {
    public BoardCmtSearchImpl() {
        super(BoardCmt.class);
    }
    @Override
    public Page<BoardCmt> searchCmt(Pageable pageable,int bno) {
        QBoardCmt boardCmt = QBoardCmt.boardCmt;
        QBoard board = QBoard.board;
        JPQLQuery<BoardCmt> query = from(boardCmt);

        query.where(boardCmt.bno.bno.eq(bno));
        query.orderBy(boardCmt.cnogroup.desc());
        query.orderBy(boardCmt.step.asc());
        query.orderBy(boardCmt.cno.desc());

        Objects.requireNonNull(this.getQuerydsl()).applyPagination(pageable,query);
        List<BoardCmt> list = query.fetch();
        long count =  query.fetchCount();
        return new PageImpl<>(list,pageable,count);
    }
}
