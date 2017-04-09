package org.mov.model.auth.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:spring/security.properties")
public class JwtSettings {
    private Integer tokenExpirationTime;
    private Integer refreshTokenExpTime;
    private String tokenIssuer;
    private String tokenSigningKey;

    @Autowired
    public JwtSettings(Environment env) {
        tokenExpirationTime = env.getProperty("tokenExpirationTime", Integer.class);
        refreshTokenExpTime = env.getProperty("refreshTokenExpTime", Integer.class);
        tokenIssuer = env.getProperty("tokenIssuer");
        tokenSigningKey = env.getProperty("tokenSigningKey");
    }

    public Integer getTokenExpirationTime() {
        return tokenExpirationTime;
    }

    public void setTokenExpirationTime(Integer tokenExpirationTime) {
        this.tokenExpirationTime = tokenExpirationTime;
    }

    public Integer getRefreshTokenExpTime() {
        return refreshTokenExpTime;
    }

    public void setRefreshTokenExpTime(Integer refreshTokenExpTime) {
        this.refreshTokenExpTime = refreshTokenExpTime;
    }

    public String getTokenIssuer() {
        return tokenIssuer;
    }

    public void setTokenIssuer(String tokenIssuer) {
        this.tokenIssuer = tokenIssuer;
    }

    public String getTokenSigningKey() {
        return tokenSigningKey;
    }

    public void setTokenSigningKey(String tokenSigningKey) {
        this.tokenSigningKey = tokenSigningKey;
    }
}
