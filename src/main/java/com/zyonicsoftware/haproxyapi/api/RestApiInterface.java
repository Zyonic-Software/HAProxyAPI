package com.zyonicsoftware.haproxyapi.api;

import com.zyonicsoftware.haproxyapi.main.HAProxyAPI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class RestApiInterface {

    public static HAProxyAPI haProxyAPI;
    public static File configFile;

    @PostMapping("/haproxy/api/registerport")
    @ResponseBody
    public ResponseEntity<String> registerNewPort(@RequestHeader final String key, final String proxyName, final String mode, final String clientTimeout, final String serverTimeout, int inputPort, final String destinationAlias, final int destinationPort, final String argument) {

        if(haProxyAPI.getAuthenticator().isAllowed(key)) {
            if(haProxyAPI.getIpAlias().destinationIsValid(destinationAlias)) {
                try {
                    if(!haProxyAPI.getProxyWriter().writeToConfig(configFile, proxyName, mode, clientTimeout, serverTimeout, inputPort, destinationAlias, destinationPort, argument)) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ \"status\" : \"port already used\" }");
                    }
                    haProxyAPI.getUpdater().updateHaproxy();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ \"status\" : \"destination invalid\" }");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{ \"status\" : \"key invalid \" }");
        }

        return ResponseEntity.status(HttpStatus.OK).body("{ \"status\" : handled }");
    }

    @PostMapping("/haproxy/api/deleteproxy")
    @ResponseBody
    public ResponseEntity<String> deletePort(@RequestHeader final String key, final String proxyName,final String destinationAlias) {

        if(haProxyAPI.getAuthenticator().isAllowed(key)) {
            try {
                haProxyAPI.getProxyWriter().removeProxyFromConfig(proxyName, destinationAlias, configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{ \"status\" : \"key invalid \" }");
        }

        return ResponseEntity.status(HttpStatus.OK).body("{ \"status\" : handled }");
    }

    @GetMapping("/haproxy/api/getproxys")
    @ResponseBody
    public ResponseEntity<String> getProxys(@RequestHeader final String key) {

        if(haProxyAPI.getAuthenticator().isAllowed(key)) {
            try {
                ArrayList<String> identifierStrings = haProxyAPI.getProxyWriter().getIdentifiers(configFile);
                StringBuilder responseBuilder = new StringBuilder();

                responseBuilder.append("{ \"proxys\" : [ ");

                AtomicInteger pos = new AtomicInteger(1);

                identifierStrings.forEach(identifierString -> {
                    String[] data = identifierString.split("--");
                    responseBuilder
                            .append("{ \"proxyName\" : \"").append(data[1]).append("\",")
                            .append("\"proxyDestination\" : \"").append(data[0].replace("#", "")).append("\",")
                            .append("\"inputPort\" : ").append(data[2]).append(",")
                            .append("\"destinationPort\" : ").append(data[3]).append(" } ");
                    if(pos.get() < identifierStrings.size()) {
                        responseBuilder.append(", ");
                    }
                    pos.getAndIncrement();
                });

                responseBuilder.append("] }");

                return ResponseEntity.status(HttpStatus.OK).body(responseBuilder.toString());

            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{ \"status\" : \"failed \" }");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{ \"status\" : \"key invalid \" }");
        }
    }

}
