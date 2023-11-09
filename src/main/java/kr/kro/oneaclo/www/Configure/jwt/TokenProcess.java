package kr.kro.oneaclo.www.Configure.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenProcess {
    private final JwtProperties jwtProperties;

    private Claims getClaims (String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
    private Map getMembers (String token) {
        Claims claims = getClaims(token);
        return claims.get("user", HashMap.class);
    }
    private Map getMemberInfo (String token) {
        Claims claims = getClaims(token);
        return claims.get("userinfo", HashMap.class);
    }

    public String getMemberToken (String token, String wantValue) {
        Map User = getMembers(token);
        Map UserInfo = getMemberInfo(token);

        if (User.get(wantValue) == null) return (String) UserInfo.get(wantValue);
        else return (String) User.get(wantValue);
    }
}
