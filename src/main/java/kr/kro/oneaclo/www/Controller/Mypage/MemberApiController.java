package kr.kro.oneaclo.www.Controller.Mypage;


import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.DTO.Mypage.MemberDTO;
import kr.kro.oneaclo.www.Service.Mypage.MemberInfoService;
import kr.kro.oneaclo.www.Service.Mypage.MembersService;
import kr.kro.oneaclo.www.Common.TokenProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



@Controller
@RequestMapping(value = "/mypage")
@RequiredArgsConstructor
public class MemberApiController {

    private final MembersService membersService;
    private final MemberInfoService memberInfoService;
    private final TokenProcess tokenProcess;
    @PostMapping(value = "/join")
    public String join(MemberDTO dto,@RequestParam MultipartFile UserProfile) throws Exception{
        String result = membersService.MembersJoin(dto,UserProfile);
        memberInfoService.MemberInfoJoin(dto);
        if("success".equalsIgnoreCase(result)){
            return "redirect:/mypage/loginform";
        }else {
            return "redirect:/";
        }
    }
    @GetMapping("/EmailCk")
    public String EmailCk(@RequestParam String email,Model model) {
        model.addAttribute("UserMail",email);
        return "views/mypage/join/EmailCk";
    }
    @PostMapping("/ChangePassword")
    public String ChangePassword(@RequestParam String ChangePw, @RequestParam String CkPw, HttpSession session) {
        String token = (String) session.getAttribute("UserInfo");
        String id = tokenProcess.getMembersToken(token,"id");
        if(ChangePw.equals(CkPw)) {
            membersService.PwChange(ChangePw,id);
            return "redirect:/mypage/logout";
        }else {
            return "redirect:/mypage/userinfo";
        }
    }
    @GetMapping("/UpdateSuccess")
    public String UpdateSuccess(HttpSession session) {
        String id = tokenProcess.getMembersToken((String) session.getAttribute("UserInfo"),"id");
        session.removeAttribute("UserInfo");
        String UserToken = tokenProcess.createUserToken(id);
        session.setAttribute("UserInfo",UserToken);
        return "redirect:/";
    }

    @PostMapping("/NewAddr")
    public String NewAddr(HttpSession session,@RequestParam String zipcode,
                          @RequestParam String address,@RequestParam String detailaddr) {
        String token = (String) session.getAttribute("UserInfo");
        String email = tokenProcess.getMembersToken(token,"email");
        memberInfoService.AddrChange(email,zipcode,address,detailaddr);
        return "redirect:/mypage/UpdateSuccess";
    }

    @GetMapping("/UserDel")
    public String UserDel(HttpSession session) {
        String token = (String) session.getAttribute("UserInfo");
        String id = tokenProcess.getMembersToken(token,"id");
        membersService.UserDel(id);
        return "redirect:/mypage/logout";
    }
}