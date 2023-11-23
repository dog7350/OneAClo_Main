package kr.kro.oneaclo.www.Service.QnA;

import kr.kro.oneaclo.www.Entity.QnA.ChatLog;
import kr.kro.oneaclo.www.Repository.QnA.ChatLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatLogServiceImpl implements ChatLogService {
    private final ChatLogRepository chatLogRepository;

    public List<ChatLog> ChatLogList(String id) {
        List<ChatLog> list = chatLogRepository.findAllBySiteOrderByCreatedAt(id).collectList().block();
        return list;
    }
}
