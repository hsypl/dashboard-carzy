package com.crazy.dashboard.service;

import com.crazy.code.dao.GenericMapper;
import com.crazy.code.service.LongPKBaseService;
import com.crazy.code.service.ServiceProcessException;
import com.crazy.code.util.DateUtil;
import com.crazy.dashboard.dao.TwitterMessageMapper;
import com.crazy.dashboard.model.TwitterMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* 推特内容 业务处理类
*
* Created by huangshuoying on 8/26/18.
*/
@Service
public class TwitterMessageService
        extends LongPKBaseService<TwitterMessage> {
    private static final Logger log = LoggerFactory.getLogger(TwitterMessageService.class);

    @Autowired
    private TwitterMessageMapper twitterMessageMapper;

    /**
    * 获取数据层mapper接口对象，子类必须实现该方法
    *
    * @return GenericMapper<E, PK> 数据层mapper接口对象
    */
    @Override
    protected GenericMapper<TwitterMessage, Long> getMapper() {
        return twitterMessageMapper;
    }

    /**
    * 根据id获取对象，如果为空，返回空对象
    * @param id Long 公司id
    * @return TwitterMessage 推特内容对象
    */
    public TwitterMessage getSafety(Long id) {
        TwitterMessage twitterMessage = get(id);
        if (twitterMessage == null) {
            twitterMessage = new TwitterMessage();
        }
        return twitterMessage;
    }

    public int save(String content,String screenName) throws ServiceProcessException {
        TwitterMessage twitterMessage = new TwitterMessage();
        twitterMessage.setContent(content);
        twitterMessage.setCreateTime(DateUtil.getTimestamp());
        twitterMessage.setScreenName(screenName);
        return insert(twitterMessage);
    }
}