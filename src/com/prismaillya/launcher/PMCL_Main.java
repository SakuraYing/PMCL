package com.prismaillya.launcher;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import org.acraft.NicoHttp.Object.Http.Handler;
import org.acraft.NicoHttp.Event.Exchange;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.acraft.NicoHttp.Object.Http.Server;

public final class PMCL_Main {

    private static int PORT = 8331;
    private static String mimelist;
    private static String defaultpage;
    public static String javaPath, maxRam, autoServer, autoPort, backgroundImage, playerName, playerPass, Version, loginType, debug;
    public static String GobalRoot;

    public static void main(String[] args) {
        System.out.println(" ____    _    ___    ____    _____ ");
        System.out.println("|  _ \\  (_)  / _ \\  / ___|  |___  |");
        System.out.println("| |_) | | | | | | | \\___ \\     / / ");
        System.out.println("|  _ <  | | | |_| |  ___) |   / / ");
        System.out.println("|_| \\_\\ |_|  \\___/  |____/   /_/  ");
        System.out.println("");
        System.out.println("                          X-Powered-By");
        System.out.println("");
        log("PMCL v1.0 is a beta version, and there may be some bugs.");
        log("If you found a bug, please report it in MCBBS or github issue.");
        log("Author Email: Prismaillya@tcotp.cn QQ: 198366085");
        log("Loading libraries, Please wait...");
        GobalRoot = new File(System.getProperty("user.dir") + "/.minecraft/").getAbsolutePath().replaceAll("\\\\", "/");
        PMCL_Main NicoCraftX = new PMCL_Main();
    }

    public PMCL_Main() {
        File file = new File("PMCL_HTTP.json");
        if (!file.exists()) {
            log("HTTP Server config file \"PMCL_HTTP.json\" not found, try to create it...");
            saveconfig();
            log("Successful create HTTP Server config file.");
        }
        try {
            InputStreamReader isr = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("mime.types"), "utf-8");
            try (BufferedReader br = new BufferedReader(isr)) {
                String lineTxt = null;
                while ((lineTxt = br.readLine()) != null) {
                    mimelist += lineTxt;
                }
            }
        } catch (IOException e) {
            log("Can't load the MIME config file!");
            log("正在结束进程...");
            System.exit(1);
        }
        File mcdir = new File(".minecraft/");
        File vedir = new File(".minecraft/versions/");
        if (!mcdir.exists()) {
            mcdir.mkdir();
        }
        if (!vedir.exists()) {
            vedir.mkdir();
        }
        try {
            defaultpage = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "defaultpage");
            PORT = Integer.parseInt(new MinecraftJsonReader().getJson("PMCL_HTTP.json", "port"));
            debug = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "debug");
        } catch (Exception err) {
            log("Can't load the config file! Is the config file have an error?");
        }
        try {
            InputStreamReader isr = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("mime.types"), "utf-8");
            try (BufferedReader br = new BufferedReader(isr)) {
                String lineTxt = null;
                while ((lineTxt = br.readLine()) != null) {
                    mimelist += lineTxt;
                }
            }
        } catch (IOException e) {
            log("Can't load the MIME config file! Is the config file have an error?");
            log("Server stopping...");
            System.exit(1);
        } finally {
            log("Done! RiOS 7.3 HTTP Server by Prismaillya.");
            log("Read More about Prismaillya on: https://www.prismaillya.com/");
            try {
                URI uri = new URI("http://localhost:" + PORT + "/launcher.html");
                Desktop.getDesktop().browse(uri);
            } catch (URISyntaxException | IOException e) {
                log(e.getMessage());
            }
        }
        try {
            Server.create(PORT).setHandler(new Handler() {
                @Override
                public void onRequest(Exchange event) throws IOException {
                    NormalRequest(event);
                }
            }
            );
        } catch (NullPointerException ess) {
            log("Failed to start the HTTP Server!");
            log("Please check the port " + PORT + " , It may have been used by other programs");
            log("Stopping RiOS......");
            System.exit(1);
        }
    }

    public void NormalRequest(Exchange event) throws IOException {
        String request = event.getURL().toString().substring(event.getURL().toString().lastIndexOf(":" + PORT + "/") + 6);
        String MimeTypes;
        if (request.equals("")) {
            request = defaultpage;
        }
        String returnstr = "";
        int returncode = 200;
        Date d = new Date();
        SimpleDateFormat sdf;
        switch (request) {
            case "system/gameversion/":
                String gameversionlist = searchversion(".minecraft/versions/");
                if (gameversionlist == null) {
                    gameversionlist = "Game Not Found";
                }
                event.setHeader("Content-Type", "text/html;charset=utf-8");
                event.setHeader("X-Powered-By", "Prismaillya");
                event.setHeader("Server", "RiOS/7.3");
                event.sendHeader(200, 0);
                event.write(gameversionlist);
                break;
            case "system/config/":
                String playername = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "playername");
                String playerpass = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "playerpass");
                String javapath = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "javapath");
                String maxram = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "maxram");
                String autoserver = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "autoserver");
                String autoport = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "autoport");
                String backgroundimage = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "backgroundimage");
                String lastestversion = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "lastestversion");
                String lastestlogin = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "lastestlogin");
                event.setHeader("Content-Type", "text/html;charset=utf-8");
                event.setHeader("X-Powered-By", "Prismaillya");
                event.setHeader("Server", "RiOS/7.3");
                event.sendHeader(200, 0);
                event.write(javapath
                        + "|" + maxram
                        + "|" + autoserver
                        + "|" + autoport
                        + "|" + backgroundimage
                        + "|" + playername
                        + "|" + playerpass
                        + "|" + lastestversion
                        + "|" + lastestlogin
                );
                break;
            case "system/startgame/":
                event.setHeader("Content-Type", "text/html;charset=utf-8");
                event.setHeader("X-Powered-By", "Prismaillya");
                event.setHeader("Server", "RiOS/7.3");
                event.sendHeader(200, 0);
                playername = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "playername");
                playerpass = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "playerpass");
                javapath = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "javapath");
                maxram = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "maxram");
                String selectversion = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "selectversion");
                String GameJson = ".minecraft/versions/" + selectversion + "/" + selectversion + ".json";
                String IndexId = new MinecraftJsonReader().getJson(GameJson, "assets");
                String logintype = new MinecraftJsonReader().getJson("PMCL_HTTP.json", "selectlogintype");
                new GameConfig().change("lastestversion", selectversion);
                new GameConfig().change("lastestlogin", logintype);
                if (!new File(".minecraft/assets/indexes/" + IndexId + ".json").exists()) {
                    log("资源文件不存在，正在下载。");
                    if (!new File("Ri_temp/").exists()) {
                        new File("Ri_temp/").mkdir();
                    }
                    try {
                        RiHttp.download("http://cdn.tcotp.cn/download/NNCL/version/assets/" + IndexId + ".zip", "Ri_temp/assets.zip");
                        unzip.unzipFile("Ri_temp/assets.zip", ".minecraft/");
                        event.write("\\u8d44\\u6e90\\u6587\\u4ef6\\u4e0b\\u8f7d\\u5b8c\\u6210\\uff0c\\u8bf7\\u91cd\\u65b0\\u542f\\u52a8\\u6e38\\u620f\\u3002");
                    } catch (Exception es) {
                        log("Resource file download failed!");
                        try {
                            event.write("\\u8d44\\u6e90\\u6587\\u4ef6\\u4e0b\\u8f7d\\u5931\\u8d25\\uff0c\\u53ef\\u80fd\\u662f\\u7f51\\u7edc\\u95ee\\u9898\\uff0c\\u60a8\\u53ef\\u4ee5\\u5c1d\\u8bd5\\u91cd\\u65b0\\u4e0b\\u8f7d\\u3002");
                        } catch (Exception ed) {
                            log(ed.getMessage());
                        }
                    }
                } else {
                    int status;
                    if (logintype.equals("online")) {
                        status = Launcher.run(javapath, playername, playerpass, selectversion, maxram, GobalRoot, true);
                    } else {
                        status = Launcher.run(javapath, playername, playerpass, selectversion, maxram, GobalRoot, false);
                    }
                    if (status == 200) {
                        try {
                            log("成功启动游戏版本：" + selectversion);
                            event.write("\\u6b63\\u5728\\u542f\\u52a8\\u6e38\\u620f\\uff0c\\u7248\\u672c\\uff1a" + selectversion);
                        } catch (Exception ex) {
                            log(ex.getMessage());
                        }
                    } else {
                        switch (status) {
                            case 403:
                                event.write("\\u6b63\\u7248\\u8d26\\u53f7\\u6216\\u5bc6\\u7801\\u9519\\u8bef\\uff01");
                                break;
                            case 404:
                                event.write("\\u65e0\\u6cd5\\u542f\\u52a8\\u6e38\\u620f\\uff0c\\u8be5\\u7248\\u672c\\u6240\\u9700\\u7684\\u4f9d\\u8d56\\u7248\\u672c\\u4e0d\\u5b58\\u5728\\u3002");
                                break;
                        }
                    }
                }
                break;
            default:
                if (request.contains("/")) {
                    String[] tag = request.split("/");
                    try {
                        switch (tag[0]) {
                            case "version":
                                event.setHeader("Content-Type", "text/html;charset=utf-8");
                                event.setHeader("X-Powered-By", "Prismaillya");
                                event.setHeader("Server", "RiOS/7.3");
                                event.sendHeader(200, 0);
                                event.write("Version set to:" + new String(Base64.decode(tag[1])));
                                new GameConfig().change("selectversion", new String(Base64.decode(tag[1])));
                                break;
                            case "logintype":
                                event.setHeader("Content-Type", "text/html;charset=utf-8");
                                event.setHeader("X-Powered-By", "Prismaillya");
                                event.setHeader("Server", "RiOS/7.3");
                                event.sendHeader(200, 0);
                                event.write("LoginType set to:" + new String(Base64.decode(tag[1])));
                                new GameConfig().change("selectlogintype", new String(Base64.decode(tag[1])));
                                break;
                            case "playername":
                                event.setHeader("Content-Type", "text/html;charset=utf-8");
                                event.setHeader("X-Powered-By", "Prismaillya");
                                event.setHeader("Server", "RiOS/7.3");
                                event.sendHeader(200, 0);
                                event.write("PlayerName set to:" + new String(Base64.decode(tag[1])));
                                new GameConfig().change("playername", new String(Base64.decode(tag[1])));
                                break;
                            case "playerpass":
                                event.setHeader("Content-Type", "text/html;charset=utf-8");
                                event.setHeader("X-Powered-By", "Prismaillya");
                                event.setHeader("Server", "RiOS/7.3");
                                event.sendHeader(200, 0);
                                event.write("PlayerPass set to:" + new String(Base64.decode(tag[1])));
                                new GameConfig().change("playerpass", new String(Base64.decode(tag[1])));
                                break;
                            case "javapath":
                                event.setHeader("Content-Type", "text/html;charset=utf-8");
                                event.setHeader("X-Powered-By", "Prismaillya");
                                event.setHeader("Server", "RiOS/7.3");
                                event.sendHeader(200, 0);
                                event.write("JavaPath set to:" + new String(Base64.decode(tag[1])));
                                new GameConfig().change("javapath", new String(Base64.decode(tag[1])));
                                log("JavaPath change: " + new String(Base64.decode(tag[1])));
                                break;
                            case "maxram":
                                event.setHeader("Content-Type", "text/html;charset=utf-8");
                                event.setHeader("X-Powered-By", "Prismaillya");
                                event.setHeader("Server", "RiOS/7.3");
                                event.sendHeader(200, 0);
                                event.write("MaxRam set to:" + new String(Base64.decode(tag[1])));
                                new GameConfig().change("maxram", new String(Base64.decode(tag[1])));
                                if (new String(Base64.decode(tag[1])).equals("")) {
                                    new GameConfig().change("maxram", "1024");
                                }
                                log("MaxRam change: " + new String(Base64.decode(tag[1])));
                                break;
                            case "autoserver":
                                event.setHeader("Content-Type", "text/html;charset=utf-8");
                                event.setHeader("X-Powered-By", "Prismaillya");
                                event.setHeader("Server", "RiOS/7.3");
                                event.sendHeader(200, 0);
                                event.write("AutoServer set to:" + new String(Base64.decode(tag[1])));
                                new GameConfig().change("autoserver", new String(Base64.decode(tag[1])));
                                log("AutoServer change: " + new String(Base64.decode(tag[1])));
                                break;
                            case "autoport":
                                event.setHeader("Content-Type", "text/html;charset=utf-8");
                                event.setHeader("X-Powered-By", "Prismaillya");
                                event.setHeader("Server", "RiOS/7.3");
                                event.sendHeader(200, 0);
                                event.write("AutoPort set to:" + new String(Base64.decode(tag[1])));
                                new GameConfig().change("autoport", new String(Base64.decode(tag[1])));
                                log("AutoPort change: " + new String(Base64.decode(tag[1])));
                                break;
                            case "backgroundimage":
                                event.setHeader("Content-Type", "text/html;charset=utf-8");
                                event.setHeader("X-Powered-By", "Prismaillya");
                                event.setHeader("Server", "RiOS/7.3");
                                event.sendHeader(200, 0);
                                event.write("BackgroundImage set to:" + new String(Base64.decode(tag[1])));
                                new GameConfig().change("backgroundimage", new String(Base64.decode(tag[1])));
                                if (new String(Base64.decode(tag[1])).equals("")) {
                                    new GameConfig().change("backgroundimage", "bg.jpg");
                                }
                                log("BackgroundImage change: " + new String(Base64.decode(tag[1])));
                                break;
                        }
                    } catch (Exception err) {
                        event.setHeader("Content-Type", "text/html;charset=utf-8");
                        event.setHeader("X-Powered-By", "Prismaillya");
                        event.setHeader("Server", "RiOS/7.3");
                        event.sendHeader(404, 0);
                        event.write("Null");
                    }
                } else {
                    try {
                        InputStream is = this.getClass().getResourceAsStream("/html/" + request);
                        String s = "";
                        MimeTypes = getmimetype(request.substring(request.lastIndexOf(".") + 1));
                        if (MimeTypes.lastIndexOf("text/") != -1) {
                            byte[] tempbytes = new byte[100];
                            int byteread = 0;
                            event.setHeader("Content-Type", MimeTypes + ";charset=utf-8");
                            event.setHeader("X-Powered-By", "Prismaillya");
                            event.setHeader("Server", "RiOS/7.3");
                            event.sendHeader(returncode, 0);
                            //File F = new File("html/" + request);
                            DataOutputStream Output = new DataOutputStream(event.getExchange().getResponseBody());
                            DataInputStream Input = new DataInputStream(is);
                            final int BufferSize = 4096 * 4096;
                            byte[] Buffer = new byte[BufferSize];
                            int Bytes;
                            while ((Bytes = Input.read(Buffer, 0, BufferSize)) != -1) {
                                Output.write(Buffer, 0, Bytes);
                            }
                            Input.close();
                        } else {
                            byte[] tempbytes = new byte[100];
                            int byteread = 0;
                            event.setHeader("Content-Type", MimeTypes + ";charset=utf-8");
                            event.setHeader("X-Powered-By", "Prismaillya");
                            event.setHeader("Server", "RiOS/7.3");
                            event.sendHeader(returncode, 0);
                            //File F = new File("html/" + request);
                            DataOutputStream Output = new DataOutputStream(event.getExchange().getResponseBody());
                            DataInputStream Input = new DataInputStream(is);
                            final int BufferSize = 4096 * 4096;
                            byte[] Buffer = new byte[BufferSize];
                            int Bytes;
                            while ((Bytes = Input.read(Buffer, 0, BufferSize)) != -1) {
                                Output.write(Buffer, 0, Bytes);
                            }
                            Input.close();
                        }
                    } catch (Exception e) {
                        returnstr = "<html><head><title>404 Not Found</title></head><body><h2>404 Not Found</h2><hr style='width: 320px;margin: 0;'>"
                                + "<p><b>Status Code:</b> 404</p><p><b>Server:</b> RiOS-PrismaillyaHTTP</p><p><b>RiOSVer:</b> 7.3-nts</p>"
                                + "<hr style='width: 320px;margin: 0;'><p>We are sorry about this, but we can't found this page on the server</p>"
                                + "<p>Please check your url and try again.</p></body></html>";
                        returncode = 404;
                        event.setHeader("Content-Type", "text/html;charset=utf-8");
                        event.setHeader("X-Powered-By", "Prismaillya");
                        event.setHeader("Server", "RiOS/7.3");
                        event.sendHeader(returncode, 0);
                        event.write(returnstr);
                    }
                    if (debug.equals("true")) {
                        log(request.substring(request.lastIndexOf(".") + 1));
                    }
                    break;
                }
        }
        if (debug.equals("true")) {
            log(
                    "New request from: "
                    + event.getExchange().getRemoteAddress().toString()
                    + " , method: "
                    + event.getExchange().getRequestMethod()
                    + " , request URL: "
                    + event.getURL()
                    + " , domain: " + event.getURL().getHost()
            );
        }
    }

    public String ThreadWrite(Exchange event, String str) {
        event.write(str);
        return null;
    }

    public String searchversion(String dir) {
        File file = new File(dir);
        String jarlist = "";
        for (File getFile : file.listFiles()) {
            if (getFile.isDirectory()) {
                String childjarpath = dir + getFile.getName() + "/" + getFile.getName() + ".jar".replaceAll("\\\\", "/");
                File childjar = new File(childjarpath);
                if (childjar.exists()) {
                    jarlist += getFile.getName() + ";";
                } else if (new File(dir + getFile.getName() + "/" + getFile.getName() + ".json".replaceAll("\\\\", "/")).exists()) {
                    jarlist += getFile.getName() + ";";
                }
            }
        }
        if (!"".equals(jarlist)) {
            return jarlist.substring(0, jarlist.length() - 1);
        } else {
            return null;
        }
    }

    public String getmimetype(String Ex) {
        String[] getlist = mimelist.split(";");
        for (String list : getlist) {
            String[] gettar = list.split("=");
            String tag1 = gettar[0];
            if (tag1.equals(Ex)) {
                return gettar[1];
            }
        }
        return "text/html";
    }

    public static void log(String str) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println("[" + sdf.format(d) + "] " + str);
    }

    public final void saveconfig() {
        try {
            InputStreamReader isr = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("PMCL_HTTP.json"), "utf-8");
            try (BufferedReader br = new BufferedReader(isr)) {
                String lineTxt = null;
                File file = new File("PMCL_HTTP.json");
                BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                while ((lineTxt = br.readLine()) != null) {
                    bf.write(lineTxt + "\n");
                }
                bf.close();
            }
        } catch (IOException e) {
            log("Failed to save the HTTP Server config file. Permission denied.");
            log("Stopping RiOS...");
            System.exit(1);
        }
    }
}
