package com.tgd.trip.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class MemberAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        log.error("# Authentication failed : {}", exception.getMessage());
    }

    private String getErrorMessage(AuthenticationException exception) {
        if (exception instanceof UsernameNotFoundException) return "ACCOUNT_NOT_FOUND";
        if (exception instanceof BadCredentialsException) return "CREDENTIAL_NOT_FOUND";
        return "UNKNOWN_LOGIN_ERROR";
    }

}
