package kr.kro.oneaclo.www.Controller.Shop;

import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.Common.TokenProcess;
import kr.kro.oneaclo.www.Service.Shop.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/order")
@RequiredArgsConstructor
public class OrderController {
    private final TokenProcess tokenProcess;
    private final ProductService productService;

    private String[] arr = {"id", "name", "email", "phone", "zipcode","address","detailaddr"};

    private Map<String, String> TokenList(String token) {
        Map<String, String> map = new HashMap<>();

        if (token != null)
            for (String str : arr) map.put(str, tokenProcess.getMembersToken(token, str));

        return map;
    }

    @GetMapping("/buyPage")
    public String OrderBuyPage(HttpSession session, Model model, @RequestParam int pno, @RequestParam int count) {
        String token = (String) session.getAttribute("UserInfo");

        if (token == null) {
            model.addAttribute("url", "/mypage/loginform");
            model.addAttribute("msg", "로그인 좀 하세요.");
            return "views/common/message";
        }

        for (String str : arr) model.addAttribute(str, TokenList(token).get(str));

        model.addAttribute("product", productService.ProductDetail(pno));
        model.addAttribute("count", count);

        return "views/shop/buyPage";
    }
}
