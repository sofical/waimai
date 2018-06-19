package com.ymhrj.ywjx.controller.third.vo;

import lombok.Data;

/**
 * Created by SONY on 2017/12/12.
 */
@Data
public class MeituanSuccessVo {
    public MeituanSuccessVo() {}
    public MeituanSuccessVo(String result) {
        this.data = result;
    }
    private String data = "success";
}
