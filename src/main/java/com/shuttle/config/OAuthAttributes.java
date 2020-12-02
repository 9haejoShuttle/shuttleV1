package com.shuttle.config;

import com.shuttle.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor @NoArgsConstructor
@Getter @Builder
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    /*
    *   OAuth2User에서 반환하는 사용자 정보는 Map이어서 직접 변환해서 사용한다.
    * */
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        OAuthAttributes newOAuthUser = OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String)attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();

        return newOAuthUser;
    }

    // TODO 현재 편의상 Role 기본값은 USER임. 나중에 직접 지정하는 것으로 변경할 수 있음
    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .build();
    }
}
