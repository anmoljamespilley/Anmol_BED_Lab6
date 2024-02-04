package com.gl.studentmanagementsystem.model;

import lombok.Data;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Setter
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    // Constructors

    public User() {
        // Default constructor
    }

    public User(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = true;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role != null) {
            return Collections.singleton(role);
        } else {
            return Collections.emptySet();
        }
    }
    // Getters and setters

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // can implement account expiration logic if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // can implement account locking logic if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // can implement credential expiration logic if needed
    }
}