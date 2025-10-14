package com.archivos.ecommerce.services;

import com.archivos.ecommerce.dtos.NewUserDto;
import com.archivos.ecommerce.entities.Role;
import com.archivos.ecommerce.entities.User;
import com.archivos.ecommerce.enums.RoleList;
import com.archivos.ecommerce.jwt.JwtUtil;
import com.archivos.ecommerce.repositories.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthService(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    public String authenticate(String email, String password){
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authResult = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authResult);
        String jwt = jwtUtil.generateToken(authResult);
        return jwtUtil.generateToken(authResult);
    }

    public void registerUser(NewUserDto newUserDto){
        if(userService.existsByEmail(newUserDto.getEmailAddress())) {
            throw new IllegalArgumentException("Email already exists");
        }

        Role roleUser = roleRepository.findByName(RoleList.ROLE_COMMON)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();
        user.setName(newUserDto.getName());
        user.setEmailAddress(newUserDto.getEmailAddress());
        user.setPassword(passwordEncoder.encode(newUserDto.getPassword()));
        user.setAddress(newUserDto.getAddress());
        user.setDpi(Long.parseLong(String.valueOf(newUserDto.getDpi())));
        user.setUserStatus(newUserDto.getUserStatus());
        user.setRole(roleUser);

        userService.save(user);
    }
}