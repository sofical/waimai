package com.ymhrj.ywjx.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ymhrj.ywjx.constant.MeituanConstant;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by zj on 2017/12/11.
 */
final public class MeituanUtils {
    /**
     * bind : https://open-erp.meituan.com/storemap?developerId=101743&businessId=2&ePoiId=1&signKey=0vwdyxv7xos6hhm7&ePoiName=原味觉醒•健康餐
     * unbind : https://open-erp.meituan.com/releasebinding?signKey=0vwdyxv7xos6hhm7&businessId=2&appAuthToken=aff688c5b23c0e357e4d535c6c6685d353379ea6adba60f65f25e3f3dd8bc3ee
     */
    final private static String URL_HEAD = "http://api.open.cater.meituan.com";
    final private static String SIGN_KEY = "0vwdyxv7xos6hhm7";
    public static <T> T get(String uri, HashMap<String, String> data, String method, Class<T> clazz) throws URISyntaxException, IOException {
        return get(uri, data, method, URL_HEAD, clazz);
    }
    public static <T> T get(String uri, HashMap<String, String> data, String method, String headUrl, Class<T> clazz) throws URISyntaxException, IOException {
        String url = headUrl + uri;
        String paramString = "";
        String postString = "";
        URIBuilder uriBuilder = new URIBuilder(url);
        List<Map.Entry<String, String>> infoIds =
                new ArrayList<>(data.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return (o1.getKey().compareTo(o2.getKey()));
            }
        });
        for (int i=0; i < infoIds.size(); i++) {
            String key = infoIds.get(i).getKey();
            String value = infoIds.get(i).getValue();
            paramString +=  key + value;
            postString += ("".equals(postString) ? "" : "&") + key + "=" + value;
            uriBuilder.setParameter(key, value);
        }
        String signString = SIGN_KEY + paramString;
        String sign = DigestUtils.sha1Hex(signString);
        uriBuilder.setParameter("sign", sign);
        postString += ("".equals(postString) ? "" : "&") + "sign=" + sign;

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        if ("GET".equals(method.toUpperCase())) {
            HttpGet httpGet = new HttpGet(String.valueOf(uriBuilder));
            response = httpClient.execute(httpGet);
        }
        if ("POST".equals(method.toUpperCase())) {
            HttpPost httpPost = new HttpPost();
            httpPost.setURI(new URI(url));
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
            StringEntity entity = new StringEntity(postString);
            entity.setChunked(false);
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
        }

        if (is2xxSuccessful(response.getStatusLine())) {
            String content = EntityUtils.toString(response.getEntity());
            // 读取完毕
            EntityUtils.consume(response.getEntity());
            return JSON.parseObject(content, clazz);
        } else {
            throw new RuntimeException("接口出错");
        }
    }

    private static List<Integer> successHttpStatus;
    static {
        successHttpStatus = new LinkedList<>();
        successHttpStatus.add(HttpStatus.SC_OK);
        successHttpStatus.add(HttpStatus.SC_CREATED);
        successHttpStatus.add(HttpStatus.SC_ACCEPTED);
        successHttpStatus.add(HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION);
        successHttpStatus.add(HttpStatus.SC_NO_CONTENT);
        successHttpStatus.add(HttpStatus.SC_RESET_CONTENT);
        successHttpStatus.add(HttpStatus.SC_PARTIAL_CONTENT);
        successHttpStatus.add(HttpStatus.SC_MULTI_STATUS);
    }

    /**
     * 校验请求是否是正确响应.
     * @param  statusLine
     * @return boolean
     */
    private static boolean is2xxSuccessful(StatusLine statusLine) {
        boolean result = false;
        if (successHttpStatus.contains(statusLine.getStatusCode())) {
            result = true;
        }
        return result;
    }

    public static String currentTimestamp() {
        return String.valueOf((int)(System.currentTimeMillis()/1000));
    }

    public static HashMap<String, String> defaultParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("charset", "UTF-8");
        params.put("timestamp", MeituanUtils.currentTimestamp());
        return params;
    }

    public static void main(String[] args) {
        /**
         * {"developerId": 100000,"ePoiId": "2","posId": "3","time": 1479022424223}
         */
        String url = "http://heartbeat.meituan.com";
        JSONObject data = new JSONObject();
        data.put("developerId", MeituanConstant.developId);
        data.put("ePoiId", "2");
        data.put("posId", "2");
        data.put("time", String.valueOf((int) (System.currentTimeMillis()/1000)));
        HashMap<String, String> request = new HashMap<>();
        request.put("data", JSONObject.toJSONString(data));
        try {
            JSONObject rsp = get("/pos/heartbeat", request, "POST", url, JSONObject.class);
            System.out.print(rsp);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
