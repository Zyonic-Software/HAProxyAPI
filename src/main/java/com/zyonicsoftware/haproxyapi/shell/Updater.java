package com.zyonicsoftware.haproxyapi.shell;

import com.zyonicsoftware.haproxyapi.main.HAProxyAPI;

import java.io.IOException;

public class Updater {

    private HAProxyAPI haProxyAPI;

    public Updater(HAProxyAPI haProxyAPI) {
        this.haProxyAPI = haProxyAPI;
    }

    public void updateHaproxy() throws IOException {
        String[] cmd = {"sh", haProxyAPI.getConfig().getHaproxyReloadScriptName(), haProxyAPI.getConfig().getHaproxyReloadScriptLocation()};

        Runtime.getRuntime().exec(cmd);
    }

}
