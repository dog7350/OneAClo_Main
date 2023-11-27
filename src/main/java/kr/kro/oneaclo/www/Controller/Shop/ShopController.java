package kr.kro.oneaclo.www.Controller.Shop;

import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.Common.TokenProcess;
import kr.kro.oneaclo.www.Entity.Shop.Product;
import kr.kro.oneaclo.www.Service.Shop.ProductFileService;
import kr.kro.oneaclo.www.Service.Shop.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/shop")
@RequiredArgsConstructor
public class ShopController {
    private final TokenProcess tokenProcess;
    private final ProductService productService;
    private final ProductFileService productFileService;

    private String[] arr = {"id", "nick", "profile", "auth"};
    private String auth;

    private Map<String, String> TokenList(String token) {
        Map<String, String> map = new HashMap<>();

        if (token != null)
            for (String str : arr) map.put(str, tokenProcess.getMembersToken(token, str));

        auth = map.get("auth");

        return map;
    }

    @GetMapping("/productUpload")
    public String shopUpload(HttpSession session, Model model) {
        String token = (String) session.getAttribute("UserInfo");

        for (String str : arr) model.addAttribute(str, TokenList(token).get(str));

        if (auth.equals("a") || auth.equals("m")) { return "views/shop/productUpload"; }
        else {
            model.addAttribute("url", "/");
            model.addAttribute("msg", "관리자가 아닙니다.");
            return "views/common/message";
        }
    }

    @GetMapping("/list")
    public String shopList(HttpSession session, Model model, @RequestParam(defaultValue = "0") String pageNumber,
                              @RequestParam(defaultValue = "pname") String searchOption, @RequestParam(defaultValue = "%") String searchValue) {
        String token = (String) session.getAttribute("UserInfo");

        for (String str : arr) model.addAttribute(str, TokenList(token).get(str));

        Page<Product> pages = null;

        int elementCount = 9;

        if (searchOption == "pname" && searchValue == "%") pages = productService.AllProductPage(Integer.parseInt(pageNumber), elementCount);
        else pages = productService.SearchProductPage(Integer.parseInt(pageNumber), elementCount, searchOption, searchValue);

        int blockPage = 10;
        int startPage = (Integer.parseInt(pageNumber) / blockPage) * blockPage;
        int endPage = Math.min((startPage + blockPage), pages.getTotalPages());

        model.addAttribute("products", pages.getContent());
        model.addAttribute("pages", pages);

        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("searchOption", searchOption);
        if (searchValue == "%") searchValue = "";
        model.addAttribute("searchValue", searchValue);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "views/shop/shopList";
    }

    @GetMapping("/detail")
    public String productDetail(HttpSession session, Model model, @RequestParam int pno) {
        String token = (String) session.getAttribute("UserInfo");

        for (String str : arr) model.addAttribute(str, TokenList(token).get(str));

        model.addAttribute("product", productService.ProductDetail(pno));
        model.addAttribute("files", productFileService.ProductDetail(pno));

        return "views/shop/productDetail";
    }
}
