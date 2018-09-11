package com.crazy.dashboard.config;

import java.util.Properties;

/**
 * Created by huangshuoying on 2017/3/28.
 */
public class TwitterConfig {
    /** 配置属性文件 */
    private Properties properties;

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }

    public String getConsumerKey() {
        return properties.getProperty("oauth.consumerKey");
    }

    public String getConsumerSecret() {
        return properties.getProperty("oauth.consumerSecret");
    }

    public void setProperty(String key,String value){
        properties.setProperty(key,value);
    }

    public String getCallbackUrl() {
        return properties.getProperty("callback.url");
    }

    public String getToken(){
        return properties.getProperty("token");
    }

    public String getTokenSecret(){
        return properties.getProperty("tokenSecret");
    }


}
