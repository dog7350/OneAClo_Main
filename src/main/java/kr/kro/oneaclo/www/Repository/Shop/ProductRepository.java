package kr.kro.oneaclo.www.Repository.Shop;

import kr.kro.oneaclo.www.Entity.Shop.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByPname(String pname);

    Page<Product> findAllByOrderByPnoDesc(Pageable pageable);
    Page<Product> findByPnameLikeOrderByPnoDesc(Pageable pageable, String pname);
    Page<Product> findByBcategoryLikeOrderByPnoDesc(Pageable pageable, String bcategory);
    Page<Product> findByMcategoryLikeOrderByPnoDesc(Pageable pageable, String mcategory);
    Page<Product> findByScategoryLikeOrderByPnoDesc(Pageable pageable, String scategory);

    Optional<Product> findByPno(int pno);
}
