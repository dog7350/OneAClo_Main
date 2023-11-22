package kr.kro.oneaclo.www.Configure;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import jakarta.transaction.Transactional;
import kr.kro.oneaclo.www.Common.TokenProcess;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import kr.kro.oneaclo.www.Repository.Mypage.MembersRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;


@Component
@RequiredArgsConstructor
@Transactional
public class UserHandler implements AuthenticationSuccessHandler {

    private final MembersRepository membersRepository;
    private final TokenProcess tokenProcess;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String UserId = request.getParameter("id");
        Optional<Members> result = membersRepository.findById(UserId);
        Members members = result.orElseThrow();
        HttpSession session = request.getSession();
        if(members.getActive().equals("f")) {
            session.invalidate();
            response.sendRedirect("/mypage/BlockUser");
        }else {
            String UserToken = tokenProcess.createUserToken(UserId);
            session.setAttribute("UserInfo", UserToken);
            response.sendRedirect("/");
        }
    }
}
