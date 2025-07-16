package com.dailynuts.security.jwt;

import com.dailynuts.member.entity.Member;
import com.dailynuts.member.entity.type.Role;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class JwtMember implements UserDetails {

    private final Member member;

    public JwtMember(Member member) {
        this.member = member;
    }

    public Long getId() {
        return member.getId();
    }

    public String getLoginId() {
        return member.getLoginId();
    }

    public String getName() {
        return member.getName();
    }

    public Enum<Role> getRole() {
        return member.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + member.getRole().name()));
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}