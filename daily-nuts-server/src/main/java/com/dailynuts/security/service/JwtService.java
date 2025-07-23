package com.dailynuts.security.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    UserDetails convertToUserDetails(String loginId);
}