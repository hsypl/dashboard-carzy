package com.crazy.dashboard.service.system;

import com.crazy.code.annotation.Menu;
import com.crazy.code.dao.GenericMapper;
import com.crazy.code.web.JstreeItem;
import com.crazy.dashboard.dao.system.UserPrivilegeMapper;
import com.crazy.dashboard.model.system.*;
import com.crazy.code.service.LongPKBaseService;
import com.crazy.code.service.ServiceProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* 合作方用户权限信息 业务处理类
*
* Created by Chwing on 8/31/17.
*/
@Service
public class UserPrivilegeService
        extends LongPKBaseService<UserPrivilege> {
    private static final Logger log = LoggerFactory.getLogger(UserPrivilegeService.class);

    @Autowired
    private UserPrivilegeMapper userPrivilegeMapper;

    @Autowired
    private MenuInfoService menuInfoService;

    @Autowired
    private ModuleInfoService moduleInfoService;

    @Autowired
    private CommandInfoService commandInfoService;

    @Autowired
    private ModuleTree moduleTree;


    /**
    * 获取数据层mapper接口对象，子类必须实现该方法
    *
    * @return GenericMapper<E, PK> 数据层mapper接口对象
    */
    @Override
    protected GenericMapper<UserPrivilege, Long> getMapper() {
        return userPrivilegeMapper;
    }

    /**
    * 根据id获取对象，如果为空，返回空对象
    * @param id Long 公司id
    * @return UserPrivilege 合作方用户权限信息对象
    */
    public UserPrivilege getSafety(Long id) {
        UserPrivilege UserPrivilege = get(id);
        if (UserPrivilege == null) {
            UserPrivilege = new UserPrivilege();
        }
        return UserPrivilege;
    }

    /**
     * 根据用户id获取用户权限列表
     * @param userId Long 用户id
     * @return List<UrpUserPrivilege> 用户权限列表
     */
    public List<UserPrivilege> getListByUserId(String userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return getList(params);
    }

    /**
     * 根据用户id获取用户权限列表
     * @param userId Long 用户id
     * @return List<UrpUserPrivilege> 用户权限列表
     */
    public List<UserPrivilege> getListByUserIdAndType(String userId,Integer itemType) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("itemType", itemType);
        return getList(params);
    }

    public List<Long> getIdListByUserIdAndType(String userId,Integer itemType){
        return getListByUserIdAndType(userId,itemType).stream()
                .map(UserPrivilege::getId).collect(Collectors.toList());
    }

    public List<Long> getItemIdListByUserIdAndType(String userId,Integer itemType){
        return getListByUserIdAndType(userId,itemType).stream()
                .map(UserPrivilege::getItemId).collect(Collectors.toList());
    }

    /**
     * 将用户权限列表构造成以"类型-id"为key的Map
     * @param privilegeList List<UrpUserPrivilege> 权限列表
     * @return Map<String, UrpUserPrivilege> 权限集合
     */
    private Map<String, UserPrivilege> buildPrivilegeMap(
            List<UserPrivilege> privilegeList) {
        Map<String, UserPrivilege> privilegeMap = new HashMap<>();
        for (UserPrivilege privilege: privilegeList) {
            privilegeMap.put(privilege.getPrivilegeKey(), privilege);
        }
        return privilegeMap;
    }

    public void save(Long itemId,Integer type,String userId) throws ServiceProcessException {
        UserPrivilege userPrivilege = new UserPrivilege();
        userPrivilege.setItemId(itemId);
        userPrivilege.setItemType(type);
        userPrivilege.setUserId(userId);
        insert(userPrivilege);
    }

//    public void save(UserInfo userInfo) throws ServiceProcessException {
//        List<ModuleInfo> moduleInfoList;
//        if (JudgeEnum.valueOf(userInfo.getAdmin()) == JudgeEnum.YES){
//            moduleInfoList = moduleInfoService.getVailMenuInfoList();
//        }else {
//            moduleInfoList = moduleInfoService.getListByModuleIdList(userInfo.getModuleIdList());
//        }
//        if(!moduleInfoList.isEmpty()){
//            saveByModuleInfoList(moduleInfoList,userInfo);
//        }else {
//            for (UserPrivilege userPrivilege : getListByUserId(userInfo.getUid())){
//                delete(userPrivilege.getId());
//            }
//        }
//
//    }

//    public void saveByModuleInfoList(List<ModuleInfo> moduleInfoList,UserInfo userInfo) throws ServiceProcessException {
//        List<Long> oldModuleIdList
//                = getItemIdListByUserIdAndType(userInfo.getUid(),ItemTypeEnum.MODULE.getValue());
//        List<Long> oldCommandIdList
//                = getItemIdListByUserIdAndType(userInfo.getUid(),ItemTypeEnum.COMMAND.getValue());
//        for (ModuleInfo moduleInfo : moduleInfoList){
//            if(!oldModuleIdList.contains(moduleInfo.getId())){
//                save(moduleInfo.getId(),ItemTypeEnum.MODULE.getValue(),userInfo.getUid());
//                //保存command
//                List<CommandInfo> commandInfoList = commandInfoService.getListByModuleId(moduleInfo.getId());
//                for (CommandInfo commandInfo : commandInfoList){
//                    if (!oldCommandIdList.contains(commandInfo.getId())){
//                        save(commandInfo.getId(),ItemTypeEnum.COMMAND.getValue(),userInfo.getUid());
//                    }
//                    oldCommandIdList.remove(commandInfo.getId());
//                }
//            }
//            oldModuleIdList.remove(moduleInfo.getId());
//            List<CommandInfo> commandInfoList = commandInfoService.getListByModuleId(moduleInfo.getId());
//            for (CommandInfo commandInfo : commandInfoList){
//                oldCommandIdList.remove(commandInfo.getId());
//            }
//        }
//        for (Long deleteModuleId : oldModuleIdList){
//            deleteByItemIdAndUserIdAndType(userInfo.getUid(),deleteModuleId,ItemTypeEnum.MODULE.getValue());
//        }
//        for (Long deleteCommandId : oldCommandIdList){
//            deleteByItemIdAndUserIdAndType(userInfo.getUid(),deleteCommandId,ItemTypeEnum.COMMAND.getValue());
//        }
//    }

    public UserPrivilege getByUserAndItemIdAndType(String userId,Long itemId,Integer type){
        Map<String,Object> params = new HashMap<>();
        params.put("userId",userId);
        params.put("itemId",itemId);
        params.put("itemType",type);
        return getByDynamicWhere(params);
    }

    public void deleteByItemIdAndUserIdAndType(String userId,Long itemId,Integer type) throws
            ServiceProcessException {
        UserPrivilege userPrivilege = getByUserAndItemIdAndType(userId,itemId,type);
        delete(userPrivilege.getId());
    }

    public JstreeItem getUserPrivilege(String userId){
        List<Long> commandIdList
                = getItemIdListByUserIdAndType(userId,ItemTypeEnum.COMMAND.getValue());
        Map<String,Boolean> state = new HashMap<>();
        state.put("opened",true);
        JstreeItem jstreeItem = new JstreeItem();
        jstreeItem.setState(state);
        jstreeItem.setId("0");
        jstreeItem.setText("所有权限");
        setMenu(jstreeItem,commandIdList);
        return jstreeItem;
    }

    public void setMenu(JstreeItem rootItem,List<Long> commandIdList){
        for (MenuInfo menuInfo : moduleTree.getRootMenuList()) {
            JstreeItem menuItem = new JstreeItem();
            menuItem.setId(menuInfo.getMenuKey());
            menuItem.setText(menuInfo.getValue());
            setModule(menuInfo,menuItem,commandIdList);
            rootItem.setChildren(menuItem);
        }

    }

    public void setModule(MenuInfo menuInfo,JstreeItem menuItem,List<Long> commandIdList){
        for (ModuleInfo moduleInfo :menuInfo.getModuleList()) {
            JstreeItem moduleItem = new JstreeItem();
            moduleItem.setId(moduleInfo.getModuleKey());
            moduleItem.setText(moduleInfo.getValue());
            setCommand(moduleInfo,moduleItem,commandIdList);
            menuItem.setChildren(moduleItem);
        }
    }

    public void setCommand(ModuleInfo moduleInfo,JstreeItem moduleItem,List<Long> commandIdList){
        for (CommandInfo commandInfo : moduleInfo.getCommandList()){
            JstreeItem commandItem = new JstreeItem();
            commandItem.setId(commandInfo.getCommandKey());
            commandItem.setText(commandInfo.getValue());
            moduleItem.setChildren(commandItem);
            if (commandIdList.contains(commandInfo.getId())){
                Map<String,Boolean> state = new HashMap<>();
                state.put("select",true);
                commandItem.setState(state);
            }
        }
    }




}