package com.jwt_auth_service.jwt_auth_service.service;

import java.time.Instant;
import java.util.UUID;

import com.jwt_auth_service.jwt_auth_service.Repository.RefreshTokenRepository;
import com.jwt_auth_service.jwt_auth_service.exception.SpringException;
import com.jwt_auth_service.jwt_auth_service.model.RefreshToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    void validateRefreshToken(String token) {
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new SpringException("Invalid refresh Token"));
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
