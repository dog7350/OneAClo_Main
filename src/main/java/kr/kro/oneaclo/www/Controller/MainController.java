package kr.kro.oneaclo.www.Controller;

import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.Common.TokenProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final TokenProcess tokenProcess;

    private final String[] arr = {"id", "nick", "profile", "auth"};

    private Map<String, String> TokenList(String token) {
        Map<String, String> map = new HashMap<>();

        if (token != null)
            for (String str : arr) map.put(str, tokenProcess.getMembersToken(token, str));

        return map;
    }

    @GetMapping("/")
    public String Home(HttpSession session, Model model) {
        String token = (String) session.getAttribute("UserInfo");

        if (token != null) {
            Map<String, String> user = TokenList(token);

            for (String str : arr) model.addAttribute(str, user.get(str));
        }

        return "views/index";
    }

}
