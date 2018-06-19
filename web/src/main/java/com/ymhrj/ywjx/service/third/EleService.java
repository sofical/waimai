package com.ymhrj.ywjx.service.third;

import com.ymhrj.ywjx.controller.third.vo.EleRequestVo;

/**
 * Created by Administrator on 2017/12/14.
 */
public interface EleService {
    /**
     * 接收订单
     * @param eleRequest
     */
    void pull(EleRequestVo eleRequest);

    /**
     * 绑定地址.
     * @param state
     * @return
     */
    String bindUrl(String state);

    /**
     * 绑定.
     * @param code
     * @param state
     */
    void bind(String code, String state);

    /**
     * 同步评价.
     */
    void syncComment();
}
