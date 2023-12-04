package kr.kro.oneaclo.www.Controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.Common.TokenProcess;
import kr.kro.oneaclo.www.DTO.Shop.ProductDTO;
import kr.kro.oneaclo.www.Service.Mypage.MembersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final TokenProcess tokenProcess;
    private final MembersService membersService;

    private final String[] arr = {"id", "nick", "profile", "auth"};

    private Map<String, String> TokenList(String token) {
        Map<String, String> map = new HashMap<>();

        if (token != null)
            for (String str : arr) map.put(str, tokenProcess.getMembersToken(token, str));

        return map;
    }

    @GetMapping("/")
    public String Home(HttpSession session, Model model, HttpServletRequest req) {
        String token = (String) session.getAttribute("UserInfo");

        if (token != null) {
            Map<String, String> user = TokenList(token);

            for (String str : arr) model.addAttribute(str, user.get(str));
        }

        Cookie[] cookies = req.getCookies();
        List<ProductDTO> productDTOS = new ArrayList<>();

        for(Cookie c:cookies) {
            if(c.getValue().length() < 10 && TokenList(token).get("id").equals(URLDecoder.decode(c.getName()).split("\\|")[0])) {
                ProductDTO productDTO = membersService.ProductInfo(Integer.parseInt(c.getValue()));
                productDTOS.add(productDTO);
            }
        }

        model.addAttribute("lately",productDTOS);

        return "views/index";
    }

}
