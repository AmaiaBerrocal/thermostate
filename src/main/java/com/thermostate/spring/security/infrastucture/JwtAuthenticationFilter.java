package com.thermostate.spring.security.infrastucture;

import com.thermostate.spring.security.model.TokenService;
import com.thermostate.spring.security.model.LogedInUser;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws IOException, ServletException {
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
