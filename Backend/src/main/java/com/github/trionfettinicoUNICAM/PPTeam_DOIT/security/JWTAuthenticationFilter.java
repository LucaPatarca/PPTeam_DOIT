package com.github.trionfettinicoUNICAM.PPTeam_DOIT.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.UserAdapter;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.UserEntity;
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

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
                                   TokenService tokenService
    ){
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
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
        String token = tokenService.create(authResult.getName());
        response.addHeader(tokenService.getHeaderName(), token);
        response.getWriter().write(new ObjectMapper().writeValueAsString(new UserEntity((UserAdapter) authResult.getPrincipal())));
    }
}
