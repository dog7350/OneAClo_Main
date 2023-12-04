package kr.kro.oneaclo.www.Controller.Shop;

import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.Common.TokenProcess;
import kr.kro.oneaclo.www.Entity.Shop.Orders;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/order")
@RequiredArgsConstructor
public class OrderController {
    private final TokenProcess tokenProcess;
    private final ProductService productService;
    private final OrdersService ordersService;

    private String[] arr = {"id", "name", "email", "phone", "zipcode","address","detailaddr"};
    private String myId;

    private Map<String, String> TokenList(String token) {
        Map<String, String> map = new HashMap<>();

        if (token != null)
            for (String str : arr) map.put(str, tokenProcess.getMembersToken(token, str));

        myId = map.get("id");

        return map;
    }

    @GetMapping("/buyPage")
    public String OrderBuyPage(HttpSession session, Model model, @RequestParam int pno, @RequestParam int count) {
        String token = (String) session.getAttribute("UserInfo");
        System.out.println(pno);
        if (token == null) {
            model.addAttribute("url", "/mypage/loginform");
            model.addAttribute("msg", "로그인 좀 하세요.");
            return "views/common/message";
        }
        Map<String, String> user = TokenList(token);

        for (String str : arr) model.addAttribute(str, user.get(str));


        model.addAttribute("product", productService.ProductDetail(pno));
        model.addAttribute("count", count);

        return "views/shop/buyPage";
    }

    @GetMapping("/orderList")
    public String OrderList (HttpSession session, Model model, @RequestParam(defaultValue = "0") String pageNumber) {

        String token = (String) session.getAttribute("UserInfo");
        Map<String, String> user = TokenList(token);

        for (String str : arr) model.addAttribute(str, user.get(str));

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

        Page<Orders> pages = ordersService.UserOrdersPage(Integer.parseInt(pageNumber), elementCount, myId);

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

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "views/shop/orderList";
    }
}
