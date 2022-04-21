package com.fandom.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter{

    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
                                                throws AuthenticationException, IOException, ServletException {

        String account = request.getParameter("account");//user.getEmail();
        String password = request.getParameter("password");//user.getPassword();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(account, password, Collections.emptyList());

        return getAuthenticationManager().authenticate(token);
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                         Authentication authResult) throws IOException, ServletException {

       // System.out.println("authorities 2: "+ authResult.getAuthorities());

        TokenAuthenticationService.addAuthentication(response, authResult.getName(), authResult.getAuthorities());

        String authorizationString = response.getHeader("Authorization");
        System.out.println("Authorization String = " + authorizationString);
    }
}
