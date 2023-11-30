package kr.kro.oneaclo.www.Service.Logs;

import kr.kro.oneaclo.www.DTO.Log.LogDTO;
import kr.kro.oneaclo.www.Entity.Shop.Product;

public interface InquiryLogService {
    void InquiryLogSave(LogDTO log, Product product);
}
