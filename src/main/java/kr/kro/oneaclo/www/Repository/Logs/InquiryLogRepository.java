package kr.kro.oneaclo.www.Repository.Logs;

import kr.kro.oneaclo.www.Entity.Log.InquiryLog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface InquiryLogRepository extends ReactiveMongoRepository<InquiryLog, String> {
}
