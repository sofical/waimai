package com.ymhrj.ywjx.controller.third;

import com.ymhrj.ywjx.controller.third.vo.EleRequestVo;
import com.ymhrj.ywjx.controller.third.vo.EleSuccessVo;
import com.ymhrj.ywjx.service.third.EleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/12/14.
 */
@RestController
@RequestMapping("/third/ele")
public class EleController {
    @Autowired
    private EleService eleService;
    @RequestMapping("/pull")
    public Object pull(@RequestBody EleRequestVo eleRequest) {
        this.eleService.pull(eleRequest);
        return new EleSuccessVo();
    }
    @RequestMapping("/create_bind")
    public void createBind(@RequestParam("shop_code") String code, HttpServletResponse response) {
        try {
            response.sendRedirect(this.eleService.bindUrl(code));
        } catch (Exception e) {

        }
    }
    @RequestMapping("/bind")
    public Object bind(@RequestParam("code") String code,
                       @RequestParam("state") String state) {
        eleService.bind(code, state);
        return "success";
    }
}
