package com.xwdz.site.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class FileUtils {

    public static synchronized void save(byte[] bytes, File saveFile) {

        FileOutputStream outputStream = null;
        BufferedOutputStream bufferedOutputStream = null;

        try {
            outputStream = new FileOutputStream(saveFile);
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            bufferedOutputStream.write(bytes);
            bufferedOutputStream.flush();

        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

        }

    }

    public static synchronized String getSuffix(String fileName) {
        if (fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf("."));
        } else {
            return fileName;
        }
    }
}
