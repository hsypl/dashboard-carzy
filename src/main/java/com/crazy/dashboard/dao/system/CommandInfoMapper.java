package com.crazy.dashboard.dao.system;


import com.crazy.code.dao.LongPKBaseMapper;
import com.crazy.dashboard.model.system.CommandInfo;

/**
 * 命令信息 MyBatis 映射接口类
 * Created by wanghongwei on 3/14/16.
 */
public interface CommandInfoMapper extends LongPKBaseMapper<CommandInfo> {
    /**
     * 更新命令的discard状态
     * @param commandInfo CommandInfo 命令对象
     * @return int 受影响的结果记录数
     */
    int updateDiscard(CommandInfo commandInfo);

    CommandInfo getByPath(String path);
}
