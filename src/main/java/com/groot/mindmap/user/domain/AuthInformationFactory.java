package com.groot.mindmap.user.domain;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.Assert;

import java.util.Map;

public enum AuthInformationFactory {
    GOOGLE {
        @Override
        public AuthInformation create(OAuth2User auth2User) {
            return new AuthInformation(
                    "google",
                    auth2User.getAttribute("name"),
                    auth2User.getAttribute("email"),
                    auth2User.getAttribute("picture"));
        }
    },
    KAKAO {
        @Override
        public AuthInformation create(OAuth2User auth2User) {
            Map<String, String> properties = auth2User.getAttribute("properties");
            Assert.notNull(properties, "properties can not be null");

            Map<String, String> accountInfos = auth2User.getAttribute("kakao_account");
            Assert.notNull(accountInfos, "accountInfos can not be null");

            return new AuthInformation(
                    "kakao",
                    properties.get("nickname"),
                    accountInfos.get("email"),
                    properties.get("profile_image"));
        }
    },
    NAVER {
        @Override
        public AuthInformation create(OAuth2User auth2User) {
            Map<String, String> response = auth2User.getAttribute("response");
            Assert.notNull(response, "response can not be null");

            return new AuthInformation(
                    "naver",
                    response.get("name"),
                    response.get("email"),
                    response.get("profile_image")
            );
        }
    };

    public abstract AuthInformation create(OAuth2User auth2User);
}
