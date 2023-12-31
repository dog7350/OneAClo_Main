package kr.kro.oneaclo.www.Controller.Mypage;


import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.Common.TokenProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

@Controller
@RequestMapping(value = "/mypage")
@RequiredArgsConstructor
public class MemberViewController {
    private final TokenProcess tokenProcess;
    private void UserModelInfo(HttpSession session, Model model, String want) {model.addAttribute(want, tokenProcess.getMembersToken(String.valueOf(session.getAttribute("UserInfo")), want));}
    @GetMapping("/loginform")
    public String loginform(HttpSession session) {
        if(session.getAttribute("UserInfo")==null) {
            return "views/mypage/login/LoginForm";
        }
            return "redirect:/";
    }

    @GetMapping("/joinform")
    public String joinform(HttpSession session) {
        if(session.getAttribute("UserInfo")==null) {
            return "views/mypage/join/JoinForm";
        }
            return "redirect:/";
    }

    @GetMapping("/p/PasswordChange")
    public String PasswordChange() {
        return "views/mypage/info/PasswordChange";
    }
    @GetMapping("/p/PhoneCh")
    public String PhoneCh() {
        return "views/mypage/info/PhoneCh";
    }

    @GetMapping("/p/NickCh")
    public String NickCh() {
        return "views/mypage/info/NickCh";
    }

    @GetMapping("/p/userinfo")
    public String userinfo(HttpSession session, Model model) {
        String[] arr = {"id","pw","name","email","nick","profile","zipcode","address","detailaddr","phone", "auth"};
        for (String list:arr) {
            UserModelInfo(session,model,list);
        }
        return "views/mypage/info/UserInfo";
    }

    @GetMapping("/p/ProfileCh")
    public String ProfileCh() {
        return "views/mypage/info/ProfileCh";
    }

    @GetMapping("/p/AddressCh")
    public String AddressCh() {
        return "views/mypage/info/AddressCh";
    }
    @GetMapping("/BlockUser")
    public String BlockUser(Model model) {
        model.addAttribute("url", "/");
        model.addAttribute("msg", "정지된 아이디 입니다.");
        return "views/common/message";
    }
    @PostMapping("/KeywordSearch")
    public String KeywordSearch(@RequestParam String category,@RequestParam String keyword) {
        return switch (category) {
            case "t" -> "redirect:/board/list?size=10&type=t&keyword=" + URLEncoder.encode(keyword);
            case "w" -> "redirect:/board/list?size=10&type=w&keyword=" + URLEncoder.encode(keyword);
            case "p" -> "redirect:/shop/list?searchOption=pname&searchValue=" + URLEncoder.encode(keyword);
            default -> "redirect:/";
        };

    }
}
