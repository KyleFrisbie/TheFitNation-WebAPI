package com.thefitnation.web.security;

import com.nimbusds.jwt.JWT;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class JWTToken implements Authentication {

    private static final long serialVersionUID = 1L;

    private JWT jwt;
    private final Collection<GrantedAuthority> authorities;
    private boolean authenticated;
    private ReadOnlyJWTClaimsSet claims;

    public JWTToken(JWT jwt) throws ParseException {
        this.jwt = jwt;
        List<String> roles;
        try {
            roles = jwt.getJWTClaimsSet().getStringListClaim("roles");
        } catch (ParseException e) {
            roles = new ArrayList<>();
        }
        List<GrantedAuthority> tmp = new ArrayList<>();
        if (roles != null) {
            for (String role : roles) {
                tmp.add(new SimpleGrantedAuthority(role));
            }
        }
        this.authorities = Collections.unmodifiableList(tmp);
        this.claims = jwt.getJWTClaimsSet();
        authenticated = false;
    }

    public JWT getJwt() {
        return jwt;
    }

    public ReadOnlyJWTClaimsSet getClaims() {
        return claims;
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return claims.getSubject();
    }

    @Override
    public String getName() {
        return claims.getSubject();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getDetails() {
        return claims.toJSONObject();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

}