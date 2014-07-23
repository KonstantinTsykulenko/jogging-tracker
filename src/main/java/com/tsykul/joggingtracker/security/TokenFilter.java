package com.tsykul.joggingtracker.security;

import com.tsykul.joggingtracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;

/**
 * @author KonstantinTsykulenko
 * @since 7/14/2014.
 */
@Component
public class TokenFilter extends GenericFilterBean {

    private UserService userService;
    private TokenStorage tokenStorage;

    @Autowired
    public TokenFilter(UserService userService, TokenStorage tokenStorage) {
        this.userService = userService;
        this.tokenStorage = tokenStorage;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String tokenHeader = httpServletRequest.getHeader("Auth-Token");

        if (tokenHeader != null) {
            String username = TokenUtils.getUsername(tokenHeader);

            if (username != null &&
                    tokenHeader.equals(tokenStorage.getToken(username))) {

                UserDetails userDetails = userService.loadUserByUsername(username);

                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                                    Collections.<GrantedAuthority>emptyList());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        chain.doFilter(request, response);
    }
}
