package com.ymhrj.ywjx.controller;

import com.ymhrj.ywjx.controller.vo.ChartVo;
import com.ymhrj.ywjx.service.base.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : CGS
 * Date : 2018-03-25
 * Time : 17:32
 */
@RestController
@RequestMapping("/api/v1/compares")
public class CompareController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/{type}", method = RequestMethod.GET)
    public List<ChartVo> getList(
            @PathVariable(value = "type") String type,
            @RequestParam(value = "dimension",required = true) String dimension
    ){
        return this.orderService.compare(type,dimension);
    }
}
