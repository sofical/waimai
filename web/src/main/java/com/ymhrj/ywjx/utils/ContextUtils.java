package com.ymhrj.ywjx.utils;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Administrator on 2017/11/27.
 */
public class ContextUtils {
    private final static  String KEY_USER_ID = "USER_ID";

    private static ThreadLocal<HashMap> local = new ThreadLocal<>();

    public static HashMap getLocal() {
        HashMap hashMap = local.get();
        if (null == hashMap) {
            hashMap = new HashMap();
            local.set(hashMap);
        }
        return hashMap;
    }

    public static <T> T get(String key, Class<T> clazz) {
        return (T) getLocal().get(key);
    }

    public static void put(String key, Object value) {
        getLocal().put(key, value);
    }

    public static UUID getUserId() {
        return get(KEY_USER_ID, UUID.class);
    }

    public static void setUserId(UUID userId) {
        put(KEY_USER_ID, userId);
    }
}
