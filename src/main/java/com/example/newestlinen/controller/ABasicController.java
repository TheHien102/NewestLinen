package com.example.newestlinen.controller;

import com.example.newestlinen.constant.LinenAConstant;
import com.example.newestlinen.intercepter.MyAuthentication;
import com.example.newestlinen.jwt.UserJwt;
import com.example.newestlinen.storage.model.Account;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class ABasicController {

    public Long getCurrentUserId() {
        try {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            MyAuthentication authentication = (MyAuthentication) securityContext.getAuthentication();
            return authentication.getJwtUser().getAccountId();
        } catch (Exception e) {
            return -1L;
        }
    }

    public Account getCurrentAdmin() {
        Account account = new Account();
        account.setId(getCurrentUserId());
        return account;
    }

    public UserJwt getSessionFromToken() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication) securityContext.getAuthentication();
        return authentication.getJwtUser();
    }

    public boolean isAdmin() {
        try {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            MyAuthentication authentication = (MyAuthentication) securityContext.getAuthentication();
            return Objects.equals(authentication.getJwtUser().getUserKind(), LinenAConstant.USER_KIND_ADMIN);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEmployee() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication) securityContext.getAuthentication();
        return Objects.equals(authentication.getJwtUser().getUserKind(), LinenAConstant.USER_KIND_EMPLOYEE);
    }

    public boolean isCollaborator() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication) securityContext.getAuthentication();
        return Objects.equals(authentication.getJwtUser().getUserKind(), LinenAConstant.USER_KIND_COLLABORATOR);
    }

    public boolean isCustomer() {
        try {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            MyAuthentication authentication = (MyAuthentication) securityContext.getAuthentication();
            return Objects.equals(authentication.getJwtUser().getUserKind(), LinenAConstant.USER_KIND_CUSTOMER);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSuperAdmin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication) securityContext.getAuthentication();
        return Objects.equals(authentication.getJwtUser().getUserKind(), LinenAConstant.USER_KIND_ADMIN) && authentication.getJwtUser().getIsSuperAdmin();
    }
}

