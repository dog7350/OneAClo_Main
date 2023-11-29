package kr.kro.oneaclo.www.Controller.Admin;

import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.Common.TokenProcess;
import kr.kro.oneaclo.www.Entity.Mypage.MemberInfo;
import kr.kro.oneaclo.www.Entity.Shop.Orders;
import kr.kro.oneaclo.www.Entity.Shop.Product;
import kr.kro.oneaclo.www.Service.Mypage.MemberInfoService;
import kr.kro.oneaclo.www.Service.Mypage.MembersService;
import kr.kro.oneaclo.www.Service.Shop.OrdersService;
import kr.kro.oneaclo.www.Service.Shop.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminController {
    private final TokenProcess tokenProcess;
    private final MembersService membersService;
    private final MemberInfoService memberInfoService;
    private final ProductService productService;
    private final OrdersService ordersService;

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
    public String MemberList(HttpSession session, Model model, @RequestParam(defaultValue = "0") String pageNumber,
                             @RequestParam(defaultValue = "id") String searchOption, @RequestParam(defaultValue = "%") String searchValue) {
        String token = (String) session.getAttribute("UserInfo");

        for (String str : arr) model.addAttribute(str, TokenList(token).get(str));

        if (auth.equals("a") || auth.equals("m")) {
            Page<MemberInfo> pages = null;

            int elementCount = 15;

            if (searchOption.equals("id") && searchValue.equals("%")) pages = memberInfoService.AllUserPage(Integer.parseInt(pageNumber), elementCount);
            else pages = memberInfoService.SearchUserPage(Integer.parseInt(pageNumber), elementCount, searchOption, searchValue);

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

            model.addAttribute("memberinfos", pages.getContent());
            model.addAttribute("pages", pages);

            model.addAttribute("pageNumber", pageNumber);
            model.addAttribute("searchOption", searchOption);
            if (searchValue.equals("%")) searchValue = "";
            model.addAttribute("searchValue", searchValue);

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
    public String ProductList(HttpSession session, Model model, @RequestParam(defaultValue = "0") String pageNumber,
                              @RequestParam(defaultValue = "pname") String searchOption, @RequestParam(defaultValue = "%") String searchValue) {
        String token = (String) session.getAttribute("UserInfo");

        for (String str : arr) model.addAttribute(str, TokenList(token).get(str));

        if (auth.equals("a") || auth.equals("m")) {
            Page<Product> pages = null;

            int elementCount = 6;

            if (searchOption.equals("pname") && searchValue.equals("%")) pages = productService.AllProductPage(Integer.parseInt(pageNumber), elementCount);
            else pages = productService.SearchProductPage(Integer.parseInt(pageNumber), elementCount, searchOption, searchValue);

            int blockPage = 10;
            int startPage = (Integer.parseInt(pageNumber) / blockPage) * blockPage;
            int endPage = Math.min((startPage + blockPage), pages.getTotalPages());

            model.addAttribute("products", pages.getContent());
            model.addAttribute("pages", pages);

            model.addAttribute("pageNumber", pageNumber);
            model.addAttribute("searchOption", searchOption);
            if (searchValue.equals("%")) searchValue = "";
            model.addAttribute("searchValue", searchValue);

            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);

            return "views/admin/shopList";
        }
        else {
            model.addAttribute("url", "/");
            model.addAttribute("msg", "관리자가 아닙니다.");
            return "views/common/message";
        }
    }

    @GetMapping("/shopModify")
    public String shopModify(HttpSession session, Model model, @RequestParam String pno) {
        String token = (String) session.getAttribute("UserInfo");

        for (String str : arr) model.addAttribute(str, TokenList(token).get(str));

        if (auth.equals("a") || auth.equals("m")) {
            model.addAttribute("product", productService.ProductDetail(Integer.parseInt(pno)));

            return "views/admin/shopModify";
        }
        else {
            model.addAttribute("url", "/");
            model.addAttribute("msg", "관리자가 아닙니다.");
            return "views/common/message";
        }
    }

    @GetMapping("/orderList")
    public String OrderList(HttpSession session, Model model, @RequestParam(defaultValue = "0") String pageNumber,
                            @RequestParam(defaultValue = "oid") String searchOption, @RequestParam(defaultValue = "%") String searchValue) {
        String token = (String) session.getAttribute("UserInfo");

        for (String str : arr) model.addAttribute(str, TokenList(token).get(str));

        if (auth.equals("a") || auth.equals("m")) {
            Page<Orders> pages = null;

            Map<String, String> statusMap = new HashMap<>();
            statusMap.put("Checking", "결제 확인중");
            statusMap.put("Ready", "배송 준비중");
            statusMap.put("Running", "배송중");
            statusMap.put("Complate", "배송 완료");
            statusMap.put("ReturnChecking", "교환 확인중");
            statusMap.put("ReturnReady", "교환 대기");
            statusMap.put("Return", "교환 완료");
            statusMap.put("DeleteChecking", "환불 확인중");
            statusMap.put("Delete", "환불 완료");

            List<String> s1 = List.of("Checking", "Ready", "Running", "Complate");
            List<String> s2 = List.of("ReturnChecking", "ReturnReady", "Return");
            List<String> s3 = List.of("DeleteChecking", "Delete");

            int elementCount = 10;

            if (searchOption.equals("oid") && searchValue.equals("%")) pages = ordersService.AllOrdersPage(Integer.parseInt(pageNumber), elementCount);
            else pages = ordersService.SearchOrdersPage(Integer.parseInt(pageNumber), elementCount, searchOption, searchValue);

            int blockPage = 10;
            int startPage = (Integer.parseInt(pageNumber) / blockPage) * blockPage;
            int endPage = Math.min((startPage + blockPage), pages.getTotalPages());

            model.addAttribute("sMap", statusMap);
            model.addAttribute("s1", s1);
            model.addAttribute("s2", s2);
            model.addAttribute("s3", s3);

            model.addAttribute("orders", pages.getContent());
            model.addAttribute("pages", pages);

            model.addAttribute("pageNumber", pageNumber);
            model.addAttribute("searchOption", searchOption);
            if (searchValue.equals("%")) searchValue = "";
            model.addAttribute("searchValue", searchValue);

            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);

            return "views/admin/orderList";
        }
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
