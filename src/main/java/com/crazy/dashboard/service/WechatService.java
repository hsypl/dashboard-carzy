package com.crazy.dashboard.service;

import com.crazy.code.httpclient.HttpClientException;
import com.crazy.code.httpclient.HttpClientUtils;
import com.crazy.code.util.DateUtil;
import com.crazy.code.util.GsonUtils;
import com.crazy.code.util.XmlUtils;
import com.crazy.dashboard.model.exchange.Depth;
import com.crazy.dashboard.model.exchange.DepthDetail;
import com.crazy.dashboard.service.exchangeApi.ContrastService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by developer2 on 2018/2/8.
 */
@Service
public class WechatService {

    private static final Logger log = LoggerFactory.getLogger(WechatService.class);

    private final static String URL ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxcc21e4c4b82e0696&secret=a56a487fab0515912861e8052617cc87";

    public static String token ;

    @Autowired
    private ContrastService contrastService;

    @Autowired
    private WeatherService weatherService;

    private void setToken() throws HttpClientException {
        String reuslt = HttpClientUtils.getString(URL);
        Map<String,String> resultMap = GsonUtils.toStrStrMap(reuslt);
        token = resultMap.get("access_token");
    }

    public void sendMessage(String message) throws HttpClientException {
        String content = "{\n" +
                "   \"touser\":[\n" +
                "    \"o_ANm1Nc_SER9g7qGgvlsBD-AqVc\",\n" +
                "    \"o_ANm1Nc_SER9g7qGgvlsBD-AqVc\"\n" +
                "   ],\n" +
                "    \"msgtype\": \"text\",\n" +
                "    \"text\": { \"content\": \""+message+"\"}\n" +
                "}";
        log.debug("wechatCont="+content);
        String post = HttpClientUtils.postJson("https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token="+token,content);
        Map<String,String> resultMap = GsonUtils.toStrStrMap(post);
        if(!resultMap.get("errcode").equals("0")){
            setToken();
            HttpClientUtils.postJson("https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token="+token,content);
        }
    }

    public String getContent(String content) throws Exception {
        if(content.contains("10000")){
            return getWeather(content.substring(5));
        }else {
            return getSymbol(content);
        }
    }

    public String reply(HttpServletRequest request) throws Exception {
        Map<String,String> result = XmlUtils.toMap(parseRequest(request).getBytes(),"UTF-8");
        String fromUser = result.get("FromUserName");
        String toUser = result.get("ToUserName");
        result.remove("MsgId");
        result.put("CreateTime", DateUtil.getTimestamp().toString());
        result.put("Content",getContent(result.get("Content")));
        result.put("FromUserName",toUser);
        result.put("ToUserName",fromUser);
        return XmlUtils.parseXML(result);
    }

    public String getWeather(String city) throws HttpClientException {
        return GsonUtils.toJson(weatherService.getWeather(city));
    }

    public String getSymbol(String name) throws IOException, HttpClientException {
        String result = "";
        List<Depth> depthList = contrastService.getDepthList(name);
        for (Depth depth : depthList){
            List<DepthDetail> sellList = depth.getSellList().subList(0,3);
            List<DepthDetail> buyList = depth.getBuyList().subList(0,3);
            result += depth.getExchangeName()+":";
            for (DepthDetail depthDetail : sellList){
                result += "("+formatFloatNumber(depthDetail.getPrice())+"-"+ (int)depthDetail.getTotal()+")";
            }
            result += "///";
            for (DepthDetail depthDetail : buyList){
                result += "("+formatFloatNumber(depthDetail.getPrice())+"-"+(int)depthDetail.getTotal()+")";
            }
            result += "///";
        }

        return result;
    }

    public static String formatFloatNumber(double value) {
        if(value != 0.00){
            DecimalFormat df = new DecimalFormat("0.00000000");
            return df.format(value);
        }else{
            return "0.00";
        }
    }

    /**
     *  返回post请求String内容
     * @param request HttpServletRequest
     * @return String
     */
    public  String parseRequest(HttpServletRequest request)
            throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder reportBuilder = new StringBuilder();
        String tempStr;
        while ((tempStr = reader.readLine()) != null) {
            reportBuilder.append(tempStr);
        }
        return reportBuilder.toString();
    }
}
