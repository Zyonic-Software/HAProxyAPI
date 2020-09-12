package com.zyonicsoftware.haproxyapi.authentication;

public class AuthConfig {

    private String authorizedKeys;

    public void setAuthorizedKeys(String authorizedKeys) {
        this.authorizedKeys = authorizedKeys;
    }

    public String getAuthorizedKeys() {
        return authorizedKeys;
    }
}
