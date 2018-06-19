package com.ymhrj.ywjx.service.base;

import com.ymhrj.ywjx.controller.vo.CustomerVo;
import com.ymhrj.ywjx.controller.vo.PageData;

/**
 * The interface Customer service.
 *
 * @author : CGS Date : 2018-03-24 Time : 15:11
 */
public interface CustomerService {
    /**
     * Gets list.
     *
     * @param phone   the phone
     * @param name    the name
     * @param address the address
     * @param page    the page
     * @param limit   the limit
     * @return the list
     */
    PageData<CustomerVo> getList(String phone, String name , String address,String  beginDate,String endDate,Integer page, Integer limit);
}
