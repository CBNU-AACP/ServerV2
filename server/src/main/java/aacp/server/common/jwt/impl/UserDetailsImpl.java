package aacp.server.common.jwt.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private String userName;
    private String userPassword;
    private List<GrantedAuthority> authorities;

    public UserDetailsImpl(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.authorities = new ArrayList<GrantedAuthority>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 현재 서비스에는 권한이 존재하지 않아서 빈 리스트를 리턴
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
