package com.crazy.dashboard.service.exchangeApi;

import com.crazy.code.dao.GenericMapper;
import com.crazy.code.httpclient.HttpClientException;
import com.crazy.code.service.LongPKBaseService;
import com.crazy.code.service.ServiceProcessException;
import com.crazy.dashboard.dao.MonitorSymbolMapper;
import com.crazy.dashboard.model.MonitorSymbol;
import com.crazy.dashboard.model.exchange.Ticket;
import com.crazy.dashboard.service.MobileInfoService;
import com.crazy.dashboard.service.MonitorRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
* 监控币种 业务处理类
*
* Created by huangshuoying on 3/6/18.
*/
@Service
public class MonitorSymbolService
        extends LongPKBaseService<MonitorSymbol> {
    private static final Logger log = LoggerFactory.getLogger(MonitorSymbolService.class);

    @Autowired
    private MonitorSymbolMapper monitorSymbolMapper;

    @Autowired
    private ContrastService contrastService;

    @Autowired
    private MonitorRecordService monitorRecordService;

    @Autowired
    private MobileInfoService mobileInfoService;

    /**
    * 获取数据层mapper接口对象，子类必须实现该方法
    *
    * @return GenericMapper<E, PK> 数据层mapper接口对象
    */
    @Override
    protected GenericMapper<MonitorSymbol, Long> getMapper() {
        return monitorSymbolMapper;
    }

    /**
    * 根据id获取对象，如果为空，返回空对象
    * @param id Long 公司id
    * @return MonitorSymbol 监控币种对象
    */
    public MonitorSymbol getSafety(Long id) {
        MonitorSymbol monitorSymbol = get(id);
        if (monitorSymbol == null) {
            monitorSymbol = new MonitorSymbol();
        }
        return monitorSymbol;
    }

    public void monitor(){
        List<MonitorSymbol> monitorSymbolList = getList();
        monitorSymbolList.forEach(monitorSymbol -> {
            try {
                monitor(monitorSymbol);
            } catch (IOException | HttpClientException | ServiceProcessException e) {
                e.printStackTrace();
            }
        });
    }

    @Async
    public void monitor(MonitorSymbol monitorSymbol)
            throws IOException, HttpClientException, ServiceProcessException {
        Ticket ticket = contrastService.getTicket(monitorSymbol);
        if(ticket.getFirstSellPrice() <= monitorSymbol.getBuyPrice()
                || ticket.getFirstBuyPrice() >= monitorSymbol.getSellPrice()){
            mobileInfoService.sendMsg(monitorSymbol.getSymbol(),"emp9J4",3);
        }

    }
}