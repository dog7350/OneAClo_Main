package kr.kro.oneaclo.www.Service.Mypage;

import kr.kro.oneaclo.www.Entity.Mypage.Members;
import kr.kro.oneaclo.www.Repository.Mypage.MembersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final MembersRepository membersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Members> FindUser = membersRepository.findById(username);
        Members members = FindUser.orElseThrow(()->new UsernameNotFoundException("존재 하지 않는 회원 입니다"));
        return User.builder()
                .username(members.getId())
                .password(members.getPw())
                .roles("user")
                .build();
    }
}
