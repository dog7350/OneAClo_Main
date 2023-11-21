package kr.kro.oneaclo.www.Configure;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SpringSecurityConfig {

    private final UserHandler userHandler;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(req->req
                                .requestMatchers("*/p/**").authenticated()
                                .requestMatchers("/**").permitAll()

                        );

        http
                .formLogin(login->login
                        .loginPage("/mypage/loginform")
                        .loginProcessingUrl("/login")
                        .usernameParameter("id")
                        .passwordParameter("pw")
                        .successHandler(userHandler)

                )
                .logout(logout->logout
                        .logoutUrl("/mypage/p/logout")
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/mypage/loginform"));


        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web)->web.ignoring()
                .requestMatchers("/common/**");
    }

}