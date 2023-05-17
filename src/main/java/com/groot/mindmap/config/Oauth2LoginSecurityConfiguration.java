package com.groot.mindmap.config;

import com.groot.mindmap.user.domain.AuthInformation;
import com.groot.mindmap.user.domain.AuthInformationFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.Assert;

@Slf4j
@Configuration
@EnableWebSecurity
public class Oauth2LoginSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authorization -> authorization
                                .baseUri("/oauth2/authorization")
                .authorizationRequestRepository(authorizationRequestRepository()))
                .redirectionEndpoint(redirection -> redirection
                        .baseUri("/*/oauth2/code/*"))
                .userInfoEndpoint(userInfo -> userInfo
                        .userService(new DefaultOAuth2UserService()))
                .successHandler(oAuth2AuthenticationSuccessHandler())
                .failureHandler(oAuth2AuthenticationFailureHandler()))
                .build();
    }

    private AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }

    private AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            OAuth2User auth2User = (DefaultOAuth2User) authentication.getPrincipal();

            // Get the platform name
            String platform = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
            Assert.notNull(platform, "platform can not be null");

            AuthInformation information = AuthInformationFactory.valueOf(platform.toUpperCase()).create(auth2User);

            response.sendRedirect("/login?success=true");
        };
    }

    private AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return (request, response, exception) -> {
            response.sendRedirect("/login?error=true");
        };
    }
}
