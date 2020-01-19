package com.winderinfo.wechat.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * lovely_melon
 * 2020/1/15
 */
public class MyFileUtil {
    private static String SDPATH = Environment.getDataDirectory() + "/djb";

    public static void saveUserImage(Context context, Bitmap bitmap, String name) {
        File appDir = new File(SDPATH);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = name + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }


    }

    public static String getSDCardBaseDir() {
        //已经挂载则获取根目录路径，并返回

        File dir = Environment.getExternalStorageDirectory();
        return dir.getAbsolutePath();

    }

    public static boolean saveData2SDCardCustomDir(byte[] data, String dir, String filename) {

        String saveDir = getSDCardBaseDir() + File.separator + dir;
        File saveFile = new File(saveDir);
        //如果不存在就创建该文件
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        String file = saveFile.getAbsolutePath() + File.separator + filename;
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(data);
            bos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {     //finally总是会执行，尽管上面已经return true;了
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

}
