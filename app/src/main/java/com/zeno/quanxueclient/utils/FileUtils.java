package com.zeno.quanxueclient.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Zeno on 2017/1/9.
 */

public class FileUtils {

    /**
     * 保存字符数据到文件
     * @param filePath
     * @param content
     */
    public static File saveFile(String filePath,String fileName,String content) {
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        File file = null;
        try{

            file = new File(filePath+fileName);
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();

            fileOutputStream =  new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(content.getBytes());

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return file;
    }
}
