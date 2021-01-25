package com.redditb.reddit.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import com.redditb.reddit.dto.AuthenticationResponse;
import com.redditb.reddit.dto.LoginRequest;
import com.redditb.reddit.dto.RefreshTokenRequest;
import com.redditb.reddit.dto.RegisterRequest;
import com.redditb.reddit.service.AuthService;
import com.redditb.reddit.service.RefreshTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authservice;
    @Autowired
    private RefreshTokenService refreshTokenService;

    // request comes here /api/auth/signup --1
    @PostMapping(value="/signup") //       RegisterRequest unpacks json to pogo(dto/RegisterRequest) -- 2
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest){
        System.out.println("#$#%#T$#$T#$Exec signup()");
        authservice.signup(registerRequest); // 3. AuthService class is called(service/)
        return new ResponseEntity<>("User verified",HttpStatus.OK);
    }

    @GetMapping(value="/accVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
        authservice.verifyToken(token);
        return new ResponseEntity<String>("User Activated", HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        System.out.println("#$#%#T$#$T#$Exec login()"); // working till here
        return authservice.login(loginRequest);
    }

    @PostMapping(value = "/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authservice.refreshToken(refreshTokenRequest);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully!!");
    }
    
}
