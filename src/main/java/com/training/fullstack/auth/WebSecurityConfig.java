package com.training.fullstack.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@CrossOrigin(origins = "http://localhost:4200")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserPrincipalService userPrincipalService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurityConfig( UserPrincipalService userPrincipalService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userPrincipalService = userPrincipalService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests().antMatchers(
//                        "/*/signup" ,"/*/confirmRegistration",
//                        //allow swagger requests
//                        "/v2/api-docs", "/swagger-resources/configuration/ui", "/configuration/ui", "/swagger-resources", "/swagger-resources/configuration/security", "/configuration/security", "/swagger-ui.html", "/webjars/**"
//                        //allow h2 console requests
//                        ,"/h2-console","/h2-console/**","/**.ico")
                "/**")
                .permitAll();
//                .anyRequest().authenticated()
//                .and()
//                // this filter will be used for /login
//                .addFilter(new JwtAuthenticationLoginFilter(authenticationManager()))
//                .addFilter(new JwtAuthorisationFilter(authenticationManager(), userPrincipalService))
//                // this disables session creation on Spring Security
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http
//                .headers()
//                .frameOptions().sameOrigin()
//                .cacheControl();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userPrincipalService).passwordEncoder(bCryptPasswordEncoder);
    }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }
}
