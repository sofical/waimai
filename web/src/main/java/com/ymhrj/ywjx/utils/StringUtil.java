package com.ymhrj.ywjx.utils;

import java.security.SecureRandom;

/**
 * Created by Administrator on 2017/11/23.
 */
public class StringUtil {
    public static String randChars(Integer length) {
        char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
                'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        // 创建一个随机数生成器类
        SecureRandom random = new SecureRandom();
        StringBuffer randomCode = new StringBuffer();
        // 随机产生codeCount
        for (int i = 0; i < length; i++) {
            String code = String.valueOf(codeSequence[random.nextInt(36)]);
            // 将产生的四个随机数组合在一起。
            randomCode.append(code);
        }
        return randomCode.toString();
    }
}
