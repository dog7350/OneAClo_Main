package kr.kro.oneaclo.www.Repository.Shop;

import kr.kro.oneaclo.www.Entity.Mypage.Members;
import kr.kro.oneaclo.www.Entity.Shop.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface OrdersRepository extends JpaRepository<Orders, String> {
    Orders findByOno(int ono);

    Page<Orders> findAllByOrderByOtimeDesc(Pageable pageable);

    Page<Orders> findByOid_idLikeOrderByOtimeDesc(Pageable pageable, String oid);
    Page<Orders> findByOnoOrderByOtimeDesc(Pageable pageable, int ono);
    Page<Orders> findByPnoOrderByOtimeDesc(Pageable pageable, int pno);
}
