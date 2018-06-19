package com.ymhrj.ywjx.controller.third;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ymhrj.ywjx.controller.third.vo.BaiduRequestVo;
import com.ymhrj.ywjx.service.third.BaiduService;
import com.ymhrj.ywjx.utils.baidu.Cmd;
import com.ymhrj.ywjx.utils.baidu.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by Administrator on 2017/12/15.
 */
@RestController
@RequestMapping("/third/baidu")
public class BaiduController {
    @Autowired
    private BaiduService baiduService;

    private static final String BAIDU_HOST = "http://api.waimai.baidu.com/";
    private static final String SOURCE = "63754";
    private static final String SECRET = "13181419189399de";


    @RequestMapping("/checkin/{order_id}")
    public Object checkIn(@PathVariable("order_id") String orderId){



        return JSON.parseObject(this.send(orderId,"order.status.push"));
    }
    private String send(String orderId,String cmdStr){
        Cmd cmd = new Cmd();
        cmd.setCmd(cmdStr);
        cmd.setSource(SOURCE);
        cmd.setSecret(SECRET);
        cmd.setTicket(UUID.randomUUID().toString().toUpperCase());
        cmd.setTimestamp((int)(System.currentTimeMillis() / 1000));
        cmd.setVersion(3);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("order_id",orderId);
        jsonObject.put("status",4);
        cmd.setBody(jsonObject);
        cmd.setSign(null);
        cmd.setEncrypt("");

        String bodyJson = jsonObject.toJSONString().replace("/", "\\/");
        //中文字符转为unicode
        //	requestJson = chinaToUnicode(requestJson);
        System.out.print(cmd.getCmd());
        String  md5Input = "";
        md5Input+="body="+bodyJson+"&";
        md5Input+="cmd="+String.valueOf(cmd.getCmd())+"&";
        md5Input+="encrypt="+String.valueOf(cmd.getEncrypt())+"&";
        md5Input+="secret="+String.valueOf(cmd.getSecret())+"&";
        md5Input+="source="+String.valueOf(cmd.getSource())+"&";
        md5Input+="ticket="+String.valueOf(cmd.getTicket())+"&";
        md5Input+="timestamp="+String.valueOf(cmd.getTimestamp())+"&";
        md5Input+="version="+String.valueOf(cmd.getVersion());

        System.out.print(md5Input);
        System.out.println('\n');
        String sign = getMD5(md5Input);
        cmd.setSign(sign);

        String  requestParam = "";
        requestParam+="cmd="+String.valueOf(cmd.getCmd())+"&";
        requestParam+="version="+String.valueOf(cmd.getVersion())+"&";
        requestParam+="timestamp="+String.valueOf(cmd.getTimestamp())+"&";
        requestParam+="ticket="+String.valueOf(cmd.getTicket())+"&";
        requestParam+="source="+String.valueOf(cmd.getSource())+"&";
        requestParam+="sign="+String.valueOf(cmd.getSign())+"&";
        requestParam+="body="+bodyJson+"&";
        requestParam+="encrypt="+String.valueOf(cmd.getEncrypt());


        String result = HttpRequest.sendPost(BAIDU_HOST, requestParam);
        System.out.print(result);
        return  result;
    }
    private   String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext.toUpperCase();
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/pull")
    public Object pull(@RequestBody BaiduRequestVo baiduRequestVo) {
        return baiduService.pull(baiduRequestVo);
    }





}
