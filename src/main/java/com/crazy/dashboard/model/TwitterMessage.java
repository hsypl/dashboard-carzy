package com.crazy.dashboard.model;

import java.io.Serializable;

/**
* 推特内容 Bean
*
* Created by huangshuoying on 8/26/18.
*/
public class TwitterMessage implements Serializable {

    /** 条目id */
    private Long id;
    /** 用户名 */
    private String screenName;
    /** 内容 */
    private String content;
    /** 创建时间 */
    private Long createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

}