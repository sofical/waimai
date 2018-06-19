package com.ymhrj.ywjx.cache;

/**
 * Created by zj on 2017/11/21.
 */
public interface KVCache {
    void set(String key, Object value);

    <T> T get(String key);
}
