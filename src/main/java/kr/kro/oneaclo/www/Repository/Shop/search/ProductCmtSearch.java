package kr.kro.oneaclo.www.Repository.Shop.search;

import kr.kro.oneaclo.www.Entity.Shop.ProductCmt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductCmtSearch {
    Page<ProductCmt> SearchProductCmt(Pageable pageable,int pno);
}
