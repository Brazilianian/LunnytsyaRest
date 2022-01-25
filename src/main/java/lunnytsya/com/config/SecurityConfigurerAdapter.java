package lunnytsya.com.config;

import lunnytsya.com.jwt.JwtFilter;
import lunnytsya.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private List<String> allowedOrigins = new ArrayList<String>() {{
        add("http://localhost:8080");
    }};
    private List<String> allowedMethods = new ArrayList<String>() {
        {
            add("GET");
            add("POST");
            add("PUT");
            add("DELETE");
            add("OPTIONS");
        }
    };
    private List<String> allowedHeaders = new ArrayList<String>() {{
        add("*");
    }};

    private final UserService userService;

    private final JwtFilter jwtFilter;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfigurerAdapter(UserService userService, JwtFilter jwtFilter, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtFilter = jwtFilter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> {
                    CorsConfiguration cors = new CorsConfiguration();
                    cors.setAllowedOrigins(this.allowedOrigins);
                    cors.setAllowedMethods(this.allowedMethods);
                    cors.setAllowedHeaders(this.allowedHeaders);
                    return cors;
                }).
                and().csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/v1/auth/**", "/api/v1/main-page/**", "/api/v1/product/**")
                .permitAll()
                .antMatchers("/api/v1/admin/**")
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }


}
