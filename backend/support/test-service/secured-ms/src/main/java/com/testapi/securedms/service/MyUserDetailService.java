package com.testapi.securedms.service;

import java.util.Optional;

import com.testapi.securedms.Repository.UserRepository;
import com.testapi.securedms.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
        .orElseThrow(()->new UsernameNotFoundException("User Not Found"));
        return new MyUserDetails(user);
    }

    
    
}
