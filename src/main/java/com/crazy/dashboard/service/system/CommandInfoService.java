package com.crazy.dashboard.service.system;

import com.crazy.code.dao.GenericMapper;
import com.crazy.dashboard.dao.system.CommandInfoMapper;
import com.crazy.dashboard.model.system.CommandInfo;
import com.crazy.code.service.LongPKBaseService;
import com.crazy.code.service.ServiceProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 命令信息业务处理类
 * Created by wanghongwei on 3/14/16.
 */
@Service
public class CommandInfoService extends LongPKBaseService<CommandInfo> {
    private static final Logger log = LoggerFactory.getLogger(CommandInfoService.class);

    @Autowired
    private CommandInfoMapper commandInfoMapper;

    /**
     * 获取数据层mapper接口对象，子类必须实现该方法
     *
     * @return GenericMapper<E, PK> 数据层mapper接口对象
     */
    @Override
    protected GenericMapper<CommandInfo, Long> getMapper() {
        return commandInfoMapper;
    }

    /**
     * 保存命令信息，根据访问路径查询是否已有记录，如果存在则更新，如果不存在则插入。
     * @param commandInfo CommandInfo 命令信息对象
     * @throws ServiceProcessException
     */
    public void save(CommandInfo commandInfo) throws ServiceProcessException {
        CommandInfo oldCommandInfo = getByPath(commandInfo.getPath());
        if (oldCommandInfo != null) {
            commandInfo.setId(oldCommandInfo.getId());
            update(commandInfo);
        } else {
            insert(commandInfo);
        }
    }

    /**
     * 获取当前有效命令信息
     * @return 命令列表
     */
    public List<CommandInfo> getVailMenuInfoList(){
        Map<String, Object> params = new HashMap<>();
        params.put("discard", false);
        return getList(params);
    }

    public List<CommandInfo> getListByModuleId(Long moduleId){
        Map<String,Object> params = new HashMap<>();
        params.put("parentId",moduleId);
        return getList(params);
    }


    /**
     * 根据包名获取命令信息对象
     * @param path String 访问路径字符串
     * @return CommandInfo 命令信息对象
     */
    public CommandInfo getByPath(String path) {
        return commandInfoMapper.getByPath(path);
    }

    /**
     * 更新命令的discard状态
     * @param commandInfo CommandInfo 命令对象
     * @return int 受影响的结果记录数
     */
    public int updateDiscard(CommandInfo commandInfo) {
        return commandInfoMapper.updateDiscard(commandInfo);
    }

    /**
     * 获取菜单集合，pkgName为 key，菜单对象为 value
     * @return Map<String, CommandInfo>
     */
    public Map<String, CommandInfo> getCommandMap() {
        Map<String, CommandInfo> commandMap = new HashMap<>();
        List<CommandInfo> commandInfoList = getList();
        for (CommandInfo commandInfo: commandInfoList) {
            commandMap.put(commandInfo.getPath(), commandInfo);
        }
        return commandMap;
    }

}
