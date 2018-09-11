package com.crazy.dashboard.dao;


import com.crazy.code.dao.LongPKBaseMapper;
import com.crazy.dashboard.model.MonitorRecord;

import java.util.Map;

/**
* 交易记录 MyBatis 映射接口类
*
* Created by huangshuoying on 2/8/18.
*/
public interface MonitorRecordMapper
        extends LongPKBaseMapper<MonitorRecord> {

    MonitorRecord getLast(Map<String, Object> params);
}