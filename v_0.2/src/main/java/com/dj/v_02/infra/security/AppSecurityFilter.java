package com.dj.v_02.infra.security;

import com.dj.v_02.infra.jwt.JwtService;
import com.dj.v_02.user.User;
import com.dj.v_02.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AppSecurityFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    public AppSecurityFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");
            String username = jwtService.getSubject(token);
            if (username != null) {
                User user = userRepository.findByUsername(username);
                if (user != null) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
