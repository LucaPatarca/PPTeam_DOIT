package com.github.trionfettinicoUNICAM.PPTeam_DOIT.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final String secret;
    private final String headerName;
    private final String tokenPrefix;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
                                  String secret,
                                  String headerName,
                                  String tokenPrefix
    ) {
        super(authenticationManager);
        this.secret = secret;
        this.headerName = headerName;
        this.tokenPrefix = tokenPrefix;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(headerName);
        if(header == null || !header.startsWith(tokenPrefix)){
            chain.doFilter(request,response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){
        String token = request.getHeader(headerName);

        if(token != null){
            String user = JWT.require(Algorithm.HMAC512(secret.getBytes()))
                    .build()
                    .verify(token.replace(tokenPrefix,""))
                    .getSubject();
            if(user != null){
                return new UsernamePasswordAuthenticationToken(user,null,new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}
