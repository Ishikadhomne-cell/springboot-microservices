package com.embarkx.authService.service;

import com.embarkx.authService.entity.AppUser;
import com.embarkx.authService.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AppUserRepository repo;

    @Autowired
    AuthenticationManager authManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public AppUser register(AppUser appUser) {
        appUser.setPassword(encoder.encode(appUser.getPassword()));
        return  repo.save(appUser);

    }

    public String verify(AppUser appUser) {
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getUsername(),appUser.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(appUser.getUsername());
        }
        return "fail";
    }
}
