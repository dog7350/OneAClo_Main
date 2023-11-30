package kr.kro.oneaclo.www.Service.Logs;

import kr.kro.oneaclo.www.DTO.Log.LogDTO;
import kr.kro.oneaclo.www.DTO.Shop.OrdersDTO;

public interface OrderLogService {
    void OrderLogSave(OrdersDTO order, LogDTO log);
}
