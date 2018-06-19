package com.ymhrj.ywjx.controller.vo;

import lombok.Data;

import java.util.List;

/**
 * PageData.
 *
 * @author zj.
 *         Created on 2018/2/21 0021.
 */
@Data
public class PageData<T> {
    private List<T> data;
    private Integer count;
    private Integer code = 0;
    private String msg = "";
}
