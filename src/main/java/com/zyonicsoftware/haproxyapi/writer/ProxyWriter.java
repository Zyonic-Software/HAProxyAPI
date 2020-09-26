package com.zyonicsoftware.haproxyapi.writer;

import com.zyonicsoftware.haproxyapi.main.HAProxyAPI;

import java.io.*;
import java.util.ArrayList;

public class ProxyWriter {

    private final HAProxyAPI haProxyAPI;

    public ProxyWriter(HAProxyAPI haProxyAPI) {
        this.haProxyAPI = haProxyAPI;
    }

    public boolean writeToConfig(final File configFile, final String proxyName, final String mode, final String clientTimeout, final String serverTimeout, int inputPort, final String destinationAlias, final int destinationPort, final String argument) throws IOException {

        String content = this.getContent(proxyName, destinationAlias, configFile);

        if(content.contains(":" + inputPort + "#")) {
            return false;
        }

        content = content + "#" + destinationAlias + "--" + proxyName + "--" + inputPort + "--" + destinationPort + "======================================================================================================\n" +
                "frontend " + proxyName + "\n" +
                        "\tmode " + mode + "\n" +
                        "\ttimeout client " + clientTimeout + "\n" +
                        "\tbind *:" + inputPort + "#\n" +
                        "\tdefault_backend " + proxyName + "\n\n" +
                "backend " + proxyName + "\n" +
                        "\tmode " + mode + "\n" +
                        "\ttimeout server " + serverTimeout + "\n" +
                        "\tserver main " + this.haProxyAPI.getIpAlias().getIpAdress(destinationAlias) + ":" + destinationPort + " " + argument + " #";

        FileWriter fileWriter = new FileWriter(configFile);


        fileWriter.write(content);
        fileWriter.close();

        return true;
    }

    public void removeProxyFromConfig(final String proxyName, final String destinationAlias, final File configFile) throws IOException {

        String content = this.getContent(proxyName, destinationAlias, configFile);

        FileWriter fileWriter = new FileWriter(configFile);

        fileWriter.write(content);
        fileWriter.close();
    }

    private String getContent(String proxyName, String destinationAlias, File configFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(configFile));
        String line = reader.readLine();

        StringBuilder configContentBuilder = new StringBuilder();

        int amountOfLinesToSkip = 10;

        String identifierString = this.getIdentifierString(proxyName, destinationAlias, configFile);


        while (line != null && !line.equals(identifierString)) {
            configContentBuilder.append(line).append(System.lineSeparator());
            line = reader.readLine();
        }


        if (line != null) {
            for (int i = 0; i < amountOfLinesToSkip; i++) {
                reader.readLine();
            }
        }

        while (line != null) {
            configContentBuilder.append(line).append(System.lineSeparator());
            line = reader.readLine();
        }
        reader.close();

        return configContentBuilder.toString().replace(identifierString, "");
    }

    private String getIdentifierString(final String proxyName, final String destinationAlias, final File configFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(configFile));
        String line = reader.readLine();

        while (line != null) {
            if (line.startsWith("#" + destinationAlias + "--" + proxyName)) {
                return line;
            }
            line = reader.readLine();
        }
        return "NoMatch";
    }

    public ArrayList<String> getIdentifiers(final File configFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(configFile));
        String line = reader.readLine();
        ArrayList<String> identifiers = new ArrayList<>();

        while (line != null) {
            if (line.startsWith("#") && line.contains("--") && line.contains("======================================================================================================")) {
                identifiers.add(line.replace("======================================================================================================", ""));
            }
            line = reader.readLine();
        }
        return identifiers;
    }
}