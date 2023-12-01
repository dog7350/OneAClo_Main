package kr.kro.oneaclo.www.Controller.Shop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.Common.TokenProcess;
import kr.kro.oneaclo.www.DTO.Log.LogDTO;
import kr.kro.oneaclo.www.Entity.Shop.Product;
import kr.kro.oneaclo.www.Service.Logs.InquiryLogService;
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
    private final InquiryLogService inquiryLogService;

    private String[] arr = {"id", "profile", "auth", "age", "gender", "address"};
    private String auth;
    private LogDTO log;

    private Map<String, String> TokenList(String token) {
        Map<String, String> map = new HashMap<>();

        if (token != null) {
            for (String str : arr) map.put(str, tokenProcess.getMembersToken(token, str));

            log = new LogDTO();

            log.setId(map.get("id"));
            log.setAge(Integer.parseInt(map.get("age")));
            log.setGender(map.get("gender"));
            log.setAddress(map.get("address"));
        }

        auth = map.get("auth");

        return map;
    }

    @GetMapping("/productUpload")
    public String shopUpload(HttpSession session, Model model) {
        String token = (String) session.getAttribute("UserInfo");
        Map<String, String> user = TokenList(token);

        for (String str : arr) model.addAttribute(str, user.get(str));

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
        Map<String, String> user = TokenList(token);

        for (String str : arr) model.addAttribute(str, user.get(str));

        Page<Product> pages = null;

        int elementCount = 9;

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

        return "views/shop/shopList";
    }

    @GetMapping("/detail")
    public String productDetail(HttpServletRequest req, HttpSession session, Model model, @RequestParam int pno) {
        String token = (String) session.getAttribute("UserInfo");
        Map<String, String> user = TokenList(token);

        for (String str : arr) model.addAttribute(str, user.get(str));

        Product product = productService.ProductDetail(pno);

        if (!req.getHeader("Referer").contains("/admin/") && !req.getHeader("Referer").contains("/order/"))
            inquiryLogService.InquiryLogSave(log, product);

        model.addAttribute("product", product);
        model.addAttribute("files", productFileService.ProductDetail(pno));

        return "views/shop/productDetail";
    }
}
