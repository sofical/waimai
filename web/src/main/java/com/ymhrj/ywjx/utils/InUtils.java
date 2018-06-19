package com.ymhrj.ywjx.utils;

/**
 * @author : CGS
 * Date : 2018-03-25
 * Time : 17:56
 */
public class InUtils {
    public static Boolean isInArr(Object[] arr,Object target){
        for (Object o:arr){
            if(target.equals(o)){
                return true;
            }
        }
        return false;
    }
    public static void validInArr(Object[] arr,Object target){
        if(!isInArr(arr,target)){
            throw new RuntimeException("参数:"+target+" 不在"+arr.toString()+"内");
        }
    }
}
