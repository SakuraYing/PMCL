/*
 * 版权所有(C)Niconico Craft 保留所有权利
 * 您不得在未经作者许可的情况下，擅自发布本软件的任何部分或全部内容
 * 否则将会追究二次发布者的法律责任
 */
package com.prismaillya.launcher;

import static com.prismaillya.launcher.PMCL_Main.log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class unzip {

    static String getSuffixName(String name) {
        return name.substring(0, name.lastIndexOf("."));
    }

    public static void unzipFile(String zipFilePath, String unzipDirectory)
            throws Exception {
        File file = new File(zipFilePath);
        ZipFile zipFile = new ZipFile(file);
        File unzipFile = new File(unzipDirectory + "/" + getSuffixName(file.getName()));
        if (unzipFile.exists()) {
            unzipFile.delete();
        }
        unzipFile.mkdir();
        Enumeration zipEnum = zipFile.entries();
        InputStream input = null;
        OutputStream output = null;
        while (zipEnum.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) zipEnum.nextElement();
            String entryName = new String(entry.getName().getBytes("ISO8859_1"));
            log("FileName: " + entryName + " Size: " + entry.getSize());
            if (entry.isDirectory()) {
                new File(unzipFile.getAbsolutePath() + "/" + entryName).mkdir();
            } else {
                input = zipFile.getInputStream(entry);
                output = new FileOutputStream(new File(unzipFile
                        .getAbsolutePath()
                        + "/" + entryName));
                byte[] buffer = new byte[1024 * 8];
                int readLen = 0;
                while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1) {
                    output.write(buffer, 0, readLen);
                }
                input.close();
                output.flush();
                output.close();
            }
        }
    }
}
