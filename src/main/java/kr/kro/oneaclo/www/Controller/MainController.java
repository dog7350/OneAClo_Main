package kr.kro.oneaclo.www.Controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.Common.CookieFinder;
import kr.kro.oneaclo.www.Common.TokenProcess;
import kr.kro.oneaclo.www.DTO.Shop.ProductDTO;
import kr.kro.oneaclo.www.Service.Board.BoardService;
import kr.kro.oneaclo.www.Service.Mypage.MembersService;
import kr.kro.oneaclo.www.Service.Shop.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final TokenProcess tokenProcess;
    private final BoardService boardService;
    private final ProductService productService;
    private final MembersService membersService;
    private final CookieFinder cookieFinder;

    private final String[] arr = {"id", "nick", "profile", "auth", "age", "gender", "address"};

    private Map<String, String> TokenList(String token) {
        Map<String, String> map = new HashMap<>();

        if (token != null)
            for (String str : arr) map.put(str, tokenProcess.getMembersToken(token, str));

        return map;
    }

    @GetMapping("/")
    public String Home(HttpServletRequest req, HttpSession session, Model model) {
        String token = (String) session.getAttribute("UserInfo");

        if (token != null) {
            Map<String, String> user = TokenList(token);

            for (String str : arr) model.addAttribute(str, user.get(str));
        }

        Cookie recom = cookieFinder.recomCookieFind(req);
        if (recom == null) model.addAttribute("recom", "");
        else {
            model.addAttribute("recom", recom.getValue());
            model.addAttribute("recomList", productService.RecomList(recom.getValue()));
        }

        model.addAttribute("popProduct", productService.MainPopularityList());
        model.addAttribute("newProduct", productService.MainNewList());

        model.addAttribute("notiBoard", boardService.MainNotificationList());
        model.addAttribute("norBoard", boardService.MainNewNormalList());

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

        return "views/index";
    }

}
