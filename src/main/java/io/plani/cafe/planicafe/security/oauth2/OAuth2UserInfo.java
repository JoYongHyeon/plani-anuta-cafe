package io.plani.cafe.planicafe.security.oauth2;

public interface OAuth2UserInfo {
    // 공급자 고유 ID (sub)
    String getId();
    String getEmail();
    String getName();
    String getPicture();
}
