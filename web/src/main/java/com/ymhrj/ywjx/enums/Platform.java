package com.ymhrj.ywjx.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author : CGS
 * Date : 2018-02-26
 * Time : 11:17
 */
public enum Platform {

    ELE(1,"饿了么"),
    BAIDU(2,"百度"),
    MEITUAN(3,"美团");

    private int value;
    private String desc;
    Platform(int value,String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    @JsonValue
    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
    /**
     * Gets by code.
     *
     * @param value the value
     * @return the by code
     */
    @JsonCreator
    public static Platform valueOf(int value) {
        for (Platform resType : values()) {
            if (resType.getValue() == value) {
                return resType;
            }
        }
        throw new RuntimeException("platform (" + value + ") not exists");
    }
}
