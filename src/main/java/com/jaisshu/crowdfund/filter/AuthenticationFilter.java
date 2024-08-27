package com.jaisshu.crowdfund.filter;

import com.jaisshu.crowdfund.utility.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Component
public class AuthenticationFilter implements Filter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        final String authorizationHeader = httpRequest.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            try {
                String userId = jwtUtil.extractUserId(token);

                if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (jwtUtil.validateToken(token, userId)) {
                        // Load user details and authorities here
                        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                                userId,
                                null,
                                authorities
                        );

                        // Set the authentication in the SecurityContext
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        throw new ServletException("Invalid token");
                    }
                }
            } catch (Exception ex) {
                // Set the response status to 401 and write the error message
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.setContentType("application/json");

                PrintWriter writer = httpResponse.getWriter();
                writer.write("{\"error\": \"Unauthorized\", \"message\": \"" + ex.getMessage() + "\"}");
                writer.flush();
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
