package com.embarkx.authService.service;

import com.embarkx.authService.entity.AppUser;
import com.embarkx.authService.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username);

        if (user == null){
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("user not found");
        }


        return new AppUserDetails(user); // returning your AppUserDetails class
    }
}
