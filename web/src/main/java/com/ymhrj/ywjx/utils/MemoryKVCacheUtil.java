package com.ymhrj.ywjx.utils;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/11/21.
 */
public class MemoryKVCacheUtil {
    private static HashMap<String, Object> cache = new HashMap<>();

    public static void set(String key, Object value) {
        cache.put(key, value);
    }


    public static <T> T get(String key) {
        return (T)cache.get(key);
    }
}
