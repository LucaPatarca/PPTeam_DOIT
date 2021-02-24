package com.github.trionfettinicoUNICAM.PPTeam_DOIT.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.UserAdapter;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.UserEntity;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final String secret;
    private final String headerName;
    private final String tokenPrefix;
    private final long expirationTime;

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
                                   String secret,
                                   String headerName,
                                   String tokenPrefix,
                                   long expirationTime
    ){
        this.authenticationManager = authenticationManager;
        this.secret = secret;
        this.headerName = headerName;
        this.tokenPrefix = tokenPrefix;
        this.expirationTime = expirationTime;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            UserEntity user = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
            response.addHeader("Access-Control-Expose-Headers", "Authorization");
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getMail(),
                            user.getSecret(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = JWT.create()
                .withSubject(((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(HMAC512(secret.getBytes()));
        response.addHeader(headerName, tokenPrefix + token);
        response.getWriter().write(new ObjectMapper().writeValueAsString(new UserEntity((UserAdapter) authResult.getPrincipal())));
    }
}
