package com.zyonicsoftware.haproxyapi.reader;

import java.util.HashMap;

public class IpAlias {

    private final HashMap<String, String> ipaddresses = new HashMap<>();

    public void addIpAddress(String alias, String address) {
        this.ipaddresses.put(alias, address);
    }

    public String getIpAdress(String alias) {
        return this.ipaddresses.get(alias);
    }

    public boolean destinationIsValid(String destination) {
        return this.ipaddresses.containsKey(destination);
    }
}
