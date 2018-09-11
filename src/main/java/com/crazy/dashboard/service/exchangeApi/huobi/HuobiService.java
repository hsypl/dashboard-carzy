package com.crazy.dashboard.service.exchangeApi.huobi;

import com.crazy.code.httpclient.HttpClientException;
import com.crazy.code.httpclient.HttpClientUtils;
import com.crazy.code.util.GsonUtils;
import com.crazy.dashboard.model.exchange.DepthDetail;
import com.crazy.dashboard.model.exchange.Ticket;
import com.crazy.dashboard.service.exchangeApi.ExchangeAbstract;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by developer2 on 2018/2/8.
 */
@Service
public class HuobiService extends ExchangeAbstract {

    @SuppressWarnings("unchecked")
    @Override
    public List<DepthDetail> getSellList(Map<String,Object> resultMap) throws HttpClientException {
        List<List<Double>> sellList = (List<List<Double>>) resultMap.get("asks");
        return getDepthMapDetailList(sellList);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DepthDetail> getBuyList(Map<String,Object> resultMap) throws HttpClientException {
        List<List<Double>> buyList = (List<List<Double>>) resultMap.get("bids");
        return getDepthMapDetailList(buyList);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String,Object> getTradeInfo(String name) throws HttpClientException, IOException {
        String url = "https://api.huobipro.com/market/depth?symbol="+name+"btc&type=step0";
        Map<String,Object> resultMap = getResult(url);
        return (Map<String, Object>) resultMap.get("tick");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Ticket getTicket(String name, String type) throws IOException, HttpClientException {
        String url = "https://api.huobipro.com/market/detail/merged?symbol="+name+type;
        Map<String, Object> detail = (Map<String, Object>) getResult(url).get("tick");
        Ticket ticket = new Ticket();
        ticket.setFirstBuyPrice(Double.parseDouble(((List) detail.get("bid")).get(0).toString()));
        ticket.setFirstSellPrice(Double.parseDouble(((List) detail.get("ask")).get(0).toString()));
        return ticket;
    }


    public Map<String,Object> getResult(String url) throws IOException, HttpClientException {
        return GsonUtils.toStrObjMap(HttpClientUtils.getString(url));
    }

    public static String formatFloatNumber(double value) {
        if(value != 0.00){
            DecimalFormat df = new DecimalFormat("########0.000000000#");
            return df.format(value);
        }else{
            return "0.00";
        }
    }

    public static void main(String[] args) throws IOException, HttpClientException {
        HuobiService huobiService = new HuobiService();
        System.out.println(GsonUtils.toJson(huobiService.getTradeInfo("smt")));
    }

}
