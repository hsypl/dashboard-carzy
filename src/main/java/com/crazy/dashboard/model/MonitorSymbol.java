package com.crazy.dashboard.model;

import java.io.Serializable;

/**
* 监控币种 Bean
*
* Created by huangshuoying on 3/6/18.
*/
public class MonitorSymbol implements Serializable {

    /** id */
    private Long id;
    /** 代币符号 */
    private String symbol;
    /** 交易所 */
    private String exchange;
    /** 卖价 */
    private Double sellPrice;
    /** 买价 */
    private Double buyPrice;
    /** 交易类型 */
    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}