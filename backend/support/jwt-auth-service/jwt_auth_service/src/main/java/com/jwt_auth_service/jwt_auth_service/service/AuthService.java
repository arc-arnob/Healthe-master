package com.jwt_auth_service.jwt_auth_service.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.jwt_auth_service.jwt_auth_service.Repository.UserRepository;
import com.jwt_auth_service.jwt_auth_service.Repository.VerificationTokenRepository;
import com.jwt_auth_service.jwt_auth_service.dto.AuthenticationResponse;
import com.jwt_auth_service.jwt_auth_service.dto.LoginRequest;
import com.jwt_auth_service.jwt_auth_service.dto.RefreshTokenRequest;
import com.jwt_auth_service.jwt_auth_service.dto.RegisterRequest;
import com.jwt_auth_service.jwt_auth_service.exception.SpringException;
import com.jwt_auth_service.jwt_auth_service.model.NotificationEmail;
import com.jwt_auth_service.jwt_auth_service.model.Role;
import com.jwt_auth_service.jwt_auth_service.model.User;
import com.jwt_auth_service.jwt_auth_service.model.VerificationToken;
import com.jwt_auth_service.jwt_auth_service.security.JwtProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository verficationTokenRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private RefreshTokenService refreshTokenService;
    private Role role;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    public void signup(RegisterRequest registerRequest){ // This accepts the dto object...

        User user = new User(); // ... and creates a user

        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        user.setRole(registerRequest.getRole());

        userRepository.save(user);
        String token = generateVerificationToken(user); // generated token 
        mailService.sendMail(new NotificationEmail( // calls MailService(service)
            "Please Activate your account",
            user.getEmail(),
            "Click below" + " http://localhost:8082/api/auth/accVerification/" + token));
        
        


    }

    // Generate Verification Token and also saving it in database.
    public String generateVerificationToken(User user){
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verficationTokenRepository.save(verificationToken);

        return token;

    }

	public void verifyToken(String token) {

        Optional<VerificationToken> verificationToken = verficationTokenRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringException("Invalid Token")));

	}
    // Put a mechanism for checking iff role is doctor or not...
    // if role is doctor then enable if only token is verified and ...
    // doctor is registed
    private void fetchUserAndEnable(VerificationToken verificationToken) {

        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringException("cannot find "+ username));
        user.setEnabled(true);
        userRepository.save(user);

    }

    public AuthenticationResponse login(LoginRequest loginRequest){
        System.out.println("In login()");// working
        Authentication authenticate = authenticationManager //st1
                                    .authenticate(
                                        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                                        loginRequest.getPassword())); // This line is by default using UserRepository
        System.out.println("setting Security Context");
        SecurityContextHolder.getContext().setAuthentication(authenticate); // Returns Void
        System.out.println("generating jwt token");
        String token = jwtProvider.generateToken(authenticate);
        System.out.println("Returning Authentication Response");
        return AuthenticationResponse.builder().
                        authenticationToken(token)
                        .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                        .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                        .username(loginRequest.getUsername())
                        .build();
        
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());

        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new SpringException("User name not found - " + principal.getUsername()));
    }




}
