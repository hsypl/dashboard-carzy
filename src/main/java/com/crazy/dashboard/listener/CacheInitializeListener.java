package com.crazy.dashboard.listener;

import com.crazy.dashboard.cache.MonitorCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 支付系统基础数据库缓存初始化器
 * 系统启动时，从基础数据库中加载支付平台基础信息，包括支付通道、应用信息、接入主体公司等信息，
 * 初始化完成后，由计划任务每隔1分钟刷新一次（根据记录的时间戳判断数据是否有更新）。
 * Created by wanghongwei on 11/08/17.
 */
public class CacheInitializeListener
        implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger log =
            LoggerFactory.getLogger(CacheInitializeListener.class);
    private static int callCounts = 0;

    @Autowired
    private MonitorCache monitorCache;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            //避免重复执行
            if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
                return;
            }
            log.debug(" 初始化执行");
            callCounts++;
            monitorCache.init();
            log.debug("CacheInitializeListener call times：" + callCounts);
        } catch (Exception e) {
            log.error("PaymentBaseCache init failed.");
            e.printStackTrace();
        }
    }

}
