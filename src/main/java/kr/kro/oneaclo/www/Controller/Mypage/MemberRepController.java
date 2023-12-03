package kr.kro.oneaclo.www.Controller.Mypage;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.Service.Mypage.MemberInfoService;
import kr.kro.oneaclo.www.Service.Mypage.MembersService;
import kr.kro.oneaclo.www.Common.TokenProcess;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping(value = "/mypage")
@RequiredArgsConstructor
@Log4j2
public class MemberRepController {
    private final MembersService membersService;
    private final MemberInfoService memberInfoService;
    private final TokenProcess tokenProcess;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private String UserString(HttpSession session,String want) {return tokenProcess.getMembersToken(String.valueOf(session.getAttribute("UserInfo")),want);}
    private void BoolSetUp(HttpServletResponse res,boolean Bool) throws IOException {
        try {
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            PrintWriter writer = res.getWriter();
            writer.print(Bool);
            writer.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void StringSetUp(HttpServletResponse res,String input) throws IOException {
        try {
            res.setContentType("text/plain");
            res.setCharacterEncoding("UTF-8");
            PrintWriter writer = res.getWriter();
            writer.print(input);
            writer.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/IdCk")
    public void IdCk(@RequestParam String id, HttpServletResponse res) throws IOException {
        boolean bool = membersService.IdCk(id);
        BoolSetUp(res, bool);
    }
    @GetMapping("/EmailGetList")
    public void EmailGetList(@RequestParam String email,HttpServletResponse res) throws IOException {
        boolean bool = memberInfoService.EmailCk(email);
        BoolSetUp(res, bool);
    }

    @GetMapping("/SendMail")
    public void SendMail(@RequestParam String email, HttpServletResponse res) throws IOException{
        String body="<h3>ONE.A.CLO 이메일 인증 서비스 입니다</h3><p>회원의 인증 번호 : ";
        String code = "";
        Random ran = new Random();
        for(int i=0; i<6; i++) {
            code += ran.nextInt(10);
        }
        body += code+"</p>";
        String title="ONE.A.CLO 가입 인증 메일입니다.";
        StringSetUp(res,code);
        membersService.send(email,title,body);
    }
    @GetMapping("/p/PasswordCk")
    public String PasswordCk(@RequestParam String Origin,HttpSession session) {
        if(bCryptPasswordEncoder.matches(Origin,UserString(session,"pw"))) {
            return "success";
        }else {
            return "fail";
        }
    }
    @PostMapping("/p/NewPhoneNumber")
    public void NewPhoneNumber(@RequestParam String NewPhone, HttpSession session) {
        memberInfoService.PhoneChange(NewPhone,UserString(session,"email"));
    }

    @PostMapping("/p/NewProfile")
    public void ProfileChange(@RequestParam MultipartFile NewProfile, HttpSession session) {
        membersService.ProfileChange(UserString(session,"id"),NewProfile);
    }

    @PostMapping("/p/NickChange")
    public void NickChange(@RequestParam String NickName,HttpSession session) {
        membersService.NickChange(UserString(session,"id"),NickName);
    }

    @GetMapping("/p/AddBasket")
    public void AddBasket(@RequestParam int pno,@RequestParam int count,@RequestParam int price, HttpServletResponse res,HttpSession session) {
        Cookie product = new Cookie("product"+pno,UserString(session,"id")+"/"+pno+"/"+count+"/"+price);
        product.setMaxAge(60*60*24);
        product.setPath("/");
        res.addCookie(product);
    }
}
