package kr.kro.oneaclo.www.Service.Shop;

import kr.kro.oneaclo.www.DTO.Shop.OrdersDTO;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import kr.kro.oneaclo.www.Entity.Shop.Orders;
import kr.kro.oneaclo.www.Entity.Shop.Product;
import kr.kro.oneaclo.www.Repository.Shop.OrdersRepository;
import kr.kro.oneaclo.www.Repository.Shop.ProductRepository;
import kr.kro.oneaclo.www.Service.Mypage.MembersService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {
    private final MembersService membersService;
    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;

    public void OrderSave(OrdersDTO dto) {
        Product product = productRepository.findByPno(dto.getPno()).get();
        Members members = membersService.SearchUser(dto.getOid());
        Orders orders = Orders.builder()
                                .pno(product)
                                .oid(members)
                                .email(dto.getEmail())
                                .ocount(dto.getOcount())
                                .totalprice(dto.getTotalprice())
                                .ouid(dto.getOuid())
                                .receiver(dto.getReceiver())
                                .phone(dto.getPhone())
                                .zipcode(dto.getZipcode())
                                .address(dto.getAddress())
                                .detailaddr(dto.getDetailaddr())
                                .build();

        ordersRepository.save(orders);
    }

    public Page<Orders> AllOrdersPage(int pageNumber, int elementCount) {
        Pageable pageable = PageRequest.of(pageNumber, elementCount);

        return ordersRepository.findAllByOrderByOtimeDesc(pageable);
    }

    public Page<Orders> SearchOrdersPage(int pageNumber, int elementCount, String searchOption, String searchValue) {
        Pageable pageable = PageRequest.of(pageNumber, elementCount);
        Page<Orders> pages = null;

        String value = "%" + searchValue + "%";

        if (searchOption.equals("oid")) pages = ordersRepository.findByOid_idLikeOrderByOtimeDesc(pageable, value);
        else if (searchOption.equals("ono")) pages = ordersRepository.findByOnoOrderByOtimeDesc(pageable, Integer.parseInt(searchValue));
        else if (searchOption.equals("pno")) pages = ordersRepository.findByPnoOrderByOtimeDesc(pageable, Integer.parseInt(searchValue));

        return pages;
    }

    public void orderStatusUpdate(int ono, String status) {
        Orders order = ordersRepository.findByOno(ono);

        order.StatusChange(status);
        ordersRepository.save(order);
    }

    public Page<Orders> UserOrdersPage(int pageNumber, int elementCount, String id) {
        Pageable pageable = PageRequest.of(pageNumber, elementCount);

        return ordersRepository.findByOid_idLikeOrderByOtimeDesc(pageable, id);
    }
}
