package kr.kro.oneaclo.www.Repository.Shop;

import kr.kro.oneaclo.www.Entity.Shop.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, String> {
}
