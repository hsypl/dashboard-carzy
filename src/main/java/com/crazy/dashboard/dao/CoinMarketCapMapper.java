package com.crazy.dashboard.dao;



import com.crazy.code.dao.StringPKBaseMapper;
import com.crazy.dashboard.model.CoinMarketCap;

import java.util.List;
import java.util.Map;

/**
* 市值信息记录 MyBatis 映射接口类
*
* Created by huangshuoying on 11/30/17.
*/
public interface CoinMarketCapMapper
        extends StringPKBaseMapper<CoinMarketCap> {

    List<String> getIdList();

    List<CoinMarketCap> getListLeftJoinByUid(Map<String, Object> params);
}