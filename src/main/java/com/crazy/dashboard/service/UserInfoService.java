package com.crazy.dashboard.service;

import com.crazy.code.dao.GenericMapper;
import com.crazy.code.service.ServiceProcessException;
import com.crazy.code.service.StringPKBaseService;
import com.crazy.dashboard.dao.UserInfoMapper;
import com.crazy.dashboard.model.UserInfo;
import com.sungness.core.util.UuidGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
* 用户信息 业务处理类
*
* Created by huangshuoying on 11/20/17.
*/
@Service
public class UserInfoService
        extends StringPKBaseService<UserInfo> {
    private static final Logger log = LoggerFactory.getLogger(UserInfoService.class);

    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
    * 获取数据层mapper接口对象，子类必须实现该方法
    *
    * @return GenericMapper<E, PK> 数据层mapper接口对象
    */
    @Override
    protected GenericMapper<UserInfo, String> getMapper() {
        return userInfoMapper;
    }

    /**
    * 根据id获取对象，如果为空，返回空对象
    * @param id Long 公司id
    * @return UserInfo 用户信息对象
    */
    public UserInfo getSafety(String id) {
        UserInfo userInfo = get(id);
        if (userInfo == null) {
            userInfo = new UserInfo();
        }
        return userInfo;
    }


    public void save(UserInfo userInfo) throws ServiceProcessException {
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        String hashPass = encode.encode(userInfo.getPassword());
        userInfo.setPassword(hashPass);
        if(StringUtils.isBlank(userInfo.getUid())){
            userInfo.setUid(UuidGenerator.nextUid());
            insert(userInfo);
        }
        update(userInfo);
    }

}