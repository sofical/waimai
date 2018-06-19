package com.ymhrj.ywjx.controller.vo;

import lombok.Data;

import java.util.UUID;

/**
 * @author : CGS
 * Date : 2018-03-01
 * Time : 19:14
 */
@Data
public class DishForCreate {
    private String code ;
    private String name ;
    private UUID shopId ;
    private String style ;
    private String taste ;
    private String tag ;
}
