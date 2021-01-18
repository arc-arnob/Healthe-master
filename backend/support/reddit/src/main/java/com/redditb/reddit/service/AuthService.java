package com.redditb.reddit.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.redditb.reddit.dto.AuthenticationResponse;
import com.redditb.reddit.dto.LoginRequest;
import com.redditb.reddit.dto.RegisterRequest;
import com.redditb.reddit.exceptions.SpringRedditException;
import com.redditb.reddit.model.NotificationEmail;
import com.redditb.reddit.model.User;
import com.redditb.reddit.model.VerificationToken;
import com.redditb.reddit.repository.UserRepository;
import com.redditb.reddit.repository.VerficationTokenRepository;
import com.redditb.reddit.security.JwtProvider;

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
    private VerficationTokenRepository verficationTokenRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    public void signup(RegisterRequest registerRequest){ // This accepts the dto object...

        User user = new User(); // ... and creates a user

        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);
        String token = generateVerificationToken(user); // generated token 
        mailService.sendMail(new NotificationEmail( // calls MailService(service)
            "Please Activate your account",
            user.getEmail(),
            "Click below" + "http://localhost:8082/api/auth/accVerification" + token));
        
        


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
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token")));

	}

    private void fetchUserAndEnable(VerificationToken verificationToken) {

        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("cannot find "+ username));
        user.setEnabled(true);
        userRepository.save(user);

    }

    public AuthenticationResponse login(LoginRequest loginRequest){
        System.out.println("#$#%#T$#$T#$Exec AuthSer->login()");// working
        Authentication authenticate = authenticationManager //st1
                                    .authenticate(
                                        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                                        loginRequest.getPassword()));
        System.out.println("#$#%#T$#$T#$Exec AuthSer->login(2)");
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        System.out.println("#$#%#T$#$T#$Exec AuthSer->login(3)");
        String token = jwtProvider.generateToken(authenticate);
        System.out.println("#$#%#T$#$T#$Exec AuthSer->login(4)");
        return new AuthenticationResponse(token, loginRequest.getUsername());
        
    }




}