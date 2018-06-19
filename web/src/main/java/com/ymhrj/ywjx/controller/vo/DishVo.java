package com.ymhrj.ywjx.controller.vo;

import com.ymhrj.ywjx.db.entity.Dish;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

/**
 * Created by cgs on 2017/12/18.
 */
@Data
public class DishVo extends Dish{

}
