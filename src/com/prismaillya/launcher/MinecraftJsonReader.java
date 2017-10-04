/*
 * 版权所有(C)Niconico Craft 保留所有权利
 * 您不得在未经作者许可的情况下，擅自发布本软件的任何部分或全部内容
 * 否则将会追究二次发布者的法律责任
 */
package com.prismaillya.launcher;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import static com.prismaillya.launcher.PMCL_Main.log;
import java.io.File;

/**
 *
 * @author jiang
 */
public class MinecraftJsonReader {

    public String getJson(String JsonFile, String getType) {
        JsonParser parse = new JsonParser();
        try {
            JsonObject json = (JsonObject) parse.parse(new FileReader(JsonFile));
            return json.get(getType).getAsString();
        } catch (JsonSyntaxException | JsonIOException ex) {
            System.err.println("Exception: Json Reader Error. " + JsonFile + " on " + getType);
            return null;
        } catch (FileNotFoundException ef) {
            System.err.println("Exception: File Not Found. " + JsonFile);
            return null;
        }
    }

    public String getLauncher(String JsonFile, String GamePath) {
        JsonParser parse = new JsonParser();
        String returnString = null;
        File librariesdir = new File(".minecraft/libraries/");
        if (!librariesdir.exists()) {
            librariesdir.mkdirs();
        }
        try {
            JsonObject json = (JsonObject) parse.parse(new FileReader(JsonFile));
            JsonArray array = json.get("libraries").getAsJsonArray();
            String pathlist = "";
            for (int i = 0; i < array.size(); i++) {
                JsonObject subObject = array.get(i).getAsJsonObject();
                JsonObject download = subObject.getAsJsonObject("downloads");
                JsonObject artifact = download.getAsJsonObject("artifact");
                try {
                    File libfile = new File(GamePath + "/libraries/" + artifact.get("path").getAsString());
                    if (!libfile.exists()) {
                        String[] FileName = artifact.get("url").getAsString().split("/");
                        log("File \"" + FileName[FileName.length - 1] + "\" not found, downloading...");
                        boolean downstatus = RiHttp.download(artifact.get("url").getAsString(), ".minecraft/libraries/" + artifact.get("path").getAsString());
                        if (downstatus) {
                            log("Download successful!");
                        }
                    }
                    pathlist += GamePath + "/libraries/" + artifact.get("path").getAsString() + ";";
                } catch (Exception ex) {
                    try {
                        String OS = System.getProperty("os.name").toLowerCase();
                        if (OS.contains("linux")) {
                            JsonObject classifiers = download.getAsJsonObject("classifiers");
                            JsonObject natives = classifiers.getAsJsonObject("natives-linux");
                            File libfile = new File(GamePath + "/libraries/" + natives.get("path").getAsString());
                            if (!libfile.exists()) {
                                String[] FileName = natives.get("path").getAsString().split("/");
                                log("File \"" + FileName[FileName.length - 1] + "\" not found, downloading...");
                                boolean downstatus = RiHttp.download(natives.get("url").getAsString(), ".minecraft/libraries/" + natives.get("path").getAsString());
                                if (downstatus) {
                                    log("Download successful!");
                                }
                            }
                            pathlist += GamePath + "/libraries/" + natives.get("path").getAsString() + ";";
                        } else if (OS.contains("windows")) {
                            JsonObject classifiers = download.getAsJsonObject("classifiers");
                            JsonObject natives = classifiers.getAsJsonObject("natives-windows");
                            File libfile = new File(GamePath + "/libraries/" + natives.get("path").getAsString());
                            if (!libfile.exists()) {
                                String[] FileName = natives.get("path").getAsString().split("/");
                                log("File \"" + FileName[FileName.length - 1] + "\" not found, downloading...");
                                boolean downstatus = RiHttp.download(natives.get("url").getAsString(), ".minecraft/libraries/" + natives.get("path").getAsString());
                                if (downstatus) {
                                    log("Download successful!");
                                }
                            }
                            pathlist += GamePath + "/libraries/" + natives.get("path").getAsString() + ";";
                        } else {
                            JsonObject classifiers = download.getAsJsonObject("classifiers");
                            JsonObject natives = classifiers.getAsJsonObject("natives-osx");
                            File libfile = new File(GamePath + "/libraries/" + natives.get("path").getAsString());
                            if (!libfile.exists()) {
                                String[] FileName = natives.get("path").getAsString().split("/");
                                log("File \"" + FileName[FileName.length - 1] + "\" not found, downloading...");
                                boolean downstatus = RiHttp.download(natives.get("url").getAsString(), ".minecraft/libraries/" + natives.get("path").getAsString());
                                if (downstatus) {
                                    log("Download successful!");
                                }
                            }
                            pathlist += GamePath + "/libraries/" + natives.get("path").getAsString() + ";";
                        }
                    } catch (Exception es) {
                        String bits = System.getProperty("os.arch").toLowerCase();
                        if (bits.contains("amd64")) {
                            JsonObject classifiers = download.getAsJsonObject("classifiers");
                            JsonObject natives = classifiers.getAsJsonObject("natives-windows-64");
                            File libfile = new File(GamePath + "/libraries/" + natives.get("path").getAsString());
                            if (!libfile.exists()) {
                                String[] FileName = natives.get("path").getAsString().split("/");
                                log("File \"" + FileName[FileName.length - 1] + "\" not found, downloading...");
                                boolean downstatus = RiHttp.download(natives.get("url").getAsString(), ".minecraft/libraries/" + natives.get("path").getAsString());
                                if (downstatus) {
                                    log("Download successful!");
                                }
                            }
                            pathlist += GamePath + "/libraries/" + natives.get("path").getAsString() + ";";
                        } else {
                            JsonObject classifiers = download.getAsJsonObject("classifiers");
                            JsonObject natives = classifiers.getAsJsonObject("natives-windows-32");
                            File libfile = new File(GamePath + "/libraries/" + natives.get("path").getAsString());
                            if (!libfile.exists()) {
                                String[] FileName = natives.get("path").getAsString().split("/");
                                log("File \"" + FileName[FileName.length - 1] + "\" not found, downloading...");
                                boolean downstatus = RiHttp.download(natives.get("url").getAsString(), ".minecraft/libraries/" + natives.get("path").getAsString());
                                if (downstatus) {
                                    log("Download successful!");
                                }
                            }
                            pathlist += GamePath + "/libraries/" + natives.get("path").getAsString() + ";";
                        }
                    }
                }
            }
            returnString = pathlist;
            return returnString;
        } catch (JsonSyntaxException | JsonIOException ex) {
            System.err.println("Exception: Json Reader Error. " + ex);
            return null;
        } catch (FileNotFoundException ef) {
            System.err.println("Exception: File Not Found. #1 " + JsonFile);
            return null;
        }
    }

    public String getForgeLauncher(String JsonFile, String GamePath, String ForgeJson) {
        String VersionPath = GamePath + "/version/";
        JsonParser parse = new JsonParser();
        String returnString = null;
        File librariesdir = new File(GamePath + "/libraries/");
        if (!librariesdir.exists()) {
            librariesdir.mkdirs();
        }
        try {
            JsonObject json = (JsonObject) parse.parse(new FileReader(JsonFile));
            JsonArray array = json.get("libraries").getAsJsonArray();
            String pathlist = "";
            JsonObject fjson = (JsonObject) parse.parse(new FileReader(ForgeJson));
            JsonArray farray = fjson.get("libraries").getAsJsonArray();
            String fpathlist = "";
            for (int i = 0; i < farray.size(); i++) {
                JsonObject SubObject = farray.get(i).getAsJsonObject();
                String objectname = SubObject.get("name").getAsString();
                String LibDoma = objectname.substring(0, objectname.indexOf(":")).replaceAll("\\.", "/");
                String LibPath = LibDoma + objectname.substring(objectname.indexOf(":"), objectname.length()).replaceAll(":", "/");
                String[] gettag = SubObject.get("name").getAsString().split(":");
                String FileName = gettag[gettag.length -2] + "-" + gettag[gettag.length -1] + ".jar";
                File libfile = new File(GamePath + "/libraries/" + LibPath + "/" + FileName);
                if (!libfile.exists()) {
                    log("File \"" + GamePath + "/libraries/" + LibPath + "/" + FileName + "\" not found, downloading...");
                    boolean downstatus = RiHttp.download(SubObject.get("url").getAsString(), ".minecraft/libraries/" + LibPath + "/" + FileName);
                    if (downstatus) {
                        log("Download successful!");
                    }
                }
                pathlist += GamePath + "/libraries/" + LibPath + "/" + FileName + ";";
            }
            for (int i = 0; i < array.size(); i++) {
                JsonObject subObject = array.get(i).getAsJsonObject();
                JsonObject download = subObject.getAsJsonObject("downloads");
                JsonObject artifact = download.getAsJsonObject("artifact");
                try {
                    File libfile = new File(GamePath + "/libraries/" + artifact.get("path").getAsString());
                    if (!libfile.exists()) {
                        String[] FileName = artifact.get("url").getAsString().split("/");
                        log("File \"" + FileName[FileName.length - 1] + "\" not found, downloading...");
                        boolean downstatus = RiHttp.download(artifact.get("url").getAsString(), ".minecraft/libraries/" + artifact.get("path").getAsString());
                        if (downstatus) {
                            log("Download successful!");
                        }
                    }
                    pathlist += GamePath + "/libraries/" + artifact.get("path").getAsString() + ";";
                } catch (Exception ex) {
                    try {
                        String OS = System.getProperty("os.name").toLowerCase();
                        if (OS.contains("linux")) {
                            JsonObject classifiers = download.getAsJsonObject("classifiers");
                            JsonObject natives = classifiers.getAsJsonObject("natives-linux");
                            File libfile = new File(GamePath + "/libraries/" + natives.get("path").getAsString());
                            if (!libfile.exists()) {
                                String[] FileName = natives.get("path").getAsString().split("/");
                                log("File \"" + FileName[FileName.length - 1] + "\" not found, downloading...");
                                boolean downstatus = RiHttp.download(natives.get("url").getAsString(), ".minecraft/libraries/" + natives.get("path").getAsString());
                                if (downstatus) {
                                    log("Download successful!");
                                }
                            }
                            pathlist += GamePath + "/libraries/" + natives.get("path").getAsString() + ";";
                        } else if (OS.contains("windows")) {
                            JsonObject classifiers = download.getAsJsonObject("classifiers");
                            JsonObject natives = classifiers.getAsJsonObject("natives-windows");
                            File libfile = new File(GamePath + "/libraries/" + natives.get("path").getAsString());
                            if (!libfile.exists()) {
                                String[] FileName = natives.get("path").getAsString().split("/");
                                log("File \"" + FileName[FileName.length - 1] + "\" not found, downloading...");
                                boolean downstatus = RiHttp.download(natives.get("url").getAsString(), ".minecraft/libraries/" + natives.get("path").getAsString());
                                if (downstatus) {
                                    log("Download successful!");
                                }
                            }
                            pathlist += GamePath + "/libraries/" + natives.get("path").getAsString() + ";";
                        } else {
                            JsonObject classifiers = download.getAsJsonObject("classifiers");
                            JsonObject natives = classifiers.getAsJsonObject("natives-osx");
                            File libfile = new File(GamePath + "/libraries/" + natives.get("path").getAsString());
                            if (!libfile.exists()) {
                                String[] FileName = natives.get("path").getAsString().split("/");
                                log("File \"" + FileName[FileName.length - 1] + "\" not found, downloading...");
                                boolean downstatus = RiHttp.download(natives.get("url").getAsString(), ".minecraft/libraries/" + natives.get("path").getAsString());
                                if (downstatus) {
                                    log("Download successful!");
                                }
                            }
                            pathlist += GamePath + "/libraries/" + natives.get("path").getAsString() + ";";
                        }
                    } catch (Exception es) {
                        String bits = System.getProperty("os.arch").toLowerCase();
                        if (bits.contains("amd64")) {
                            JsonObject classifiers = download.getAsJsonObject("classifiers");
                            JsonObject natives = classifiers.getAsJsonObject("natives-windows-64");
                            File libfile = new File(GamePath + "/libraries/" + natives.get("path").getAsString());
                            if (!libfile.exists()) {
                                String[] FileName = natives.get("path").getAsString().split("/");
                                log("File \"" + FileName[FileName.length - 1] + "\" not found, downloading...");
                                boolean downstatus = RiHttp.download(natives.get("url").getAsString(), ".minecraft/libraries/" + natives.get("path").getAsString());
                                if (downstatus) {
                                    log("Download successful!");
                                }
                            }
                            pathlist += GamePath + "/libraries/" + natives.get("path").getAsString() + ";";
                        } else {
                            JsonObject classifiers = download.getAsJsonObject("classifiers");
                            JsonObject natives = classifiers.getAsJsonObject("natives-windows-32");
                            File libfile = new File(GamePath + "/libraries/" + natives.get("path").getAsString());
                            if (!libfile.exists()) {
                                String[] FileName = natives.get("path").getAsString().split("/");
                                log("File \"" + FileName[FileName.length - 1] + "\" not found, downloading...");
                                boolean downstatus = RiHttp.download(natives.get("url").getAsString(), ".minecraft/libraries/" + natives.get("path").getAsString());
                                if (downstatus) {
                                    log("Download successful!");
                                }
                            }
                            pathlist += GamePath + "/libraries/" + natives.get("path").getAsString() + ";";
                        }
                    }
                }
            }
            returnString = pathlist;
            return returnString;
        } catch (JsonSyntaxException | JsonIOException ex) {
            System.err.println("Exception: Json Reader Error. " + ex);
            return null;
        } catch (FileNotFoundException ef) {
            System.err.println("Exception: File Not Found. #1 " + JsonFile + " #2 " + ForgeJson);
            return null;
        }
    }
}
