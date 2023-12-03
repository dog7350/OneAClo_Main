package kr.kro.oneaclo.www.Repository.Shop.search;

import com.querydsl.jpa.JPQLQuery;
import kr.kro.oneaclo.www.Entity.Shop.ProductCmt;
import kr.kro.oneaclo.www.Entity.Shop.QProductCmt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;

public class ProductCmtSearchImpl extends QuerydslRepositorySupport implements ProductCmtSearch {
    public ProductCmtSearchImpl() {
        super(ProductCmt.class);
    }
    @Override
    public Page<ProductCmt> SearchProductCmt(Pageable pageable, int pno) {
        QProductCmt productCmt = QProductCmt.productCmt;
        JPQLQuery<ProductCmt> query = from(productCmt);

        query.where(productCmt.pno.pno.eq(pno));
        query.orderBy(productCmt.cnogroup.desc());
        query.orderBy(productCmt.step.asc());
        query.orderBy(productCmt.cno.desc());

        Objects.requireNonNull(Objects.requireNonNull(this.getQuerydsl()).applyPagination(pageable,query));
        List<ProductCmt> list = query.fetch();
        long count = query.fetchCount();
        return new PageImpl<>(list,pageable,count);
    }
}
