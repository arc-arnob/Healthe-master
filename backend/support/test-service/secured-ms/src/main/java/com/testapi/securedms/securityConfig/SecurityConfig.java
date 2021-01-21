package com.testapi.securedms.securityConfig;

import com.testapi.securedms.security.JwtSecurityFilter;
import com.testapi.securedms.service.MyUserDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailService myUserDetailService;
    @Autowired
    private JwtSecurityFilter jwtSecurityFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http.csrf().disable() // csrf attack occurs when using sessions and cookies
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/api/testing")// ... by this permitAll()..
        .permitAll()
        .anyRequest() // ...else all other requests should be authenticated
        .authenticated(); // ... by this code.

        http.addFilterBefore(jwtSecurityFilter, //10 creating 1st error
                UsernamePasswordAuthenticationFilter.class); // This is checked first for checking
                                                        //... if jwt token is already present or not.
    }
        
}
