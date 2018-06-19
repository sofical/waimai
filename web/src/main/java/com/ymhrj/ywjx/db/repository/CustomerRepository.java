package com.ymhrj.ywjx.db.repository;

import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.db.entity.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by Administrator on 2017/12/13.
 */
@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, UUID> {

    PageData<Customer> getList(String phone, String name, String address,String  beginDate,String endDate, Integer page, Integer limit);

    Customer findByPhone(String phone);
}
