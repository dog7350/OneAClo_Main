package kr.kro.oneaclo.www.Controller.Mypage;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.DTO.Mypage.MemberDTO;
import kr.kro.oneaclo.www.DTO.Shop.ProductDTO;
import kr.kro.oneaclo.www.Service.Mypage.MemberInfoService;
import kr.kro.oneaclo.www.Service.Mypage.MembersService;
import kr.kro.oneaclo.www.Common.TokenProcess;
import kr.kro.oneaclo.www.Service.Shop.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
@Controller
@RequestMapping(value = "/mypage")
@RequiredArgsConstructor
public class MemberApiController {

    private final MembersService membersService;
    private final MemberInfoService memberInfoService;
    private final TokenProcess tokenProcess;
    private String UserString(HttpSession session,String want) {return tokenProcess.getMembersToken(String.valueOf(session.getAttribute("UserInfo")),want);}
    @PostMapping(value = "/join")
    public String join(MemberDTO dto,@RequestParam(required = false) MultipartFile UserProfile) throws Exception{
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
    @PostMapping("/p/ChangePassword")
    public String ChangePassword(@RequestParam String ChangePw, @RequestParam String CkPw, HttpSession session) {
        if(ChangePw.equals(CkPw)) {
            membersService.PwChange(ChangePw,UserString(session,"id"));
            return "redirect:/mypage/p/logout";
        }else {
            return "redirect:/mypage/p/userinfo";
        }
    }
    @GetMapping("/p/UpdateSuccess")
    public String UpdateSuccess(HttpSession session) {
        String id = UserString(session,"id");
        session.removeAttribute("UserInfo");
        String UserToken = tokenProcess.createUserToken(id);
        session.setAttribute("UserInfo",UserToken);
        return "redirect:/mypage/p/userinfo";
    }

    @PostMapping("/p/NewAddr")
    public String NewAddr(HttpSession session,@RequestParam String zipcode,
                          @RequestParam String address,@RequestParam String detailaddr) {
        memberInfoService.AddrChange(UserString(session,"email"),zipcode,address,detailaddr);
        return "redirect:/mypage/p/UpdateSuccess";
    }

    @GetMapping("/p/UserDel")
    public String UserDel(HttpSession session) {
        membersService.UserDel(UserString(session,"id"));
        return "redirect:/mypage/p/logout";
    }

    @GetMapping("/p/Basket")
    public String basket(Model model,HttpServletRequest request,HttpSession session) {
        Cookie[] CartList = request.getCookies();
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(Cookie c:CartList) {
            if(c.getValue().length() < 10 && UserString(session,"id").equals(URLDecoder.decode(c.getName()).split("#")[0])) {
                ProductDTO productDTO = membersService.ProductInfo(Integer.parseInt(c.getValue().split("/")[0]));
                productDTO.setCount(Integer.parseInt(c.getValue().split("/")[1]));
                productDTOS.add(productDTO);
            }
        }
        model.addAttribute("cart",productDTOS);

        return "views/mypage/basket/BasketInfo";
    }
}
