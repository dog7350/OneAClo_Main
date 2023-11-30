package kr.kro.oneaclo.www.Repository.Logs;

import kr.kro.oneaclo.www.Entity.Log.OrderLog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface OrderLogRepository extends ReactiveMongoRepository<OrderLog, String> {
}
