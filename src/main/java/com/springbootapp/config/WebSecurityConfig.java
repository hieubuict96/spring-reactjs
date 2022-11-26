package com.springbootapp.config;

import com.springbootapp.security.JWTAuthenticationEntryPoint;
import com.springbootapp.security.jwt.JWTConfigurer;
import com.springbootapp.security.jwt.TokenProvider;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final TokenProvider tokenProvider;
  private final JWTAuthenticationEntryPoint authenticationErrorHandler;

  public WebSecurityConfig(TokenProvider tokenProvider, JWTAuthenticationEntryPoint authenticationErrorHandler) {
    this.tokenProvider = tokenProvider;
    this.authenticationErrorHandler = authenticationErrorHandler;
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() { //đây là đoạn code để có thể tự động xác thực mật khẩu và mật khẩu được băm
    return new BCryptPasswordEncoder();
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**").antMatchers("/****");
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf().disable().exceptionHandling().authenticationEntryPoint(authenticationErrorHandler).and()
        .headers().frameOptions().sameOrigin().and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
        .anyRequest().permitAll()
        .and().apply(securityConfigurerAdapter());
  }

  private JWTConfigurer securityConfigurerAdapter() {
    return new JWTConfigurer(tokenProvider);
  }
}
