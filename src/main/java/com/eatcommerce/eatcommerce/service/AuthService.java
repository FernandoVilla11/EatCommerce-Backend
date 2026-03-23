package com.eatcommerce.eatcommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.eatcommerce.eatcommerce.DTO.LoginResponse;
import com.eatcommerce.eatcommerce.DTO.UserLoginResponse;
import com.eatcommerce.eatcommerce.entity.User;
import com.eatcommerce.eatcommerce.repository.UserRepository;
import com.eatcommerce.eatcommerce.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    public LoginResponse login(String userName, String password) {
    System.out.println("=== LOGIN ATTEMPT ===");
    System.out.println("userName: " + userName);
    System.out.println("password length: " + password.length());
    
    Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userName, password)
    );

    UserDetails userDetails = (UserDetails) auth.getPrincipal();
    User user = userRepository.findByUserName(userName).orElseThrow(() -> new RuntimeException("User not found"));
    UserLoginResponse userLoginResponse = new UserLoginResponse(user.getUserId(), user.getUserName(), user.getRole());

    return new LoginResponse(jwtUtil.generateToken(userDetails), userLoginResponse);
    }
}
