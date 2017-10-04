/*
 * 版权所有(C)Niconico Craft 保留所有权利
 * 您不得在未经作者许可的情况下，擅自发布本软件的任何部分或全部内容
 * 否则将会追究二次发布者的法律责任
 */
package com.prismaillya.launcher;

/**
 *
 * @author jiang
 */
public class libloader {

    public String load(boolean type, String version, String gamepath, String javapath, String maxram) {
        if (type) {
            String startlnk = "cmd /c cd /D " + gamepath.replaceAll("/", "\\\\") + "&\"" + javapath + "\" -XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump -XX:+UseG1GC -XX:-UseAdaptiveSizePolicy -XX:-OmitStackTraceInFastThrow -Xmn128m -Xmx" + maxram + "m -Djava.library.path=" + gamepath.replaceAll("/", "\\\\") + "\\versions\\" + version + "\\" + version + "-natives -Dfml.ignoreInvalidMinecraftCertificates=true -Dfml.ignorePatchDiscrepancies=true -cp ";
            return startlnk;
        } else {
            String startlnk = javapath + " -Dminecraft.client.jar=" + gamepath + "/versions/" + version + "/" + version + ".jar -Dminecraft.launcher.version=7.3.1031 \"-Dminecraft.launcher.brand=Prismaillya Minecraft Launcher\" -Xincgc -XX:-UseAdaptiveSizePolicy -XX:-OmitStackTraceInFastThrow -Xmn128m -Xmx" + maxram + "m -Djava.library.path=" + gamepath + "/versions/" + version + "/" + version + "-natives -Dfml.ignoreInvalidMinecraftCertificates=true -Dfml.ignorePatchDiscrepancies=true -Duser.home=null -cp ";
            return startlnk;
        }
    }
}
