package com.AuthenticationWithJWT.Authentication.service;



import com.AuthenticationWithJWT.Authentication.entities.RefreshToken;
import com.AuthenticationWithJWT.Authentication.payload.request.RefreshTokenRequest;
import com.AuthenticationWithJWT.Authentication.payload.response.RefreshTokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

import java.util.Optional;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(Long userId);
    RefreshToken verifyExpiration(RefreshToken token);
    Optional<RefreshToken> findByToken(String token);
    RefreshTokenResponse generateNewToken(RefreshTokenRequest request);
    ResponseCookie generateRefreshTokenCookie(String token);
    String getRefreshTokenFromCookies(HttpServletRequest request);
    void deleteByToken(String token);
    ResponseCookie getCleanRefreshTokenCookie();


}
