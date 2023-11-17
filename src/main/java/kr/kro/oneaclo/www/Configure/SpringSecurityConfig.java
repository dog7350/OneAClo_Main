package kr.kro.oneaclo.www.Configure;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SpringSecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requet->requet
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("common/**","/mypage/joinform","/mypage/EmailCk","views/**","/",
                                "/mypage/join","/mypage/EmailCk","/mypage/IdCk","/mypage/EmailGetList","/mypage/SendMail","/mypage/jwtcreate").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login->login
                        .loginPage("/mypage/loginform")
                        .loginProcessingUrl("/login")
                        .usernameParameter("id")
                        .passwordParameter("pw")
                        .defaultSuccessUrl("/mypage/jwtcreate",true)
                        .permitAll()
                )
                .logout(logout->logout
                        .logoutUrl("/mypage/logout")
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/mypage/loginform"));

        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}