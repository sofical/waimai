package com.ymhrj.ywjx;

import org.springframework.context.annotation.ComponentScan;

/**
 * Created by zj on 2017/11/21.
 */
@ComponentScan({"com.ymhrj"})
public class Ywjx {
    public String getVersion() {
        return "0.0.1";
    }
}
