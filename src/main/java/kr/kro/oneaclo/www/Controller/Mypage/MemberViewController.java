package kr.kro.oneaclo.www.Controller.Mypage;

import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.Common.TokenProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MemberViewController {
    private final TokenProcess tokenProcess;
    @GetMapping("/loginform")
    public String loginform(HttpSession session) {
        if(session.getAttribute("UserInfo")==null) {
            return "views/mypage/login/LoginForm";
        }
            return "redirect:http://www.oneaclo.kro.kr";
    }

    @GetMapping("/joinform")
    public String joinform(HttpSession session) {
        if(session.getAttribute("UserInfo")==null) {
            return "views/mypage/join/JoinForm";
        }
            return "redirect:http://www.oneaclo.kro.kr";
    }

    @GetMapping("/PasswordChange")
    public String PasswordChange() {
        return "views/mypage/info/PasswordChange";
    }
    @GetMapping("/jwtcreate")
    public String jwtcteate() {
//        return "redirect:www.oneaclo.kro.kr";
        return "redirect:/userinfo";
    }
    @GetMapping("/PhoneCh")
    public String PhoneCh() {
        return "views/mypage/info/PhoneCh";
    }

    @GetMapping("/NickCh")
    public String NickCh() {
        return "views/mypage/info/NickCh";
    }

    @GetMapping("/userinfo")
    public String userinfo(HttpSession session, Model model) {
        String token = (String) session.getAttribute("UserInfo");
        String[] arr = {"id","pw","name","email","nick","profile","zipcode","address","detailaddr","phone"};
        for (String list:arr) {
            model.addAttribute(list, tokenProcess.getMembersToken(token,list));
        }
        return "views/mypage/info/UserInfo";
    }

    @GetMapping("/ProfileCh")
    public String ProfileCh() {
        return "views/mypage/info/ProfileCh";
    }

    @GetMapping("/AddressCh")
    public String AddressCh() {
        return "views/mypage/info/AddressCh";
    }
}
