package kr.kro.oneaclo.www.Controller.Shop;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.Common.CookieFinder;
import kr.kro.oneaclo.www.Common.TokenProcess;
import kr.kro.oneaclo.www.DTO.Page.PageRequestDTO;
import kr.kro.oneaclo.www.DTO.Log.LogDTO;
import kr.kro.oneaclo.www.DTO.Page.PageResponseDTO;
import kr.kro.oneaclo.www.DTO.Shop.ProductCmtDTO;
import kr.kro.oneaclo.www.DTO.Shop.ProductDTO;
import kr.kro.oneaclo.www.Entity.Shop.Product;
import kr.kro.oneaclo.www.Service.Logs.InquiryLogService;
import kr.kro.oneaclo.www.Service.Mypage.MembersService;
import kr.kro.oneaclo.www.Service.Shop.ProductCmtService;
import kr.kro.oneaclo.www.Service.Shop.ProductFileService;
import kr.kro.oneaclo.www.Service.Shop.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/shop")
@RequiredArgsConstructor
public class ShopController {
    private final TokenProcess tokenProcess;
    private final ProductService productService;
    private final ProductCmtService productCmtService;
    private final ProductFileService productFileService;
    private final InquiryLogService inquiryLogService;
    private final MembersService membersService;
    private final CookieFinder cookieFinder;

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
    public String shopList(HttpServletRequest req, HttpSession session, Model model, @RequestParam(defaultValue = "0") String pageNumber,
                           @RequestParam(defaultValue = "pname") String searchOption, @RequestParam(defaultValue = "%") String searchValue) {
        String token = (String) session.getAttribute("UserInfo");
        Map<String, String> user = TokenList(token);

        for (String str : arr) model.addAttribute(str, user.get(str));

        Cookie recom = cookieFinder.recomCookieFind(req);
        if (recom == null) {
            model.addAttribute("recom", "");
            model.addAttribute("recomList", new ArrayList<>());
        }
        else {
            model.addAttribute("recom", recom.getValue());
            model.addAttribute("recomList", productService.RecomList(recom.getValue()));
        }

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

        List<ProductDTO> productDTOS = new ArrayList<>();

        if (req.getCookies() != null) {
            Cookie[] cookies = req.getCookies();
            for (Cookie c : cookies) {
                String id = TokenList(token).get("id");
                if (id != null){
                    if (c.getValue().length() < 10 && id.equals(URLDecoder.decode(c.getName()).split("\\|")[0])) {
                        ProductDTO productDTO = membersService.ProductInfo(Integer.parseInt(c.getValue()));
                        if (productDTO != null) productDTOS.add(productDTO);
                    }
                }
            }
        }

        model.addAttribute("lately",productDTOS);

        return "views/shop/shopList";
    }

    @GetMapping("/detail")
    public String productDetail(HttpServletRequest req, HttpServletResponse res, HttpSession session, Model model, @RequestParam int pno, PageRequestDTO pageRequestDTO) {
        String token = (String) session.getAttribute("UserInfo");
        Map<String, String> user = TokenList(token);

        for (String str : arr) model.addAttribute(str, user.get(str));

        if (!cookieFinder.shopInquiryCookieFind(req, pno)) {
            Cookie cookie = new Cookie(String.valueOf(pno), String.valueOf(pno));
            cookie.setMaxAge(30 * 60);
            res.addCookie(cookie);

            productService.ProductInquiryAdd(pno);
        }

        Cookie recom = cookieFinder.recomCookieFind(req);
        if (recom == null) {
            model.addAttribute("recom", "");
            model.addAttribute("recomList", new ArrayList<>());
        }
        else {
            model.addAttribute("recom", recom.getValue());
            model.addAttribute("recomList", productService.RecomList(recom.getValue()));
        }

        Product product = productService.ProductDetail(pno);

        if (!req.getHeader("Referer").contains("/admin/") && !req.getHeader("Referer").contains("/order/"))
            inquiryLogService.InquiryLogSave(log, product);

        model.addAttribute("product", product);
        model.addAttribute("files", productFileService.ProductDetail(pno));

        pageRequestDTO.setSize(5);
        PageResponseDTO<ProductCmtDTO> responseDTO = productCmtService.ProductCmtList(pageRequestDTO, pno);
        model.addAttribute("cmt", responseDTO);

        if (user.get("id") != null) {
            Cookie approach = new Cookie(URLEncoder.encode(user.get("id")) + "|" + pno, pno + "");
            approach.setMaxAge(60 * 60 * 24);
            approach.setPath("/");
            res.addCookie(approach);
        }

        List<ProductDTO> productDTOS = new ArrayList<>();
        if (req.getCookies() != null) {
            Cookie[] cookies = req.getCookies();
            for (Cookie c : cookies) {
                if (c.getValue().length() < 10 && TokenList(token).get("id").equals(URLDecoder.decode(c.getName()).split("\\|")[0])) {
                    ProductDTO productDTO = membersService.ProductInfo(Integer.parseInt(c.getValue()));
                    if (productDTO != null) productDTOS.add(productDTO);
                }
            }
        }


        model.addAttribute("lately",productDTOS);

        return "views/shop/productDetail";
    }
    @PostMapping("/CmtModify")
    public void CmtModify(ProductCmtDTO dto) {
        productCmtService.CmtModify(dto);
    }
}
