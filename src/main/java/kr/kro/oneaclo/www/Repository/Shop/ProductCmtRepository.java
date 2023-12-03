package kr.kro.oneaclo.www.Repository.Shop;

import kr.kro.oneaclo.www.Entity.Shop.Product;
import kr.kro.oneaclo.www.Entity.Shop.ProductCmt;
import kr.kro.oneaclo.www.Entity.Shop.ProductCmtId;
import kr.kro.oneaclo.www.Repository.Shop.search.ProductCmtSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCmtRepository extends JpaRepository<ProductCmt, ProductCmtId>, ProductCmtSearch {
    List<ProductCmt> findAllByPno(Product pno);
}
