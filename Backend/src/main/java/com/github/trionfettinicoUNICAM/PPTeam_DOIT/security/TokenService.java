package com.github.trionfettinicoUNICAM.PPTeam_DOIT.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
public class TokenService {
    @Value(value = "${oauth.secret}")
    private String secret;
    @Value(value = "${oauth.headerName}")
    private String headerName;
    @Value(value = "${oauth.tokenPrefix}")
    private String tokenPrefix;
    @Value(value = "${oauth.expirationTime}")
    private long expirationTime;

    public String create(String subject){
        return this.tokenPrefix + JWT.create()
                .withSubject(subject)
                .withExpiresAt(getExpirationDate())
                .sign(HMAC512(secret.getBytes()));
    }

    public String getSubject(String token) throws JWTVerificationException{
        return JWT.require(Algorithm.HMAC512(secret.getBytes()))
                .build()
                .verify(token.replace(tokenPrefix,""))
                .getSubject();
    }

    public boolean isValid(String token){
        try{
            getSubject(token.replace(tokenPrefix,""));
            return true;
        }catch (JWTVerificationException e){
            return false;
        }
    }

    public String refresh(String token){
        String subject = getSubject(token);
        return create(subject);
    }

    public String getHeaderName() {
        return headerName;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    private Date getExpirationDate(){
        return new Date(System.currentTimeMillis() + expirationTime);
    }
}
