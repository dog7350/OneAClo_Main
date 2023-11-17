package kr.kro.oneaclo.www.Controller;

import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.Common.TokenProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final TokenProcess tokenProcess;

    @GetMapping("/")
    public String Home(HttpSession session, Model model) {
        String token = (String) session.getAttribute("UserInfo");

        if (token != null) {
            String[] arr = {"id", "nick", "profile", "auth"};

            for (String list : arr) model.addAttribute(list, tokenProcess.getMembersToken(token, list));
        }

        return "views/index";
    }

}
