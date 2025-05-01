package com.embarkx.authService.repository;

import com.embarkx.authService.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    boolean existsByUsername(String username);

    AppUser findByUsername(String username);
}
