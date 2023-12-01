package kr.kro.oneaclo.www.Service.Logs;

import kr.kro.oneaclo.www.DTO.Log.LogDTO;
import kr.kro.oneaclo.www.Entity.Log.InquiryLog;
import kr.kro.oneaclo.www.Entity.Shop.Product;
import kr.kro.oneaclo.www.Repository.Logs.InquiryLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class InquiryLogServiceImpl implements InquiryLogService {
    private final InquiryLogRepository inquiryLogRepository;

    public void InquiryLogSave(LogDTO log, Product product) {
        InquiryLog inquiryLog = InquiryLog.builder()
                                        .pno(product.getPno())
                                        .bcate(product.getBcategory())
                                        .mcate(product.getMcategory())
                                        .scate(product.getScategory())
                                        .userid(log.getId())
                                        .age(log.getAge())
                                        .gender(log.getGender())
                                        .address(log.getAddress())
                                        .createdAt(new Date())
                                        .build();

        inquiryLogRepository.save(inquiryLog).subscribe();
    }
}
