package com.web.ptitexam.security;

import com.web.ptitexam.constant.Constant;
import com.web.ptitexam.service.UserService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new CustomUserDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider authProvider(
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/**", "/css/**", "/image/**", "/register")
                        .permitAll()

                        // .requestMatchers("/teacher/**").hasAuthority(Constant.ROLE_TEACHER)
                        // .requestMatchers("/student/**").hasAuthority(Constant.ROLE_STUDENT)
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/" + Constant.PAGE_LOGIN)
                        .successHandler(customAuthenticationSuccessHandler())
                        .failureHandler(customAuthenticationFailureHandler())
                        .permitAll()

                )
                .exceptionHandling(ex -> ex.accessDeniedPage("/access-deny"))
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL để đăng xuất
                        .logoutSuccessUrl("/" + Constant.PAGE_LOGIN + "?logout") // Trang chuyển hướng sau khi đăng xuất
                                                                                 // thành công
                        .invalidateHttpSession(true) // Hủy phiên khi đăng xuất
                        .clearAuthentication(true) // Xóa thông tin xác thực
                        .deleteCookies("JSESSIONID") // Xóa cookie JSESSIONID
                        .permitAll())
                .sessionManagement(session -> session
                        // Cấu hình chính sách tạo session ở đây
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS));
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

}
