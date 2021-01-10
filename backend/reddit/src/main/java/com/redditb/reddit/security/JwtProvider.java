package com.redditb.reddit.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import com.redditb.reddit.exceptions.SpringRedditException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

@Service
public class JwtProvider {

    private KeyStore keyStore;
    @PostConstruct //Read about this annotation
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS"); // we want ot work with jks type 
            InputStream resourceAsStream = getClass().getResourceAsStream("/springjwt.jks"); //start getting the data from jks files
            keyStore.load(resourceAsStream, "password".toCharArray()); // load given we pass the correct password
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) { // handle exceptions
            throw new SpringRedditException("Exception occurred while loading keystore", e);
        }
    }

    
    public String generateToken(Authentication authentication){

        User principal = (User)authentication.getPrincipal(); // This is purely theory based.
        return Jwts.builder()
                .setSubject(principal.getUsername()) // body
                .signWith(getPrivateKey()) // signed it with our private key
                .compact(); // converted to string
    }

    // Returning the private Key
    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("springjwt", "password".toCharArray()); // returing private key
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new SpringRedditException("Exception occured while retrieving public key from keystore", e);
        }
    }
}
