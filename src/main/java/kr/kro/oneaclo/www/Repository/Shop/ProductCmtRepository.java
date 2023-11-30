package kr.kro.oneaclo.www.Repository.Shop;

import kr.kro.oneaclo.www.Entity.Shop.ProductCmt;
import kr.kro.oneaclo.www.Entity.Shop.ProductCmtId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCmtRepository extends JpaRepository<ProductCmt, ProductCmtId> {
}
