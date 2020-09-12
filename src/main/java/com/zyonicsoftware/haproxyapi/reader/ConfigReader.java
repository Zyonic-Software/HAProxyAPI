package com.zyonicsoftware.haproxyapi.reader;

import com.zyonicsoftware.haproxyapi.authentication.AuthConfig;
import org.simpleyaml.configuration.file.YamlConfiguration;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.File;

public class ConfigReader {

    public void loadApiConfig(Config config) {

        try {

            final File file = new File("config.yml");

            if (!file.createNewFile()) {
                final YamlFile yamlFile = new YamlFile(file);
                yamlFile.load();

                config.setPort(yamlFile.getInt("connectionPort"));
                config.setConfigLocation(yamlFile.getString("haproxyConfigLocation"));
                config.setHaproxyReloadScriptName(yamlFile.getString("haproxyReloadScriptName"));
                config.setHaproxyReloadScriptLocation(yamlFile.getString("haproxyReloadScriptLocation"));


            } else {
                System.out.println("Config file not Found, fill out the config file!");

                final YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

                yamlConfiguration.createSection("port");
                yamlConfiguration.set("connectionPort", "56621");

                yamlConfiguration.createSection("haproxyConfigLocation");
                yamlConfiguration.set("haproxyConfigLocation", "/etc/haproxy/haproxy.cfg");

                yamlConfiguration.createSection("haproxyReloadScriptName");
                yamlConfiguration.set("haproxyReloadScriptName", "reload.sh");

                yamlConfiguration.createSection("haproxyReloadScriptLocation");
                yamlConfiguration.set("haproxyReloadScriptLocation", "/");

                yamlConfiguration.save(file);
            }
        } catch (Exception ignored) {}

    }


    public void loadAuthorizationKeys(AuthConfig authConfig) {
        try {

            final File file = new File("authconfig.yml");

            if (!file.createNewFile()) {
                final YamlFile yamlFile = new YamlFile(file);
                yamlFile.load();

                authConfig.setAuthorizedKeys(yamlFile.getString("authorizedKeys"));
            } else {
                System.out.println("Authentication Config file not Found, fill out the authconfig file!");

                final YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

                yamlConfiguration.createSection("authorizedKeys");

                yamlConfiguration.save(file);
            }
        } catch (Exception ignored) {}
    }

    public void loadIpAlias(IpAlias ipAlias) {
        try {

            final File file = new File("ipconfig.yml");

            if (!file.createNewFile()) {
                final YamlFile yamlFile = new YamlFile(file);
                yamlFile.load();

                String[] ips = yamlFile.getString("ipaddresses").split(",");

                for(String ip : ips) {
                    String[] alias = ip.split(";");
                    ipAlias.addIpAddress(alias[0], alias[1]);
                }
            } else {
                System.out.println("Authentication Config file not Found, fill out the authconfig file!");

                final YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

                yamlConfiguration.createSection("ipaddresses");

                yamlConfiguration.save(file);
            }
        } catch (Exception ignored) {}
    }
}
