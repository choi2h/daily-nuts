package com.dailynuts.security.service;

import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    UserDetails cookByLoginId(String loginId);
}