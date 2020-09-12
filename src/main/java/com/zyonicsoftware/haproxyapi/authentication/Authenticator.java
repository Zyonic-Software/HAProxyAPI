package com.zyonicsoftware.haproxyapi.authentication;

public class Authenticator {

    private final AuthConfig authConfig;

    public Authenticator(AuthConfig authConfig) {
        this.authConfig = authConfig;
    }

    public boolean isAllowed(String key) {
        return this.authConfig.getAuthorizedKeys().contains(key);
    }

}
