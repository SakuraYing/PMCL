/*
 * 版权所有(C)Niconico Craft 保留所有权利
 * 您不得在未经作者许可的情况下，擅自发布本软件的任何部分或全部内容
 * 否则将会追究二次发布者的法律责任
 */
package com.prismaillya.launcher;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import static com.prismaillya.launcher.PMCL_Main.log;
import java.io.IOException;
import java.util.UUID;

/**
 *
 * @author jiang
 *
 * 返回码： 200 正常启动 403 登陆失败 404 无法加载Json
 *
 */
public class Launcher {

    public static int run(String Javapath, String Player, String Password, String version, String Maxram, String GamePath, boolean onlineMode) {
        String VersionPath = GamePath + "/versions/";
        String GameJson = VersionPath + version + "/" + version + ".json";
        String islibraries = new MinecraftJsonReader().getJson(GameJson, "mainClass");
        String IndexId = new MinecraftJsonReader().getJson(GameJson, "assets");
        String autoserver = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "autoserver");
        String autoport = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "autoport");
        String uuid = getuuid();
        String gameName = Player;
        String Token = uuid;
        if (onlineMode) {
            try {
                String loginBack = new MojangAuth().login(Player, Password);
                JsonParser parse = new JsonParser();
                try {
                    if ("403".equals(loginBack)) {
                        return 403;
                    }
                    JsonObject json = (JsonObject) parse.parse(loginBack);
                    JsonObject profile = json.get("selectedProfile").getAsJsonObject();
                    uuid = profile.get("id").getAsString();
                    gameName = profile.get("name").getAsString();
                    Token = json.get("accessToken").getAsString();
                    if (islibraries.equals("net.minecraft.client.main.Main")) {
                        log("版本\"" + version + "\"为纯净版");
                        String UserAgent = new MinecraftJsonReader().getJson(GameJson, "minecraftArguments")
                                .replace("${auth_player_name}", gameName)
                                .replace("${version_name}", "\"Prismaillya\"")
                                .replace("${game_directory}", GamePath)
                                .replace("${assets_root}", GamePath + "/assets")
                                .replace("${assets_index_name}", IndexId)
                                .replace("${auth_uuid}", uuid)
                                .replace("${auth_access_token}", Token)
                                .replace("${user_properties}", "{}")
                                .replace("${user_type}", "Legacy")
                                .replace("${auth_session}", uuid)
                                .replace("${game_assets}", GamePath + "/assets")
                                .replace("\r\n", "")
                                .replace("${version_type}", "\"PMCL-RiOS7.3\"");
                        String serveragent = "";
                        if (!"".equals(autoserver)) {
                            if ("".equals(autoport)) {
                                autoport = "25565";
                            }
                            serveragent = "--server " + autoserver + " --port " + autoport;
                        }
                        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                            String command = new libloader().load(true, version, GamePath, Javapath, Maxram)
                                    + new MinecraftJsonReader().getLauncher(VersionPath + version + "/" + version + ".json", GamePath).replaceAll("/", "\\\\")
                                    + VersionPath.replaceAll("/", "\\\\") + version + "\\\\" + version + ".jar " + islibraries + " "
                                    + UserAgent.replaceAll("/", "\\\\") + " --height 480 --width 842" + serveragent;
                            //log(command);
                            Thread thread = new Thread(start(command));
                            thread.start();
                            return 200;
                        } else {
                            String command = new libloader().load(false, version, GamePath, Javapath, Maxram)
                                    + new MinecraftJsonReader().getLauncher(VersionPath + version + "/" + version + ".json", GamePath)
                                    + VersionPath + version + "/" + version + ".jar " + islibraries + " "
                                    + UserAgent + " --height 480 --width 842" + serveragent;
                            //log(command);
                            Thread thread = new Thread(start(command));
                            thread.start();
                            return 200;
                        }
                    } else {
                        log("版本\"" + version + "\"为 Forge 或其他版本");
                        String FromVersion = new MinecraftJsonReader().getJson(GameJson, "inheritsFrom");
                        String UserAgent = new MinecraftJsonReader().getJson(GameJson, "minecraftArguments")
                                .replace("${auth_player_name}", gameName)
                                .replace("${version_name}", "\"Prismaillya\"")
                                .replace("${game_directory}", GamePath)
                                .replace("${assets_root}", GamePath + "/assets")
                                .replace("${assets_index_name}", IndexId)
                                .replace("${auth_uuid}", uuid)
                                .replace("${auth_access_token}", Token)
                                .replace("${user_properties}", "{}")
                                .replace("${user_type}", "Legacy")
                                .replace("${auth_session}", uuid)
                                .replace("${game_assets}", GamePath + "/assets")
                                .replace("\r\n", "")
                                .replace("${version_type}", "\"PMCL-RiOS7.3\"");
                        String LauncherHead = new libloader().load(true, version, GamePath, Javapath, Maxram);
                        String LauncherLibs = new MinecraftJsonReader().getForgeLauncher(VersionPath + FromVersion + "/" + FromVersion + ".json", GamePath, VersionPath + version + "\\" + version + ".json").replaceAll("/", "\\\\");
                        if (LauncherLibs == null) {
                            return 404;
                        }
                        String Launchercmd = LauncherHead + LauncherLibs + VersionPath + FromVersion + "/" + FromVersion + ".jar " + islibraries + " " + UserAgent + " --height 480 --width 842";
                        //log(Launchercmd);
                        String serveragent = "";
                        if (!"".equals(autoserver)) {
                            if ("".equals(autoport)) {
                                autoport = "25565";
                            }
                            serveragent = "--server " + autoserver + " --port " + autoport;
                        }
                        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                            String command = new libloader().load(true, version, GamePath, Javapath, Maxram)
                                    + new MinecraftJsonReader().getLauncher(VersionPath + version + "/" + version + ".json", GamePath).replaceAll("/", "\\\\")
                                    + VersionPath.replaceAll("/", "\\\\") + FromVersion + "\\" + FromVersion + ".jar " + islibraries + " "
                                    + UserAgent.replaceAll("/", "\\\\") + " --height 480 --width 842" + serveragent;
                            //log(command);
                            Thread thread = new Thread(start(command));
                            thread.start();
                            return 200;
                        } else {
                            String command = new libloader().load(false, version, GamePath, Javapath, Maxram)
                                    + new MinecraftJsonReader().getLauncher(VersionPath + version + "/" + version + ".json", GamePath)
                                    + VersionPath + version + "/" + version + ".jar " + islibraries + " "
                                    + UserAgent + " --height 480 --width 842" + serveragent;
                            //log(command);
                            Thread thread = new Thread(start(command));
                            thread.start();
                            return 200;
                        }
                    }
                } catch (JsonSyntaxException | JsonIOException ex) {
                    System.err.println("Exception: Json Reader Error. \n" + loginBack);
                }
            } catch (Exception es) {
                log("Exception: " + es.getMessage());
                return 403;
            }
        } else {
            if (islibraries.equals("net.minecraft.client.main.Main")) {
                log("版本\"" + version + "\"为纯净版");
                String UserAgent = new MinecraftJsonReader().getJson(GameJson, "minecraftArguments")
                        .replace("${auth_player_name}", gameName)
                        .replace("${version_name}", "\"Prismaillya\"")
                        .replace("${game_directory}", GamePath)
                        .replace("${assets_root}", GamePath + "/assets")
                        .replace("${assets_index_name}", IndexId)
                        .replace("${auth_uuid}", uuid)
                        .replace("${auth_access_token}", Token)
                        .replace("${user_properties}", "{}")
                        .replace("${user_type}", "Legacy")
                        .replace("${auth_session}", uuid)
                        .replace("${game_assets}", GamePath + "/assets")
                        .replace("\r\n", "")
                        .replace("${version_type}", "\"PMCL-RiOS7.3\"");
                String serveragent = "";
                if (!"".equals(autoserver)) {
                    if ("".equals(autoport)) {
                        autoport = "25565";
                    }
                    serveragent = "--server " + autoserver + " --port " + autoport;
                }
                if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                    String command = new libloader().load(true, version, GamePath, Javapath, Maxram)
                            + new MinecraftJsonReader().getLauncher(VersionPath + version + "/" + version + ".json", GamePath).replaceAll("/", "\\\\")
                            + VersionPath.replaceAll("/", "\\\\") + version + "\\\\" + version + ".jar " + islibraries + " "
                            + UserAgent.replaceAll("/", "\\\\") + " --height 480 --width 842" + serveragent;
                    //log(command);
                    Thread thread = new Thread(start(command));
                    thread.start();
                    return 200;
                } else {
                    String command = new libloader().load(false, version, GamePath, Javapath, Maxram)
                            + new MinecraftJsonReader().getLauncher(VersionPath + version + "/" + version + ".json", GamePath)
                            + VersionPath + version + "/" + version + ".jar " + islibraries + " "
                            + UserAgent + " --height 480 --width 842" + serveragent;
                    //log(command);
                    Thread thread = new Thread(start(command));
                    thread.start();
                    return 200;
                }
            } else {
                log("版本\"" + version + "\"为 Forge 或其他版本");
                String FromVersion = new MinecraftJsonReader().getJson(GameJson, "inheritsFrom");
                String UserAgent = new MinecraftJsonReader().getJson(GameJson, "minecraftArguments")
                        .replace("${auth_player_name}", gameName)
                        .replace("${version_name}", "\"Prismaillya\"")
                        .replace("${game_directory}", GamePath)
                        .replace("${assets_root}", GamePath + "/assets")
                        .replace("${assets_index_name}", IndexId)
                        .replace("${auth_uuid}", uuid)
                        .replace("${auth_access_token}", Token)
                        .replace("${user_properties}", "{}")
                        .replace("${user_type}", "Legacy")
                        .replace("${auth_session}", uuid)
                        .replace("${game_assets}", GamePath + "/assets")
                        .replace("\r\n", "")
                        .replace("${version_type}", "\"PMCL-RiOS7.3\"");
                String LauncherHead = new libloader().load(true, version, GamePath, Javapath, Maxram);
                String LauncherLibs = new MinecraftJsonReader().getForgeLauncher(VersionPath + FromVersion + "/" + FromVersion + ".json", GamePath, VersionPath + version + "\\" + version + ".json").replaceAll("/", "\\\\");
                if (LauncherLibs == null) {
                    return 404;
                }
                String Launchercmd = LauncherHead + LauncherLibs + VersionPath + FromVersion + "/" + FromVersion + ".jar " + islibraries + " " + UserAgent + " --height 480 --width 842";
                log(Launchercmd);
                String serveragent = "";
                if (!"".equals(autoserver)) {
                    if ("".equals(autoport)) {
                        autoport = "25565";
                    }
                    serveragent = "--server " + autoserver + " --port " + autoport;
                }
                if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                    String command = new libloader().load(true, version, GamePath, Javapath, Maxram)
                            + new MinecraftJsonReader().getLauncher(VersionPath + version + "/" + version + ".json", GamePath).replaceAll("/", "\\\\")
                            + VersionPath.replaceAll("/", "\\\\") + FromVersion + "\\" + FromVersion + ".jar " + islibraries + " "
                            + UserAgent.replaceAll("/", "\\\\") + " --height 480 --width 842" + serveragent;
                    //log(command);
                    Thread thread = new Thread(start(command));
                    thread.start();
                    return 200;
                } else {
                    String command = new libloader().load(false, version, GamePath, Javapath, Maxram)
                            + new MinecraftJsonReader().getLauncher(VersionPath + version + "/" + version + ".json", GamePath)
                            + VersionPath + version + "/" + version + ".jar " + islibraries + " "
                            + UserAgent + " --height 480 --width 842" + serveragent;
                    //log(command);
                    Thread thread = new Thread(start(command));
                    thread.start();
                    return 200;
                }
            }
        }
        return 200;
    }

    public static String getuuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    private static String start(String args) {
        try {
            filewrite.write("PMCL_debug.log", args);
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(args);
            return "Successful";
        } catch (IOException er) {
            log("Start game failed.");
            return "Error";
        }
    }
}
