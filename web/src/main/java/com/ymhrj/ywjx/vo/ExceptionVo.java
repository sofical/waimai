package com.ymhrj.ywjx.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by Administrator on 2017/11/22.
 */
@Data
public class ExceptionVo {
    public String code;
    public String message;
    public String host;
    public String requestId;
    public Date serverTime;
    public Object e;
}
