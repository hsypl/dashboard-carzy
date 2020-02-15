package com.crazy.dashboard.service;

import com.crazy.Data;
import com.crazy.JsonUtils;
import com.crazy.code.httpclient.HttpClientException;
import com.crazy.code.httpclient.HttpClientUtils;
import com.crazy.code.util.GsonUtils;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2018/9/12.
 */
@Service
public class CoinmarketcapApiService {

    public static void main(String[] args) throws IOException, InterruptedException, HttpClientException {
//            Document doc = Jsoup.connect("http://p.zwjhl.com/price.aspx?url=https%3A%2F%2Fdetail.tmall.com%2Fitem.htm%3Fid%3D605249734464").get();
//            Elements elements = doc.select("script");
//            System.out.println(elements.get(9).data());
//            String result = elements.get(9).data();
//            System.out.println(result.indexOf("flotChart.chartNow('"));
//            String bTest = "flotChart.chartNow('";
//            int begin = result.indexOf(bTest) + bTest.length();
//            int end = result.indexOf("','http");
//            String json = result.substring(begin, end);
//            System.out.println("[" + json + "]");
        HttpClientUtils.postJson("https://oapi.dingtalk.com/robot/send?access_token=8c41250dacd44181c24e1e4ce8693521fe43edbe0262f20435f58a132a9d323b", "");

    }


}
