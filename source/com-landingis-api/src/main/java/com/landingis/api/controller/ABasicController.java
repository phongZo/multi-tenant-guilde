package com.landingis.api.controller;

import com.landingis.api.dto.ErrorCode;
import com.landingis.api.exception.RequestException;
import com.landingis.api.storage.master.model.Account;
import com.landingis.api.constant.LandingISConstant;
import com.landingis.api.intercepter.MyAuthentication;
import com.landingis.api.jwt.UserJwt;
import com.landingis.api.storage.master.model.Auditable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class ABasicController {

    public long getCurrentUserId(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication)securityContext.getAuthentication();
        return authentication.getJwtUser().getAccountId().longValue();
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

    public void checkActive(Auditable auditable) {
        if(auditable == null) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_FOUND);
        } else if(!auditable.getStatus().equals(LandingISConstant.STATUS_ACTIVE)) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_INACTIVE);
        }
    }
}

