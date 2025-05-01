package com.embarkx.authService.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authority;

    public Authority() {}

    public Authority(String authority) {
        this.authority = authority;
    }

    // getters and setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getAuthority() { return authority; }

    public void setAuthority(String authority) { this.authority = authority; }
}
