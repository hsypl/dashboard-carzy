package com.crazy.dashboard.service.exchangeApi;


import com.crazy.code.httpclient.HttpClientException;
import com.crazy.dashboard.model.exchange.Depth;
import com.crazy.dashboard.model.exchange.DepthDetail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by developer2 on 2018/2/9.
 */
public abstract class ExchangeAbstract implements ExchangeApiInterface {

    @Override
    public Depth getDepth(String name) throws HttpClientException, IOException {
        Depth depth = new Depth(name);
        Map<String,Object> resultMap = getTradeInfo(name);
        List<DepthDetail> sellList = getSellList(resultMap);
        List<DepthDetail> buyList =  getBuyList(resultMap);
        depth.setBuyList(buyList);
        depth.setSellList(sellList);
        return depth;
    }

    protected abstract List<DepthDetail> getSellList(Map<String, Object> resultMap) throws HttpClientException;

    protected abstract List<DepthDetail> getBuyList(Map<String,Object> resultMap) throws HttpClientException;

    protected abstract Map<String,Object> getTradeInfo(String name) throws HttpClientException, IOException;

    protected List<DepthDetail> getDepthMapDetailList(List<List<Double>> orderList){
        List<DepthDetail> depthDetailList = new ArrayList<>();
        for (List<Double> child : orderList){
            DepthDetail depthDetail = new DepthDetail();
            depthDetail.setPrice(child.get(0));
            depthDetail.setTotal(child.get(1));
            depthDetailList.add(depthDetail);
        }
        return depthDetailList;
    }

}
