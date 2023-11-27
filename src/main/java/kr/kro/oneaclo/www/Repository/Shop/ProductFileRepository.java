package kr.kro.oneaclo.www.Repository.Shop;

import kr.kro.oneaclo.www.Entity.Shop.Product;
import kr.kro.oneaclo.www.Entity.Shop.ProductFile;
import kr.kro.oneaclo.www.Entity.Shop.ProductFileId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductFileRepository extends JpaRepository<ProductFile, ProductFileId> {
    List<ProductFile> findAllByPno(Product pno);
}
