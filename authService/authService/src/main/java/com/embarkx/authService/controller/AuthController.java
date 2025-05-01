package com.embarkx.authService.controller;

import com.embarkx.authService.entity.AppUser;
import com.embarkx.authService.repository.AuthorityRepository;
import com.embarkx.authService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/hello")
    public String sayHello(){
        return  "Hello!";
    }

    @PostMapping("/register")
    public AppUser register(@RequestBody AppUser appUser) {
        return userService.register(appUser);
    }

    @PostMapping("/login")
    public String login(@RequestBody AppUser appUser) {
        return userService.verify(appUser);
    }
}
