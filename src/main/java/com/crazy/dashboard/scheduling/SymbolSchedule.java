package com.crazy.dashboard.scheduling;

import com.crazy.dashboard.service.exchangeApi.MonitorSymbolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by developer2 on 2018/3/6.
 */
@Service
public class SymbolSchedule {

    @Autowired
    private MonitorSymbolService monitorSymbolService;

    public void start(){
        monitorSymbolService.monitor();
    }
}
