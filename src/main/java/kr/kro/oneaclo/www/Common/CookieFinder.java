package kr.kro.oneaclo.www.Common;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class CookieFinder {
    public boolean shopInquiryCookieFind(HttpServletRequest req, int pno) {
        Cookie[] cookies = req.getCookies();
        for (Cookie c : cookies)
            if (c.getValue().equals(String.valueOf(pno)))
                return true;

        return false;
    }

    public Cookie recomCookieFind(HttpServletRequest req) {
        if (req.getCookies() != null) {
            Cookie[] cookies = req.getCookies();

            for (Cookie c : cookies)
                if (c != null)
                    if (c.getName().equals("recom"))
                        return c;
        }

        return null;
    }
}
