package com.zyonicsoftware.haproxyapi.main;

import com.zyonicsoftware.haproxyapi.api.RestApiInterface;
import com.zyonicsoftware.haproxyapi.api.SpringApplication;
import com.zyonicsoftware.haproxyapi.authentication.AuthConfig;
import com.zyonicsoftware.haproxyapi.reader.Config;
import com.zyonicsoftware.haproxyapi.reader.ConfigReader;
import com.zyonicsoftware.haproxyapi.reader.IpAlias;

import java.io.File;

public class Initializer {

    public static void main(String[] args) {

        System.out.println(" __  __     ______     ______   ______     ______     __  __     __  __     ______     ______   __    \n" +
                "/\\ \\_\\ \\   /\\  __ \\   /\\  == \\ /\\  == \\   /\\  __ \\   /\\_\\_\\_\\   /\\ \\_\\ \\   /\\  __ \\   /\\  == \\ /\\ \\   \n" +
                "\\ \\  __ \\  \\ \\  __ \\  \\ \\  _-/ \\ \\  __<   \\ \\ \\/\\ \\  \\/_/\\_\\/_  \\ \\____ \\  \\ \\  __ \\  \\ \\  _-/ \\ \\ \\  \n" +
                " \\ \\_\\ \\_\\  \\ \\_\\ \\_\\  \\ \\_\\    \\ \\_\\ \\_\\  \\ \\_____\\   /\\_\\/\\_\\  \\/\\_____\\  \\ \\_\\ \\_\\  \\ \\_\\    \\ \\_\\ \n" +
                "  \\/_/\\/_/   \\/_/\\/_/   \\/_/     \\/_/ /_/   \\/_____/   \\/_/\\/_/   \\/_____/   \\/_/\\/_/   \\/_/     \\/_/ \n");

        SpringApplication springApplication = new SpringApplication();

        Config config = new Config();
        AuthConfig authConfig = new AuthConfig();
        IpAlias ipAlias = new IpAlias();

        ConfigReader configReader = new ConfigReader();

        configReader.loadApiConfig(config);
        configReader.loadAuthorizationKeys(authConfig);
        configReader.loadIpAlias(ipAlias);

        HAProxyAPI haProxyAPI = new HAProxyAPI(config, authConfig, ipAlias);

        springApplication.startSpring(config.getPort());

        RestApiInterface.haProxyAPI = haProxyAPI;
        RestApiInterface.configFile = new File(haProxyAPI.getConfig().getConfigLocation());
    }

}
