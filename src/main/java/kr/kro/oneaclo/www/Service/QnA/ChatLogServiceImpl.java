package kr.kro.oneaclo.www.Service.QnA;

import kr.kro.oneaclo.www.Entity.Log.ChatLog;
import kr.kro.oneaclo.www.Repository.QnA.ChatLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
