package com.ymhrj.ywjx.controller.third;

import com.ymhrj.ywjx.service.third.CronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : CGS
 * Date : 2018-04-01
 * Time : 15:59
 */
@RestController
@RequestMapping("/third/cron")
public class CronController {
    @Autowired
    private CronService cronService;

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String formatOrders() {
        return this.cronService.formatOrder();
    }

    @RequestMapping(value = "/comments", method = RequestMethod.GET)
    public String formatComments() {
        return this.cronService.formatComment();
    }
}
