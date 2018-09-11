package com.crazy.dashboard.model.exchange;

import java.util.List;

/**
 * Created by developer2 on 2018/2/8.
 */
public class Depth {

    private String name;

    private List<DepthDetail> sellList;

    private List<DepthDetail> buyList;

    private String exchangeName;

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public Depth(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<DepthDetail> getSellList() {
        return sellList;
    }

    public void setSellList(List<DepthDetail> sellList) {
        this.sellList = sellList;
    }

    public List<DepthDetail> getBuyList() {
        return buyList;
    }

    public void setBuyList(List<DepthDetail> buyList) {
        this.buyList = buyList;
    }

    public double getFirstBuyPrice() {
        return buyList.get(0).getPrice();
    }

    public double getFirstSellPrice() {
        return sellList.get(0).getPrice();
    }

}
