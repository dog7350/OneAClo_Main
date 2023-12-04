package kr.kro.oneaclo.www.Repository.Shop;

import kr.kro.oneaclo.www.Entity.Shop.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByPname(String pname);

    Page<Product> findAllByOrderByPnoDesc(Pageable pageable);
    Page<Product> findByPnameLikeOrderByPnoDesc(Pageable pageable, String pname);
    Page<Product> findByBcategoryLikeOrderByPnoDesc(Pageable pageable, String bcategory);
    Page<Product> findByMcategoryLikeOrderByPnoDesc(Pageable pageable, String mcategory);
    Page<Product> findByScategoryLikeOrderByPnoDesc(Pageable pageable, String scategory);

    Optional<Product> findByPno(int pno);
    void deleteByPno(int pno);

    @Query(value = "SELECT * FROM (SELECT * FROM product ORDER BY inquiry DESC) WHERE ROWNUM <= 6", nativeQuery = true)
    List<Product> mainPopularityList();

    @Query(value = "SELECT * FROM (SELECT * FROM product ORDER BY time DESC) WHERE ROWNUM <= 6", nativeQuery = true)
    List<Product> mainNewList();

    @Query(value = "SELECT * FROM (SELECT * FROM product WHERE mcategory=:category ORDER BY inquiry DESC) WHERE ROWNUM <= 3", nativeQuery = true)
    List<Product> recomList(String category);
}
