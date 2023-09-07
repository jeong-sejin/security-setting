package com.todo.securitySetting.Handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;

@Service("loginFailHandler")
public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler  {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                 AuthenticationException exception) throws IOException, ServletException{

        String errorCode;

        if (exception instanceof BadCredentialsException) {
            errorCode = "1";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            errorCode = "2";
        } else if (exception instanceof UsernameNotFoundException) {
            errorCode = "3";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            errorCode = "4";
        } else {
            errorCode = "5";
        }
        setDefaultFailureUrl("/login/loginForm?errorCode=" + errorCode);

        super.onAuthenticationFailure(request, response, exception);
    }
}
