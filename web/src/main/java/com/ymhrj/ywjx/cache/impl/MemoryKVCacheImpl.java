package com.ymhrj.ywjx.cache.impl;

import com.ymhrj.ywjx.cache.KVCache;
import com.ymhrj.ywjx.utils.MemoryKVCacheUtil;
import org.springframework.stereotype.Component;

/**
 * Created by zj on 2017/11/21.
 */
@Component
public class MemoryKVCacheImpl implements KVCache {
    @Override
    public void set(String key, Object value) {
        MemoryKVCacheUtil.set(key, value);
    }

    @Override
    public <T> T get(String key) {
        return MemoryKVCacheUtil.get(key);
    }
}
