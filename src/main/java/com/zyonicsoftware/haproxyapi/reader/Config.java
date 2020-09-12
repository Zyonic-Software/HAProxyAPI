package com.zyonicsoftware.haproxyapi.reader;

public class Config {

    private int port;
    private String configLocation;
    private String haproxyReloadScriptName;
    private String haproxyReloadScriptLocation;

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setHaproxyReloadScriptLocation(String haproxyReloadScriptLocation) {
        this.haproxyReloadScriptLocation = haproxyReloadScriptLocation;
    }

    public void setHaproxyReloadScriptName(String haproxyReloadScriptName) {
        this.haproxyReloadScriptName = haproxyReloadScriptName;
    }

    public String getConfigLocation() {
        return configLocation;
    }

    public int getPort() {
        return port;
    }

    public String getHaproxyReloadScriptLocation() {
        return haproxyReloadScriptLocation;
    }

    public String getHaproxyReloadScriptName() {
        return haproxyReloadScriptName;
    }
}
