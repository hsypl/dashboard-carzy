package com.crazy.dashboard.service;

import com.crazy.code.httpclient.HttpClientException;
import com.crazy.code.httpclient.HttpClientUtils;
import com.crazy.code.service.ServiceProcessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by developer2 on 2018/2/6.
 */
@Service
public class MobileInfoService {

    private final static String APP_ID = "19922";

    private final static String SECRET = "e5fc227faa56b5581e680028f07a9a3e";

    private final static String SEND_URL = "https://api.submail.cn/message/xsend.json";

    @Autowired
    private MonitorRecordService monitorRecordService;

    /**
     * 发送参数到url
     * @return String , 解析发送接口返回的json  status为success表示成功，error表示失败
     */
    private void sendToUrl(String name,String project) throws HttpClientException,
            UnsupportedEncodingException, ServiceProcessException {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("appid", APP_ID);
        map.put("to", "13750450369");
        map.put("project", project);
        map.put("vars", "{\"name\":\"" + name + "\"}");
        map.put("signature", SECRET);
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : map.keySet()) {
            stringBuilder.append(key)
                    .append("=").append(URLEncoder.encode(map.get(key), "UTF-8"))
                    .append("&");
        }
        String response = HttpClientUtils.postForm(SEND_URL,
                stringBuilder.substring(0,stringBuilder.length()-1));
        System.out.print(response);
    }

    public void sendMsg(String name,String project,Integer type) throws ServiceProcessException, UnsupportedEncodingException, HttpClientException {
        int re = monitorRecordService.sendSave(name,type);
        if(re > 0){
            sendToUrl(name,project);
        }
    }

    public static void main(String[] args) throws HttpClientException, UnsupportedEncodingException, ServiceProcessException {
        MobileInfoService mobileInfoService = new MobileInfoService();
    }
}
