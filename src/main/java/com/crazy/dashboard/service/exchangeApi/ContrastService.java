package com.crazy.dashboard.service.exchangeApi;

import com.crazy.code.httpclient.HttpClientException;
import com.crazy.code.service.ServiceProcessException;
import com.crazy.dashboard.cache.MonitorCache;
import com.crazy.dashboard.model.MonitorSymbol;
import com.crazy.dashboard.model.enu.ExchangeTypeEnum;
import com.crazy.dashboard.model.exchange.Depth;
import com.crazy.dashboard.model.exchange.Ticket;
import com.crazy.dashboard.service.MobileInfoService;
import com.crazy.dashboard.service.MonitorRecordService;
import com.crazy.dashboard.service.WechatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by developer2 on 2018/2/8.
 */
@Service
public class ContrastService {

    private static final Logger log = LoggerFactory.getLogger(ContrastService.class);

    @Autowired
    private WechatService wechatService;

    @Autowired
    private MonitorRecordService monitorRecordService;

    @Autowired
    private MobileInfoService mobileInfoService;
    @Autowired
    private MonitorCache monitorCache;

    @Autowired
    private Map<String,ExchangeApiInterface> exchangeMap;


    public void monitor(){
        for (String name : monitorCache.getSymbolExchangeList().keySet()){
            try {
                monitor(name);
            } catch (IOException | HttpClientException | ServiceProcessException e) {
                e.printStackTrace();
            }
        }
    }

    public Ticket getTicket(MonitorSymbol monitorSymbol)
            throws IOException, HttpClientException {
        return exchangeMap.get(monitorSymbol.getExchange()).getTicket(monitorSymbol.getSymbol(),
                ExchangeTypeEnum.getDescription(monitorSymbol.getType()));
    }

    public List<Depth> getDepthList(String name) throws IOException, HttpClientException {
        List<String> exchangeList = monitorCache.getSymbolExchangeList().get(name);
        List<Depth> depthList = new ArrayList<>();
        for (String exchange : exchangeList){
            Depth depth = exchangeMap.get(exchange).getDepth(name);
            depth.setExchangeName(exchange.substring(0,3));
            depthList.add(depth);
        }
        return depthList;
    }

    public void monitor(String name) throws IOException, HttpClientException, ServiceProcessException {
        List<Depth> depthList = getDepthList(name);
        if((depthList.get(0).getFirstBuyPrice() - depthList.get(1).getFirstSellPrice()) > monitorCache.getDisPrice().get(name)
                || (depthList.get(1).getFirstBuyPrice() - depthList.get(0).getFirstSellPrice()) > monitorCache.getDisPrice().get(name)){
            mobileInfoService.sendMsg(name,"dm8ce4",2);
            String stringBuilder =depthList.get(0).getExchangeName()+"Buy:"
                    + formatFloatNumber(depthList.get(0).getFirstBuyPrice())
                    +"btc------------"
                    +depthList.get(0).getExchangeName()+"Sell:"
                    +formatFloatNumber(depthList.get(0).getFirstSellPrice())
                    +"btc------------"+depthList.get(1).getExchangeName()+"Buy:"
                    + formatFloatNumber(depthList.get(1).getFirstBuyPrice())
                    +"btc------------"+depthList.get(1).getExchangeName()+"Sell:"
                    +formatFloatNumber(depthList.get(1).getFirstSellPrice())+"btc------"+name;
            int re = monitorRecordService.sendSave(name,1);
            if (re > 0){
                wechatService.sendMessage(stringBuilder);
            }
        }

    }



    private static String formatFloatNumber(double value) {
        if(value != 0.00){
            DecimalFormat df = new DecimalFormat("0.00000000");
            return df.format(value);
        }else{
            return "0.00";
        }
    }


}
