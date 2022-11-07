package com.example.newestlinen.controller;

import com.example.newestlinen.constant.LandingISConstant;
import com.example.newestlinen.intercepter.MyAuthentication;
import com.example.newestlinen.jwt.UserJwt;
import com.example.newestlinen.storage.model.Account;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class ABasicController {

    public long getCurrentUserId(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication)securityContext.getAuthentication();
        return authentication.getJwtUser().getAccountId();
    }

    public Account getCurrentAdmin() {
        Account account = new Account();
        account.setId(getCurrentUserId());
        return account;
    }

    public UserJwt getSessionFromToken(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication)securityContext.getAuthentication();
        return authentication.getJwtUser();
    }

    public boolean isAdmin(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication)securityContext.getAuthentication();
        return Objects.equals(authentication.getJwtUser().getUserKind(), LandingISConstant.USER_KIND_ADMIN);
    }

    public boolean isEmployee(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication)securityContext.getAuthentication();
        return Objects.equals(authentication.getJwtUser().getUserKind(), LandingISConstant.USER_KIND_EMPLOYEE);
    }

    public boolean isCollaborator(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication)securityContext.getAuthentication();
        return Objects.equals(authentication.getJwtUser().getUserKind(), LandingISConstant.USER_KIND_COLLABORATOR);
    }

    public boolean isCustomer(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication)securityContext.getAuthentication();
        return Objects.equals(authentication.getJwtUser().getUserKind(), LandingISConstant.USER_KIND_CUSTOMER);
    }

    public boolean isSuperAdmin(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication)securityContext.getAuthentication();
        return Objects.equals(authentication.getJwtUser().getUserKind(), LandingISConstant.USER_KIND_ADMIN) && authentication.getJwtUser().getIsSuperAdmin();
    }
}

