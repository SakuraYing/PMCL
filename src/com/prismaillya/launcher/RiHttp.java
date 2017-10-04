/*
 * 版权所有(C)Niconico Craft 保留所有权利
 * 您不得在未经作者许可的情况下，擅自发布本软件的任何部分或全部内容
 * 否则将会追究二次发布者的法律责任
 */
package com.prismaillya.launcher;

import static com.prismaillya.launcher.PMCL_Main.log;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author jainghao7172
 */
public class RiHttp {

    public static boolean download(String strUrl, String fileName) {
        try {
            log("Downloading file: " + strUrl);
            if (fileName.contains("/")) {
                String[] getdir = fileName.split("/");
                String dirname = fileName.replace(getdir[getdir.length - 1], "");
                File downdir = new File(dirname);
                if (!downdir.exists()) {
                    log("Directory not found, create it.");
                    downdir.mkdirs();
                }
            }
            log("Start download file.");
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            DataInputStream input = new DataInputStream(conn.getInputStream());
            DataOutputStream output = new DataOutputStream(new FileOutputStream(fileName));
            byte[] buffer = new byte[1024 * 8];
            int count = 0;
            while ((count = input.read(buffer)) > 0) {
                output.write(buffer, 0, count);
            }
            output.close();
            input.close();
            log("Successful! File have been saved to: " + fileName);
            return true;
        } catch (Exception ex) {
            log("Failed to download file, error: " + ex.getMessage());
            return false;
        }
    }
}
