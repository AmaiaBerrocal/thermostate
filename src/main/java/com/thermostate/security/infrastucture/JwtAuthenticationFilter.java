package com.thermostate.security.infrastucture;

import com.thermostate.security.model.TokenService;
import com.thermostate.security.model.LogedInUser;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    public JwtAuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (headerIsInvalid(header)) {
            filterChain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken token = createToken(header);
        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(request, response);
    }

    private boolean headerIsInvalid(String authorizationHeader) {
        return authorizationHeader == null || !authorizationHeader.startsWith("Bearer ");
    }

    private UsernamePasswordAuthenticationToken createToken(String authorizationHeader) {
        LogedInUser userPrincipal = tokenService.parseToken(authorizationHeader);
        return new UsernamePasswordAuthenticationToken(userPrincipal, userPrincipal, List.of());
    }
}
