/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prismaillya.launcher;

import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.BufferedWriter;
import java.io.File;

/**
 *
 * @author jianghao7172
 */
public class filewrite {

    /**
     * @param fileName
     * @param content
     */
    public static void append(String fileName, String content) {
        try {
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            long fileLength = randomFile.length();
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param fileName
     * @param str
     */
    public static void write(String fileName, String str) {
        String path = fileName;
        try {
            FileWriter fw = new FileWriter(path, false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(str);
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
