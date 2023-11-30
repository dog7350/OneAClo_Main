package kr.kro.oneaclo.www.Repository.QnA;

import kr.kro.oneaclo.www.Entity.QnA.ChatLog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ChatLogRepository extends ReactiveMongoRepository<ChatLog, String> {
    Flux<ChatLog> findAllBySiteOrderByCreatedAt(String id);
}
