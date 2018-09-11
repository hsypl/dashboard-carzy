package com.crazy.dashboard.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 应用信息缓存实现类
 * Created by wanghongwei on 12/08/2017.
 */
@Service
public class MonitorCache  {
    private final static Logger log = LoggerFactory.getLogger(MonitorCache.class);

    private static Map<String,List<String>> symbolExchangeList = new ConcurrentHashMap<>();

    private static Map<String,Double> disPrice = new ConcurrentHashMap<>();

    private final static double DNA_DIS_PRICE = 0.000001;

    private final static double SNC_DIS_PRICE = 0.000001;

    public void init() {
        List<String> dnaExchange = new ArrayList<>();
        dnaExchange.add("cryptopiaService");
        dnaExchange.add("okexService");
        List<String> sncExchange = new ArrayList<>();
        sncExchange.add("okexService");
        sncExchange.add("huobiService");
        symbolExchangeList.put("dna",dnaExchange);
        symbolExchangeList.put("snc",sncExchange);
        disPrice.put("dna",DNA_DIS_PRICE);
        disPrice.put("snc",SNC_DIS_PRICE);
    }

    public void refresh() {
    }

    public Map<String,List<String>> getSymbolExchangeList(){
        return symbolExchangeList;
    }

    public Map<String,Double> getDisPrice(){
        return disPrice;
    }

}
