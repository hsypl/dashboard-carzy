package com.crazy.dashboard.service.exchangeApi.cryptopia;

import com.crazy.code.httpclient.HttpClientException;
import com.crazy.code.httpclient.HttpClientUtils;
import com.crazy.code.util.GsonUtils;
import com.crazy.dashboard.model.exchange.DepthDetail;
import com.crazy.dashboard.model.exchange.Ticket;
import com.crazy.dashboard.service.exchangeApi.ExchangeAbstract;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by developer2 on 2018/1/12.
 */
@Service
public class CryptopiaService extends ExchangeAbstract {

    private static final Logger log = LoggerFactory.getLogger(CryptopiaService.class);


    @SuppressWarnings("unchecked")
    @Override
    public List<DepthDetail> getSellList(Map<String,Object> resultMap) throws HttpClientException {
        List<Map<String,Object>> sellList = (List<Map<String, Object>>) resultMap.get("Sell");
        return getDepthDoubleDetailList(sellList);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DepthDetail> getBuyList(Map<String,Object> resultMap) throws HttpClientException {
        List<Map<String,Object>> buyList = (List<Map<String, Object>>) resultMap.get("Buy");
        return getDepthDoubleDetailList(buyList);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String,Object> getTradeInfo(String name) throws HttpClientException {
        String url = "https://www.cryptopia.co.nz/api/GetMarketOrders/"+name+"_BTC/10";
        String result = HttpClientUtils.getString(url);
        Map<String,Object> resultMap = GsonUtils.toStrObjMap(result);
        return (Map<String, Object>) resultMap.get("Data");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Ticket getTicket(String name, String type) throws HttpClientException {
        String url = "https://www.cryptopia.co.nz/api/GetMarket/"+name+"_"+type;
        String result = HttpClientUtils.getString(url);
        Map<String,Object> resultMap = GsonUtils.toStrObjMap(result);
        Map<String, Object> data = (Map<String, Object>) resultMap.get("Data");
        Ticket ticket = new Ticket();
        ticket.setFirstSellPrice(Double.parseDouble(data.get("AskPrice").toString()));
        ticket.setFirstBuyPrice(Double.parseDouble(data.get("BidPrice").toString()));
        return ticket;
    }

    public List<DepthDetail> getDepthDoubleDetailList(List<Map<String,Object>> detailList){
        List<DepthDetail> depthDetailList = new ArrayList<>();
        for (Map<String,Object> child : detailList){
            DepthDetail depthDetail = new DepthDetail();
            depthDetail.setPrice((Double) child.get("Price"));
            depthDetail.setTotal((Double) child.get("Volume"));
            depthDetailList.add(depthDetail);
        }
        return depthDetailList;
    }

    public static void main(String[] args) throws Exception {

    }
}
