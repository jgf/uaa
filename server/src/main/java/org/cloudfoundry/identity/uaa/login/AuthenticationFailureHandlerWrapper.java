package org.cloudfoundry.identity.uaa.login;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFailureHandlerWrapper implements org.springframework.security.web.authentication.AuthenticationFailureHandler {
    private AuthenticationFailureHandler delegate;

    public AuthenticationFailureHandlerWrapper(AuthenticationFailureHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Cookie currentUserCookie = new Cookie("Current-User", "");
        currentUserCookie.setHttpOnly(false);
        currentUserCookie.setMaxAge(0);
        response.addCookie(currentUserCookie);

        delegate.onAuthenticationFailure(request, response, exception);
    }
}
