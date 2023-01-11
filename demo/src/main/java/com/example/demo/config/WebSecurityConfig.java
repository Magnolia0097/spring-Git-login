package com.example.demo.config;

import com.example.demo.security.*;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@Slf4j
public class WebSecurityConfig {


  @Autowired
  private TokenProvider tokenProvider;

  @Autowired
  private OAuthUserServiceImpl oAuthUserService; // 만든 OAuthUserServiceImpl 추가

  @Autowired
  private OAuthSuccessHandler oAuthSuccessHandler; // Success Handelr 추가

  @Autowired
  private  RedirectUrlCookieFilter redirectUrlCookieFilter;

  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception {
    // http  시큐리티 빌더
    http.cors() // WebMvcConfig 에서 이미 설정했으므로 기본 cors 설정
            .and()
            .csrf() // csrf 는 현재 사용하지 않으므로 disable
            .disable()
            .httpBasic() // token 을 사용하므로 basic 인증 disable
            .disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests() // /와 /auth/** 경로는 인증 안해도 됨
            .antMatchers("/","/auth/**","/oauth2/**").permitAll()
            .anyRequest() // /와 /auth/** 이외의 모든 경로는 인증해야됨.
            .authenticated()
            .and()
            .oauth2Login()
            .redirectionEndpoint()
            .baseUri("/oauth2/callback/*")//callback uri 설정; // oauth2Login 설정)
            .and()
            .authorizationEndpoint()
            .baseUri("/auth/authorize") //OAuth 2.0 흐름 시작을 위한 엔드포인트 추가요
            .and()
            .userInfoEndpoint()
            .userService(oAuthUserService) // OAuthUserServiceimpl 을 유저 서비스로 등록
            .and()
            .successHandler(oAuthSuccessHandler)
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(new Http403ForbiddenEntryPoint()); //Http403ForbiddenEntryPoint 추가



    // filter 등록
    // 매 요청마다
    // CorsFilter 실행한 후에
    // JwtAuthenticationFilter 실행한다
    http.addFilterAfter(
            new JwtAuthenticationFilter(tokenProvider),
            UsernamePasswordAuthenticationFilter.class
    );

    http.addFilterBefore( // Before
            redirectUrlCookieFilter,
            OAuth2AuthorizationRequestRedirectFilter.class // 리디렉트 되기 전에 필터를 실행해야 한다
             );
    return http.build();
  }

}
