package com.web.ptitexam.config;

import com.web.ptitexam.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Trả về quyền của người dùng từ vai trò (role)
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // Trả về mật khẩu đã mã hóa
    }

    @Override
    public String getUsername() {
        return user.getUsername(); // Trả về tên đăng nhập
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Tùy vào logic của bạn, trả về true nếu tài khoản không hết hạn
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Trả về true nếu tài khoản không bị khóa
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Trả về true nếu chứng chỉ không hết hạn
    }

    @Override
    public boolean isEnabled() {
        return true; // Trả về trạng thái của tài khoản
    }
}