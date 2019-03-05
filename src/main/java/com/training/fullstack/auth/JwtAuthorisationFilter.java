package com.training.fullstack.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.training.fullstack.auth.Constants.HEADER_STRING;
import static com.training.fullstack.auth.Constants.TOKEN_PREFIX;

public class JwtAuthorisationFilter extends BasicAuthenticationFilter {

    private UserPrincipalService userPrincipalService;

    public JwtAuthorisationFilter(AuthenticationManager authManager, UserPrincipalService userPrincipalService) {
        super(authManager);
        this.userPrincipalService = userPrincipalService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX, "");
        if (token != null) {
            // parse the token.
            String  username = JwtTokenUtil.getUsernameFromToken(token);
            UserDetails userPrincipal = userPrincipalService.loadUserByUsername(username);
            if (username != null) {
                return new UsernamePasswordAuthenticationToken(username, userPrincipal.getPassword(), userPrincipal.getAuthorities());
            }
            return null;
        }
        return null;
    }
}