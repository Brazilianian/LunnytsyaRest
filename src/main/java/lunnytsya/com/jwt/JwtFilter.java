package lunnytsya.com.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.impl.DefaultHeader;
import lunnytsya.com.domain.User;
import lunnytsya.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtility jwtUtility;

    private final UserService userService;


    public JwtFilter(JwtUtility jwtUtility, UserService userService) {
        this.jwtUtility = jwtUtility;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String token;
        String username;

        try {
            if (null != authorization && authorization.startsWith("Bearer ")) {
                token = authorization.substring(7);
                username = jwtUtility.getUsernameFromToken(token);

                if (null != username && SecurityContextHolder.getContext().getAuthentication() == null) {
                    User user
                            = userService.loadUserByUsername(username);

                    if (user != null && jwtUtility.validateToken(token, user)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                                = new UsernamePasswordAuthenticationToken(user,
                                null, user.getAuthorities());

                        usernamePasswordAuthenticationToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );

                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            }
        } catch (ExpiredJwtException ex) {
            String isRefreshToken = request.getHeader("isRefreshToken");
            String requestURL = request.getRequestURL().toString();
            // allow for Refresh Token creation if following conditions are true.
            if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refresh-token")) {
                allowForRefreshToken(ex, request);
            } else{
                response.setHeader("exception", ex.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {

        // create a UsernamePasswordAuthenticationToken with null values.
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                null, null, null);
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        // Set the claims so that in controller we will be using it to create
        // new JWT
        request.setAttribute("claims", ex.getClaims());
    }
}