package com.redditb.reddit.config;

import com.redditb.reddit.security.JwtSecurityFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;

// This class holdes the complete security configution of our web app backend
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    
    private JwtSecurityFilter jwtSecurityFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();   // This is used on AuthService() as...
                                                //... authenticationManager is an interface...
                                                //...and have many impls therefore declaring bean...
                                                //... is a must.
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      
        http.csrf().disable() // csrf attack occurs when using sessions and cookies
        .authorizeRequests()
        .antMatchers("/api/auth/**") // all request from this api should be allowed ...
        .permitAll() 
        .antMatchers(HttpMethod.GET, "/api/securedController")// ... by this permitAll()..
        .permitAll()
        .anyRequest() // ...else all other requests should be authenticated
        .authenticated(); // ... by this code.

        http.addFilterBefore(jwtSecurityFilter, //10 creating 1st error
                UsernamePasswordAuthenticationFilter.class); // This is checked first for checking
                                                        //... if jwt token is already present or not.
    }

    // Authenthication manager builder for login purpose
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{

        authenticationManagerBuilder.userDetailsService(userDetailsService) // I need to centralize this
            .passwordEncoder(passwordEncoder());

    }

    
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    
}
