package kr.kro.oneaclo.www.Service.Shop;

import kr.kro.oneaclo.www.Repository.Shop.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;
}
