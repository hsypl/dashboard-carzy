package com.crazy.dashboard.service.exchangeApi.okex;

import com.crazy.code.httpclient.HttpClientException;
import com.crazy.code.httpclient.HttpClientUtils;
import com.crazy.code.util.GsonUtils;
import com.crazy.dashboard.model.exchange.DepthDetail;
import com.crazy.dashboard.model.exchange.Ticket;
import com.crazy.dashboard.service.exchangeApi.ExchangeAbstract;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by developer2 on 2018/2/5.
 */
@Service
public class OkexService extends ExchangeAbstract {


    @SuppressWarnings("unchecked")
    @Override
    public List<DepthDetail> getSellList(Map<String,Object> resultMap) throws HttpClientException {
        List<List<Double>> buyList = (List<List<Double>>) resultMap.get("asks");
        List<List<Double>> orderBuyList
                = buyList.stream().sorted(Comparator.comparing(o->o.get(0)))
                .collect(Collectors.toList());
        return getDepthMapDetailList(orderBuyList);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DepthDetail> getBuyList(Map<String,Object> resultMap) throws HttpClientException {
        List<List<Double>> sellList = (List<List<Double>>) resultMap.get("bids");
        return getDepthMapDetailList(sellList);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String,Object> getTradeInfo(String name) throws HttpClientException {
        String result = HttpClientUtils.getString(
                "https://www.okex.com/api/v1/depth.do?symbol="+name+"_btc&size=3");
        return GsonUtils.toStrObjMap(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Ticket getTicket(String name, String type) throws HttpClientException {
        String result = HttpClientUtils.getString(
                "https://www.okex.com/api/v1/ticker.do?symbol="+name+"_"+type);
        Map<String,Object> resultMap = GsonUtils.toStrObjMap(result);
        Map<String,Object> ticker = (Map<String, Object>) resultMap.get("ticker");
        Ticket ticket = new Ticket();
        ticket.setFirstBuyPrice(Double.parseDouble(ticker.get("buy").toString()));
        ticket.setFirstSellPrice(Double.parseDouble(ticker.get("sell").toString()));
        return ticket;
    }


    public static String formatFloatNumber(double value) {
        if(value != 0.00){
            DecimalFormat df = new DecimalFormat("0.00000000");
            return df.format(value);
        }else{
            return "0.00";
        }
    }


    public static void main(String[] args) throws HttpClientException, IOException {
        OkexService okexService = new OkexService();
        System.out.println(GsonUtils.toJson(okexService.getTicket("snc","btc")));
//        System.out.println("aaaaaaaaaaaaaa");
//        HuobiService huobiService = new HuobiService();
//        System.out.println(GsonUtils.toJson(huobiService.getTicket("snc","btc")));
    }


}
