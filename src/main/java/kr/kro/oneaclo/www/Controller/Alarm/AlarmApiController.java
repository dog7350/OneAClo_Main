package kr.kro.oneaclo.www.Controller.Alarm;

import kr.kro.oneaclo.www.DTO.Alarm.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class AlarmApiController {

    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/order")
    public void addOrder(@RequestBody MessageDTO dto) {
        messagingTemplate.convertAndSend("/realSend/order/admin", dto);
    }

    @MessageMapping("/qna")
    public void addQna(@RequestBody MessageDTO dto) {
        messagingTemplate.convertAndSend("/realSend/qna/admin", dto);
    }

}