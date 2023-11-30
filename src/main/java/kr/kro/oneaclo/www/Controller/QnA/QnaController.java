package kr.kro.oneaclo.www.Controller.QnA;

import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.Common.TokenProcess;
import kr.kro.oneaclo.www.Service.QnA.ChatLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/qna")
@RequiredArgsConstructor
public class QnaController {
    private final TokenProcess tokenProcess;
    private final ChatLogService chatLogService;

    private final String[] arr = {"id", "nick", "profile", "auth"};
    private String auth; // 현재 유저의 권한
    private String userId; // 현재 유저의 ID

    private Map<String, String> TokenList(String token) {
        Map<String, String> map = new HashMap<>();

        if (token != null)
            for (String str : arr) map.put(str, tokenProcess.getMembersToken(token, str));

        auth = map.get("auth");
        userId = map.get("id");

        return map;
    }

    @GetMapping("/chat")
    public String qnaMain(HttpSession session, Model model, @RequestParam String id) {
        String token = (String) session.getAttribute("UserInfo");
        Map<String, String> user = TokenList(token);

        for (String str : arr) model.addAttribute(str, user.get(str));

        if (auth.equals("c") && !id.equals(userId)) {
            model.addAttribute("url", "/");
            model.addAttribute("msg", "정상적인 경로가 아닙니다.");
            return "views/common/message";
        }

        model.addAttribute("site", id);
        model.addAttribute("logs", chatLogService.ChatLogList(id));

        return "views/qna/chat";
    }
}
