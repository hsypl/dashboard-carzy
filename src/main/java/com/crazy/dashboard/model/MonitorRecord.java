package com.crazy.dashboard.model;

import java.io.Serializable;

/**
* 交易记录 Bean
*
* Created by huangshuoying on 2/8/18.
*/
public class MonitorRecord implements Serializable {

    /** id */
    private Long id;
    /** 代币符号 */
    private String symbol;
    /** 类型 */
    private Integer type;
    /** 发送时间 */
    private Long sendTime;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

}