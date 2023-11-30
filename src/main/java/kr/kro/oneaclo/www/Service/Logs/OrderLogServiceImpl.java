package kr.kro.oneaclo.www.Service.Logs;

import kr.kro.oneaclo.www.DTO.Log.LogDTO;
import kr.kro.oneaclo.www.DTO.Shop.OrdersDTO;
import kr.kro.oneaclo.www.Entity.Log.OrderLog;
import kr.kro.oneaclo.www.Repository.Logs.OrderLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLogServiceImpl implements OrderLogService {
    private final OrderLogRepository orderLogRepository;

    public void OrderLogSave(OrdersDTO order, LogDTO log) {
        OrderLog orderLog = OrderLog.builder()
                .pno(order.getPno())
                .bcate(order.getBcategory())
                .mcate(order.getMcategory())
                .scate(order.getScategory())
                .age(log.getAge())
                .gender(log.getGender())
                .count(log.getCount())
                .build();

        orderLogRepository.save(orderLog).subscribe();
    }
}
