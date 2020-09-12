package com.zyonicsoftware.haproxyapi.main;

import com.zyonicsoftware.haproxyapi.authentication.AuthConfig;
import com.zyonicsoftware.haproxyapi.authentication.Authenticator;
import com.zyonicsoftware.haproxyapi.shell.Updater;
import com.zyonicsoftware.haproxyapi.reader.Config;
import com.zyonicsoftware.haproxyapi.reader.IpAlias;
import com.zyonicsoftware.haproxyapi.writer.ProxyWriter;

public class HAProxyAPI {

    private final Authenticator authenticator;
    private final Config config;
    private final Updater updater;
    private final IpAlias ipAlias;
    //private final HAConfigManager haConfigManager;
    private final ProxyWriter proxyWriter;

    public HAProxyAPI(Config config, AuthConfig authConfig, IpAlias ipAlias) {
        this.config = config;
        this.ipAlias = ipAlias;
        this.updater = new Updater(this);
        //this.haConfigManager = new HAConfigManager(this);
        this.proxyWriter = new ProxyWriter(this);

        this.authenticator = new Authenticator(authConfig);
    }


    public Config getConfig() {
        return config;
    }

    public Authenticator getAuthenticator() {
        return authenticator;
    }

    public Updater getUpdater() {
        return updater;
    }

    public IpAlias getIpAlias() {
        return ipAlias;
    }

    public ProxyWriter getProxyWriter() {
        return proxyWriter;
    }
}
