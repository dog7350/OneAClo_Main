package kr.kro.oneaclo.www.Service.QnA;

import kr.kro.oneaclo.www.Entity.QnA.ChatLog;

import java.util.List;

public interface ChatLogService {
    List<ChatLog> ChatLogList(String id);
}
