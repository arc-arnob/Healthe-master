package com.testapi.securedms.service;

import java.time.Instant;
import java.util.Collection;


import com.testapi.securedms.model.User;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


public class MyUserDetails implements UserDetails {


    private String username;
    private String password;
    private boolean enabled;
    private String email;
    private Instant createdAt;


    public MyUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.enabled = user.isEnabled();
        this.email = user.getEmail();
        this.createdAt = user.getCreated();
    }
    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return this.password;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return this.enabled;
    }

    public String email(){
        return this.email;
    }


    public Instant getCreatedAt() {
        return createdAt;
    }


    
    
     
}
