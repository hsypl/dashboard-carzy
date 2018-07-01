package com.crazy.dashboard.service;

import com.crazy.code.dao.GenericMapper;
import com.crazy.code.service.ServiceProcessException;
import com.crazy.code.service.StringPKBaseService;
import com.crazy.dashboard.dao.CoinMarketCapMapper;
import com.crazy.dashboard.model.CoinMarketCap;
import com.google.gson.reflect.TypeToken;
import com.sungness.core.httpclient.HttpClientException;
import com.sungness.core.httpclient.HttpClientUtils;
import com.sungness.core.util.GsonUtils;
import com.sungness.core.util.tools.DoubleTools;
import com.sungness.core.util.tools.LongTools;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 市值信息记录 业务处理类
*
* Created by huangshuoying on 11/30/17.
*/
@Service
public class CoinMarketCapService
        extends StringPKBaseService<CoinMarketCap> {
    private static final Logger log = LoggerFactory.getLogger(CoinMarketCapService.class);

    @Autowired
    private CoinMarketCapMapper coinMarketCapMapper;

    private static final String GET_URL = "https://api.coinmarketcap.com/v1/ticker/?convert=CNY&limit=600";

    private static final String ONE_URL = "https://api.coinmarketcap.com/v1/ticker/";

    /**
    * 获取数据层mapper接口对象，子类必须实现该方法
    *
    * @return GenericMapper<E, PK> 数据层mapper接口对象
    */
    @Override
    protected GenericMapper<CoinMarketCap, String> getMapper() {
        return coinMarketCapMapper;
    }

    /**
    * 根据id获取对象，如果为空，返回空对象
    * @param id Long 公司id
    * @return CoinMarketCap 市值信息记录对象
    */
    public CoinMarketCap getSafety(String id) {
        CoinMarketCap coinMarketCap = get(id);
        if (coinMarketCap == null) {
            coinMarketCap = new CoinMarketCap();
        }
        return coinMarketCap;
    }

    public List<String> getIdList(){
        return coinMarketCapMapper.getIdList();
    }

    public List<CoinMarketCap> getListLeftJoinByUid(String uid){
        Map<String,Object> params= new HashMap<>();
        params.put("uid",uid);
        return coinMarketCapMapper.getListLeftJoinByUid(params);
    }

    public List<CoinMarketCap> getListByStatus(Integer status){
        Map<String,Object> params = new HashMap<>();
        params.put("status",status);
        params.put("fullordering","a.percent_change_24h DESC");
        return getList(params);
    }

    public static List<Map<String,String>> getListByJson(String result){
        Type topicType1 = new TypeToken<List<Map<String,Object>>>() {
        }.getType();
        List<Map<String,String>> resultList =
                GsonUtils.fromJson(result, topicType1);
        return resultList;
    }

    @Transactional(timeout = 1000)
    public void update() throws ServiceProcessException {
        try {
            String result = HttpClientUtils.getString(GET_URL);
            List<Map<String,String>> resultList = getListByJson(result);
            for(Map<String,String> data : resultList){
                parseData(data);
            }
        } catch (HttpClientException e) {
            update();
        }
    }

    public CoinMarketCap parseData(Map<String,String> data) throws ServiceProcessException {
        CoinMarketCap coinMarketCap = getSafety(data.get("id"));
        coinMarketCap.setId(data.get("id"));
        coinMarketCap.setName(data.get("name"));
        coinMarketCap.setSymbol(data.get("symbol"));
        coinMarketCap.setRank(Integer.parseInt(data.get("rank")));
        coinMarketCap.setPriceUsd(DoubleTools.parseDouble(data.get("price_usd")));
        coinMarketCap.setPriceBtc(DoubleTools.parseDouble(data.get("price_btc")));
        coinMarketCap.setVolumeUsd24H(DoubleTools.parseDouble(data.get("24h_volume_usd")));
        coinMarketCap.setMarketCapUsd(DoubleTools.parseDouble(data.get("market_cap_usd")));
        coinMarketCap.setPercentChange24H(DoubleTools.parseDouble(data.get("percent_change_24h")));
        coinMarketCap.setPercentChange7D(DoubleTools.parseDouble(data.get("percent_change_7d")));
        coinMarketCap.setLastUpdated((LongTools.parse(data.get("last_updated"))));
        coinMarketCap.setPriceCny(DoubleTools.parseDouble(data.get("price_cny")));
        coinMarketCap.setVolumeCny24H(DoubleTools.parseDouble(data.get("24h_volume_cny")));
        coinMarketCap.setMarketCapCny(DoubleTools.parseDouble(data.get("market_cap_cny")));
        if(StringUtils.isBlank(coinMarketCap.getId())){
            insert(coinMarketCap);
        }else {
            update(coinMarketCap);
        }
        return coinMarketCap;
    }

    public CoinMarketCap getPrice(String symbol) throws HttpClientException, ServiceProcessException {
        String result = HttpClientUtils.getString(ONE_URL+symbol+"?convert=CNY");
        List<Map<String,String>> resultList = getListByJson(result);
        return parseData(resultList.get(0));
    }

    public static void main(String[] args) throws HttpClientException {
        CoinMarketCapService coinMarketCapService = new CoinMarketCapService();
    }
}