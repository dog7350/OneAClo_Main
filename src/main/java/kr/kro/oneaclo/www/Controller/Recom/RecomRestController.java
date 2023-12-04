package kr.kro.oneaclo.www.Controller.Recom;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecomRestController {

    @GetMapping("/recomCreate")
    public String RecomCreate(HttpServletResponse res, @RequestParam String category) {
        Cookie cookie = new Cookie("recom", category);
        res.addCookie(cookie);

        return "success";
    }
}
