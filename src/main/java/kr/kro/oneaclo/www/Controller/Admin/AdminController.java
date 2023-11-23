package kr.kro.oneaclo.www.Controller.Admin;

import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.Common.TokenProcess;
import kr.kro.oneaclo.www.Entity.Mypage.MemberInfo;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import kr.kro.oneaclo.www.Service.Mypage.MemberInfoService;
import kr.kro.oneaclo.www.Service.Mypage.MembersService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Member;
import java.util.*;

@Controller
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminController {
    private final TokenProcess tokenProcess;
    private final MembersService membersService;
    private final MemberInfoService memberInfoService;

    private String[] arr = {"id", "nick", "profile", "auth"};
    private String auth;

    private Map<String, String> TokenList(String token) {
        Map<String, String> map = new HashMap<>();

        if (token != null)
            for (String str : arr) map.put(str, tokenProcess.getMembersToken(token, str));

        auth = map.get("auth");

        return map;
    }

    @GetMapping("/")
    public String AdminMain(HttpSession session, Model model) {
        String token = (String) session.getAttribute("UserInfo");

        for (String str : arr) model.addAttribute(str, TokenList(token).get(str));

        if (auth.equals("a") || auth.equals("m")) return "views/admin/admin";
        else {
            model.addAttribute("url", "/");
            model.addAttribute("msg", "관리자가 아닙니다.");
            return "views/common/message";
        }
    }

    @GetMapping("/memberList")
    public String MemberList(HttpSession session, Model model, @RequestParam String pageNumber) {
        String token = (String) session.getAttribute("UserInfo");

        for (String str : arr) model.addAttribute(str, TokenList(token).get(str));

        if (auth.equals("a") || auth.equals("m")) {
            if (pageNumber == null) pageNumber = "0";

            int elementCount = 15;
            Page<Members> pages = membersService.AllUserPage(Integer.parseInt(pageNumber), elementCount);
            int blockPage = 10;
            int startPage = (Integer.parseInt(pageNumber) / blockPage) * blockPage;
            int endPage = Math.min((startPage + blockPage), pages.getTotalPages());
            /*
                전체 페이지 수 : pages.getTotalPages()
                전체 데이터 개수 : pages.getTotalElements()
                현재 페이지 번호 : pages.getNumber()
                페이지당 데이터 개수 : pages.getSize()
                다음 페이지 존재 여부 : pages.hasNext()
                시작 페이지 여부 : pages.isFirst()
            */

            model.addAttribute("members", pages.getContent());
            model.addAttribute("memberInfos", memberInfoService.AllUserPage(Integer.parseInt(pageNumber), elementCount).getContent());
            model.addAttribute("pages", pages);

            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);

            return "views/admin/memberList";
        }
        else {
            model.addAttribute("url", "/");
            model.addAttribute("msg", "관리자가 아닙니다.");
            return "views/common/message";
        }
    }
    @GetMapping("/memberMail")
    public String memberMail(HttpSession session, Model model, @RequestParam String email) {
        String token = (String) session.getAttribute("UserInfo");

        for (String str : arr) model.addAttribute(str, TokenList(token).get(str));

        MemberInfo userInfo = memberInfoService.findUser(email).get();

        model.addAttribute("userInfo", userInfo);

        if (auth.equals("a") || auth.equals("m")) return "views/admin/memberMail";
        else {
            model.addAttribute("url", "/");
            model.addAttribute("msg", "관리자가 아닙니다.");
            return "views/common/message";
        }
    }
    @PostMapping("/sendMail")
    public String sendMail(@RequestParam String destEmail, @RequestParam String title, @RequestParam String content) {
        membersService.send(destEmail, title, content);

        return "redirect:/admin/memberList?pageNumber=0";
    }

    @GetMapping("/productList")
    public String ProductList(HttpSession session, Model model) {
        String token = (String) session.getAttribute("UserInfo");

        for (String str : arr) model.addAttribute(str, TokenList(token).get(str));

        if (auth.equals("a") || auth.equals("m")) return "views/admin/admin";
        else {
            model.addAttribute("url", "/");
            model.addAttribute("msg", "관리자가 아닙니다.");
            return "views/common/message";
        }
    }

    @GetMapping("/orderList")
    public String OrderList(HttpSession session, Model model) {
        String token = (String) session.getAttribute("UserInfo");

        for (String str : arr) model.addAttribute(str, TokenList(token).get(str));

        if (auth.equals("a") || auth.equals("m")) return "views/admin/admin";
        else {
            model.addAttribute("url", "/");
            model.addAttribute("msg", "관리자가 아닙니다.");
            return "views/common/message";
        }
    }

    @GetMapping("/dataAnalysis")
    public String DataAnalysis(HttpSession session, Model model) {
        String token = (String) session.getAttribute("UserInfo");

        for (String str : arr) model.addAttribute(str, TokenList(token).get(str));

        if (auth.equals("a") || auth.equals("m")) return "views/admin/admin";
        else {
            model.addAttribute("url", "/");
            model.addAttribute("msg", "관리자가 아닙니다.");
            return "views/common/message";
        }
    }
}
