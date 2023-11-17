package kr.kro.oneaclo.www.Common;

import io.jsonwebtoken.Claims;
import kr.kro.oneaclo.www.Common.JWT.TokenProvider;
import kr.kro.oneaclo.www.Entity.Member.MemberInfo;
import kr.kro.oneaclo.www.Entity.Member.Members;
import kr.kro.oneaclo.www.Repository.Member.MemberInfoRepository;
import kr.kro.oneaclo.www.Repository.Member.MembersRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class TokenProcess {
    private final TokenProvider tokenProvider;
    private final MembersRepository membersRepository;
    private final MemberInfoRepository memberInfoRepository;

    private Map getMembers (String token) {
        Claims claims = tokenProvider.getClaims(token);
        return claims.get("user", HashMap.class);
    }
    private Map getMemberInfo (String token) {
        Claims claims = tokenProvider.getClaims(token);
        return claims.get("userinfo", HashMap.class);
    }

    public String createUserToken(String Id) {
        Optional<Members> FindUser = membersRepository.findById(Id);
        Members members = FindUser.orElseThrow(()->new UsernameNotFoundException("존재 하지 않는 회원 입니다"));
        Optional<MemberInfo> FindUserInfo = memberInfoRepository.findMemberInfoById(members);
        MemberInfo memberInfo = FindUserInfo.orElseThrow(()->new UsernameNotFoundException("존재 하지 않는 회원 입니다"));

        return tokenProvider.generateToken(members,memberInfo, Duration.ofHours(2));
    }
    public String tokenCheck(String token) {
        return String.valueOf(tokenProvider.getAuthentication(token));
    }

    public String getMembersToken (String token, String wantValue) {
        Map User = getMembers(token);
        Map UserInfo = getMemberInfo(token);

        if (User.get(wantValue) == null) return (String) UserInfo.get(wantValue);
        else return (String) User.get(wantValue);
    }
}
