/*
 * 版权所有(C)Niconico Craft 保留所有权利
 * 您不得在未经作者许可的情况下，擅自发布本软件的任何部分或全部内容
 * 否则将会追究二次发布者的法律责任
 */
package com.prismaillya.launcher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author jiang
 */
public class MojangAuth {

    public String login(String username, String password) throws MalformedURLException, IOException {
        URL url = new URL("https://authserver.mojang.com/authenticate"); //Mojang正版验证服务器
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //创建连接
        /**
         * 连接参数设置
         */
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        conn.setUseCaches(false);
        conn.setInstanceFollowRedirects(true);
        conn.setConnectTimeout(20000);
        conn.setReadTimeout(300000);
        conn.setRequestProperty("Content-Type", "application/json");

        conn.connect(); //连接服务器
        DataOutputStream out = new DataOutputStream(conn.getOutputStream()); //创建标准输出系统
        String json = "{\"agent\": {\"name\": \"Minecraft\",\"version\": 1},\"username\": \"" + username
                + "\",\"password\": \"" + password + "\"}"; //需要发送的信息
        out.writeBytes(json); //发送信息
        if (conn.getResponseCode() == 403) { //如果服务器返回403错误
            return "403"; //返回403
        }
        DataInputStream in = new DataInputStream(conn.getInputStream()); //如果正常
        return in.readLine(); //返回服务器结果
    }
}
