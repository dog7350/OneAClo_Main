package kr.kro.oneaclo.www.Service.Shop;

import kr.kro.oneaclo.www.DTO.Shop.OrdersDTO;
import kr.kro.oneaclo.www.Entity.Shop.Orders;
import org.springframework.data.domain.Page;

public interface OrdersService {
    void OrderSave(OrdersDTO dto);

    Page<Orders> AllOrdersPage(int pageNumber, int elementCount);
    Page<Orders> SearchOrdersPage(int pageNumber, int elementCount, String searchOption, String searchValue);
    Page<Orders> UserOrdersPage(int pageNumber, int elementCount, String id);

    void orderStatusUpdate(int ono, String status);
}
