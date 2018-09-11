package com.crazy.dashboard.scheduling;

import com.crazy.dashboard.service.exchangeApi.ContrastService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by developer2 on 2017/9/5.
 */
@Service
public class MonitorSchedule {
    private static final Logger log = LoggerFactory.getLogger(MonitorSchedule.class);

    @Autowired
    private ContrastService contrastService;

    /**
     * 计划任务定时调度该方法
     */
    public void start(){
        log.debug("价格监控");
        contrastService.monitor();
    }

}
