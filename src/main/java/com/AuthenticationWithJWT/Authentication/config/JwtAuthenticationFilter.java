package com.AuthenticationWithJWT.Authentication.config;

import com.AuthenticationWithJWT.Authentication.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Retrieve the token from the Authorization header
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String username = null;

        try {
            // Check if the Authorization header is valid
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                jwtToken = requestTokenHeader.substring(7); // Extract the token (remove "Bearer ")
                username = jwtService.extractUserName(jwtToken); // Extract username
            } else {
                logger.warn("Authorization header is missing or invalid: {}", requestTokenHeader);
            }

            // Handle expired token
            if (jwtToken != null && username == null) {
                throw new IllegalArgumentException("Invalid token: Unable to extract username.");
            }
        } catch (ExpiredJwtException e) {
            logger.warn("JWT Token expired: {}", e.getMessage());
            sendErrorResponse(response, request.getRequestURI(), "Le token a expiré", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (Exception e) {
            logger.error("Error processing JWT token: {}", e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        // Validate the username and set the authentication context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set authentication in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                logger.info("User authenticated successfully: {}", username);
            } else {
                logger.warn("Invalid JWT token for user: {}", username);
            }
        }

        // Proceed with the filter chain
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        boolean shouldSkip = path.startsWith("/chat/send"); // Add paths to skip if needed
        logger.info("Request URI: {}, Should Skip Filter: {}", path, shouldSkip);
        return shouldSkip;
    }

    /**
     * Sends a structured JSON error response.
     *
     * @param response HttpServletResponse object
     * @param path     Request URI
     * @param message  Error message
     * @param status   HTTP status code
     */
    private void sendErrorResponse(HttpServletResponse response, String path, String message, int status) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write(
                "{\"path\": \"" + path + "\", " +
                        "\"message\": \"" + message + "\", " +
                        "\"timestamp\": " + System.currentTimeMillis() + ", " +
                        "\"status\": " + status + " }"
        );
    }
}
