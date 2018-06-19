package com.ymhrj.ywjx.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.*;

/**
 * file utils.
 */
public class FileUtil {
    /**
     * 读取配置文件
     */
    public static <T> T readConfig(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(FileUtil.class.getResourceAsStream(fileName), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                sb.append(line);
            }
            return JSON.parseObject(String.valueOf(sb), new TypeReference<T>(){});
        } catch (UnsupportedEncodingException ex){
            throw new RuntimeException("配置文件编码错误", ex);
        } catch (IOException e) {
            throw new RuntimeException("配置文件错误", e);
        }
    }
    /**
     * 删除单个文件
     */
    public static boolean deleteFile(String sPath) {
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }
}
