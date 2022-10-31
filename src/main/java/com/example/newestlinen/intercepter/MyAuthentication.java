package com.example.newestlinen.intercepter;

import com.example.newestlinen.jwt.UserJwt;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class MyAuthentication implements Authentication {
    private UserJwt jwtUser;

    public MyAuthentication(UserJwt jwtUser) {
        this.jwtUser = jwtUser;
    }

    public void setJwtUser(UserJwt jwtUser) {
        this.jwtUser = jwtUser;
    }


    public UserJwt getJwtUser() {
        return jwtUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean b) {
        // Do nothing because of X and Y.
    }

    @Override
    public String getName() {
        if(jwtUser!=null){
            return jwtUser.getUsername();
        }
        return null;
    }
}
