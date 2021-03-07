package com.diagnosis_gateway.gateway.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtProvider {

    @Value("${jwt.authorities.key}")
    public String AUTHORITIES_KEY;

    private KeyStore keyStore;

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    @PostConstruct //Read about this annotation
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS"); // we want ot work with jks type 
            InputStream resourceAsStream = getClass().getResourceAsStream("/springjwt.jks"); //start getting the data from jks files
            keyStore.load(resourceAsStream, "password".toCharArray()); // load given we pass the correct password
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) { // handle exceptions
            throw new UsernameNotFoundException("Exception occurred while loading keystore", e);
        }               //Change This.
    }

    // This is not generating token for this service
    public String generateToken(Authentication authentication){

        User principal = (User)authentication.getPrincipal(); // This is purely theory based.
        return Jwts.builder()
                .setSubject(principal.getUsername()) //body
                .signWith(getPrivateKey()) // signed it with our private key
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact(); // converted to string
    }

    // This is used for refresh token in security context goes empty
    public String generateTokenWithUserName(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }


    // Used in jwtPovider of appointment service
    // Returning the private Key
    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("springjwt", "password".toCharArray()); // returing private key
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new UsernameNotFoundException("Exception occured while retrieving public key from keystore", e);
        }                   //Change this
    }
    // Used in jwtPovider of appointment service
    public boolean validateToken(String jwt) {
        System.out.println("Here JwtPRovider validating token"); //2
        Jwts.parser().setSigningKey(getPublickey()).parseClaimsJws(jwt);
        return true;
    }

    //  Used in jwtPovider of appointment service
    private PublicKey getPublickey() {
        System.out.println("Here Inside getPublicKey"); //3
        try {
            return keyStore.getCertificate("springjwt").getPublicKey();
        } catch (KeyStoreException e) {
            throw new UsernameNotFoundException("Exception occured while " +
                    "retrieving public key from keystore", e); //Change This
        }
    }

    // Used in jwtPovider of appointment service
    public String getUsernameFromJwt(String token) {
        System.out.println("Here inside getUsernameFromJwt"); //4
        Claims claims = Jwts.parser()
                .setSigningKey(getPublickey())
                .parseClaimsJws(token)
                .getBody();
        System.out.println("Claims.getsub:"+ claims.getSubject());
       // System.out.println("Claims.get Roles:"+ claims.get("roles")); //jwtProvider is working fine.
        return claims.getSubject();
    }

    public Collection<? extends GrantedAuthority> getAuthorityFromJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(getPublickey())
                .parseClaimsJws(token)
                .getBody();
        // System.out.println("Latest Error, getAuthJwt");
        return Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(",")) // will only work if claims with this key passed
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
    }

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }

}