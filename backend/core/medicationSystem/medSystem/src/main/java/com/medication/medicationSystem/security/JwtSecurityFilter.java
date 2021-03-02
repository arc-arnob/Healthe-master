package com.medication.medicationSystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;



public class JwtSecurityFilter extends OncePerRequestFilter{
    
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwt = getJwtFromRequest(request);
        System.out.println("Here inside JwtFilter and jwt got is "+ jwt); // 6 //success
        if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) {
            String username = jwtProvider.getUsernameFromJwt(jwt);
            
            //final Collection<? extends GrantedAuthority> authorities = jwtProvider.getAuthorityFromJwt(jwt);
            
            // Checking authorities
            // for(int i=0;i<authorities.size();i++){
            //     System.out.println("printing authorities: "+authorities.iterator().next());
            // }




            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Debug
            System.out.println("&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&");
            System.out.println(userDetails.getUsername());
            System.out.println(userDetails.getPassword());
            System.out.println(userDetails.getAuthorities());
            System.out.println(userDetails.isEnabled());
            System.out.println("&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&");



            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());

            // System.out.println("jwtsecFilter: userdetail.getAuth"+userDetails.getAuthorities());
            
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            //System.out.println("authentication principal details"+ authentication.getPrincipal()); //Debug

            //SecurityContextHolder.clearContext();
            
            SecurityContextHolder.getContext().setAuthentication(authentication); 
            
            System.out.println("security context after clearing and resetting" + SecurityContextHolder.getContext()
             .getAuthentication()); //Debug
            
            //System.out.println("getAuthorities:" + userDetails.getAuthorities()); //Debug
            
            System.out.println("Leaving JwtFilter"); //Debug
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return bearerToken;
    }
    
}
