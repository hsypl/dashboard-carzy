package com.crazy.dashboard.model;

import com.sungness.core.util.DateUtil;

import java.io.Serializable;

/**
* 市值信息记录 Bean
*
* Created by huangshuoying on 11/30/17.
*/
public class CoinMarketCap implements Serializable {

    /** id */
    private String id;
    /** 名称 */
    private String name;
    /** 代币符号 */
    private String symbol;
    /** 排名 */
    private Integer rank;
    /** 美元 */
    private Double priceUsd;
    /** BTC价格 */
    private Double priceBtc;
    /** 24小时交易量 */
    private Double volumeUsd24H;
    /** 市值 */
    private Double marketCapUsd;
    /** 24小时涨幅 */
    private Double percentChange24H;
    /** 一星期涨幅 */
    private Double percentChange7D;
    /** 人民币 */
    private Double priceCny;
    /** 24小时交易量 */
    private Double volumeCny24H;
    /** 市值 */
    private Double marketCapCny;

    private Long lastUpdated;

    private int status;


    /**关联*/
    private Long relationId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Double getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(Double priceUsd) {
        this.priceUsd = priceUsd;
    }

    public Double getPriceBtc() {
        return priceBtc;
    }

    public void setPriceBtc(Double priceBtc) {
        this.priceBtc = priceBtc;
    }

    public Double getVolumeUsd24H() {
        return volumeUsd24H;
    }

    public void setVolumeUsd24H(Double volumeUsd24H) {
        this.volumeUsd24H = volumeUsd24H;
    }

    public Double getMarketCapUsd() {
        return marketCapUsd;
    }

    public void setMarketCapUsd(Double marketCapUsd) {
        this.marketCapUsd = marketCapUsd;
    }

    public Double getPercentChange24H() {
        return percentChange24H;
    }

    public void setPercentChange24H(Double percentChange24H) {
        this.percentChange24H = percentChange24H;
    }

    public Double getPercentChange7D() {
        return percentChange7D;
    }

    public void setPercentChange7D(Double percentChange7D) {
        this.percentChange7D = percentChange7D;
    }

    public Double getPriceCny() {
        return priceCny;
    }

    public void setPriceCny(Double priceCny) {
        this.priceCny = priceCny;
    }

    public Double getVolumeCny24H() {
        return volumeCny24H;
    }

    public void setVolumeCny24H(Double volumeCny24H) {
        this.volumeCny24H = volumeCny24H;
    }

    public Double getMarketCapCny() {
        return marketCapCny;
    }

    public void setMarketCapCny(Double marketCapCny) {
        this.marketCapCny = marketCapCny;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFormatCreateTime(){
        return DateUtil.fullFormat(lastUpdated);
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }
}