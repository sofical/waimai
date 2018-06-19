package com.ymhrj.ywjx.service.base.impl;

import com.ymhrj.ywjx.controller.vo.CustomerVo;
import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.db.entity.Customer;
import com.ymhrj.ywjx.db.repository.CustomerRepository;
import com.ymhrj.ywjx.service.base.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : CGS
 * Date : 2018-03-24
 * Time : 15:18
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

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
    @Override
    public PageData<CustomerVo> getList(String phone, String name, String address, String  beginDate,String endDate,Integer page, Integer limit) {
        PageData<Customer> result = customerRepository.getList(phone,name,address,beginDate,endDate,page,limit);
        PageData<CustomerVo> voPageData = new PageData<>();
        List<CustomerVo> customerVoList = new ArrayList<>();
        for (Customer customer : result.getData()){
            CustomerVo vo = this.poToVo(customer);
            customerVoList.add(vo);
        }
        voPageData.setData(customerVoList);
        voPageData.setCount(result.getCount());
        return voPageData;
    }


    private CustomerVo  poToVo(Customer customer){
        CustomerVo vo = new CustomerVo();
        BeanUtils.copyProperties(customer,vo);
        return vo;
    }
}
