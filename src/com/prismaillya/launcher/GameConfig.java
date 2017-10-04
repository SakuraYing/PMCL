package com.prismaillya.launcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jiang
 */
public class GameConfig {

    public void change(String key, String value) {
        String change1 = template().replace("${" + key + "}", value);
        String port = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "port");
        String hostname = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "hostname");
        String defaultpage = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "defaultpage");
        String playername = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "playername");
        String playerpass = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "playerpass");
        String javapath = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "javapath");
        String maxram = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "maxram");
        String autoserver = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "autoserver");
        String autoport = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "autoport");
        String backgroundimage = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "backgroundimage");
        String lastestversion = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "lastestversion");
        String lastestlogin = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "lastestlogin");
        String selectversion = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "selectversion");
        String selectlogintype = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "selectlogintype");
        String writestr = change1.replace("${port}", port)
                .replace("${hostname}", hostname)
                .replace("${defaultpage}", defaultpage)
                .replace("${playername}", playername)
                .replace("${playerpass}", playerpass)
                .replace("${javapath}", javapath)
                .replace("${maxram}", maxram)
                .replace("${autoserver}", autoserver)
                .replace("${autoport}", autoport)
                .replace("${backgroundimage}", backgroundimage)
                .replace("${lastestversion}", lastestversion)
                .replace("${lastestlogin}", lastestlogin)
                .replace("${selectversion}", selectversion)
                .replace("${selectlogintype}", selectlogintype);
        filewrite.write("PMCL_HTTP.json", writestr);
    }

    private String template() {
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("Config_tmp.json"), "utf-8");
            try (BufferedReader br = new BufferedReader(isr)) {
                String lineTxt = null;
                String config = "";
                while ((lineTxt = br.readLine()) != null) {
                    config += lineTxt + "\n";
                }
                return config;
            } catch (IOException ex) {
                Logger.getLogger(GameConfig.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GameConfig.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
