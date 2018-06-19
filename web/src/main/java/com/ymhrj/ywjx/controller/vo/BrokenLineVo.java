package com.ymhrj.ywjx.controller.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author : CGS
 * Date : 2018-03-17
 * Time : 17:49
 */
@Data
public class BrokenLineVo {
    private List<String> names;
    private List<BigDecimal> values;
}
