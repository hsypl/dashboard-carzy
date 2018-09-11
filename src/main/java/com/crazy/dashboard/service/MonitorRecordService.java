package com.crazy.dashboard.service;

import com.crazy.code.dao.GenericMapper;
import com.crazy.code.service.LongPKBaseService;
import com.crazy.code.service.ServiceProcessException;
import com.crazy.code.util.DateUtil;
import com.crazy.code.util.DateUtilExt;
import com.crazy.dashboard.dao.MonitorRecordMapper;
import com.crazy.dashboard.model.MonitorRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* 交易记录 业务处理类
*
* Created by huangshuoying on 2/8/18.
*/
@Service
public class MonitorRecordService
        extends LongPKBaseService<MonitorRecord> {
    private static final Logger log = LoggerFactory.getLogger(MonitorRecordService.class);

    @Autowired
    private MonitorRecordMapper monitorRecordMapper;

    /**
    * 获取数据层mapper接口对象，子类必须实现该方法
    *
    * @return GenericMapper<E, PK> 数据层mapper接口对象
    */
    @Override
    protected GenericMapper<MonitorRecord, Long> getMapper() {
        return monitorRecordMapper;
    }

    /**
    * 根据id获取对象，如果为空，返回空对象
    * @param id Long 公司id
    * @return MonitorRecord 交易记录对象
    */
    public MonitorRecord getSafety(Long id) {
        MonitorRecord monitorRecord = get(id);
        if (monitorRecord == null) {
            monitorRecord = new MonitorRecord();
        }
        return monitorRecord;
    }

    public MonitorRecord getLast(String symbol,Integer type){
        Map<String,Object> params = new HashMap<>();
        params.put("symbol",symbol);
        params.put("type",type);
        return monitorRecordMapper.getLast(params);
    }

    public int sendSave(String symbol,Integer type) throws ServiceProcessException {
        MonitorRecord monitorRecord = getLast(symbol,type);
        if(monitorRecord != null){
            if ((DateUtil.getTimestamp() - monitorRecord.getSendTime()) < 60*60){
                return 0;
            }
        }
        return save(symbol,type);

    }

    public int save(String symbol,Integer type) throws ServiceProcessException {
        MonitorRecord monitorRecord = new MonitorRecord();
        monitorRecord.setSendTime(DateUtilExt.getTimestamp());
        monitorRecord.setSymbol(symbol);
        monitorRecord.setType(type);
        return insert(monitorRecord);
    }

}