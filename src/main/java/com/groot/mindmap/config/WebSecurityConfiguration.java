package com.groot.mindmap.config;

import com.groot.mindmap.user.domain.AuthInformation;
import com.groot.mindmap.user.domain.AuthInformationFactory;
import com.groot.mindmap.user.domain.User;
import com.groot.mindmap.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Optional;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    private final UserService userService;

    @Value("${login.success.redirect-uri}")
    private String successUrl;

    @Value("${login.failure.redirect-uri}")
    private String failureUrl;

    @Value("${allowed.origin.url}")
    private String allowedOrigin;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .headers()
                    .frameOptions().disable()
                    .contentTypeOptions().disable()
                .and()
//                .oauth2Login(oauth2 -> oauth2
//                        .authorizationEndpoint(authorization -> authorization
//                                .baseUri("/oauth2/authorization")
//                                .authorizationRequestRepository(authorizationRequestRepository()))
//                        .redirectionEndpoint(redirection -> redirection
//                                .baseUri("/*/oauth2/code/*"))
//                        .userInfoEndpoint(userInfo -> userInfo
//                                .userService(new DefaultOAuth2UserService()))
//                        .successHandler(oAuth2AuthenticationSuccessHandler())
//                        .failureHandler(oAuth2AuthenticationFailureHandler()))
                .authorizeHttpRequests()
//                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                    .anyRequest().permitAll()
                .and()
                .cors()
                .and()
                .csrf().disable()
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin(allowedOrigin);
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }

    private AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            AuthInformation information = AuthInformationFactory.of(authentication);
            Optional<User> user = userService.findByPlatformAndEmail(information.platform(), information.email());
            if (user.isEmpty()) {
                userService.create(information);
            }

            // TODO JWT 생성 후 헤더에 넣어 반환하는 로직 추가

            response.sendRedirect(successUrl);
        };
    }

    private AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return (request, response, exception) -> {
            response.sendRedirect(failureUrl);
        };
    }
}
