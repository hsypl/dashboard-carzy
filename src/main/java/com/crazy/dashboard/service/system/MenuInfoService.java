package com.crazy.dashboard.service.system;

import com.crazy.code.dao.GenericMapper;
import com.crazy.dashboard.dao.system.MenuInfoMapper;
import com.crazy.dashboard.model.system.MenuInfo;
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
 * 菜单信息业务处理类
 * Created by wanghongwei on 3/14/16.
 */
@Service
public class MenuInfoService extends LongPKBaseService<MenuInfo> {
    private static final Logger log = LoggerFactory.getLogger(MenuInfoService.class);

    @Autowired
    private MenuInfoMapper menuInfoMapper;

    /**
     * 获取数据层mapper接口对象，子类必须实现该方法
     *
     * @return GenericMapper<E, PK> 数据层mapper接口对象
     */
    @Override
    protected GenericMapper<MenuInfo, Long> getMapper() {
        return menuInfoMapper;
    }

    /**
     * 保存菜单信息，根据包名查询是否已有记录，如果存在则更新，如果不存在则插入。
     * @param menuInfo MenuInfo 菜单信息对象
     * @throws ServiceProcessException
     */
    public void save(MenuInfo menuInfo) throws ServiceProcessException {
        MenuInfo oldMenuInfo = getByPkgName(menuInfo.getPkgName());
        if (oldMenuInfo != null) {
            menuInfo.setId(oldMenuInfo.getId());
            update(menuInfo);
        } else {
            insert(menuInfo);
        }
    }

    /**
     * 根据包名获取菜单信息对象
     * @param pkgName String 包名字符串
     * @return MenuInfo 菜单信息对象
     */
    public MenuInfo getByPkgName(String pkgName) {
        return menuInfoMapper.getByPkgName(pkgName);
    }

    /**
     * 更新菜单的discard状态
     * @param menuInfo MenuInfo 菜单对象
     * @return int 受影响的结果记录数
     */
    public int updateDiscard(MenuInfo menuInfo) {
        return menuInfoMapper.updateDiscard(menuInfo);
    }

    /**
     * 获取当前有效菜单信息
     * @return 菜单列表
     */
    public List<MenuInfo> getVailMenuInfoList(){
        Map<String, Object> params = new HashMap<>();
        params.put("discard", false);
        return getList(params);
    }

    /**
     * 获取菜单集合，pkgName为 key，菜单对象为 value
     * @return Map<String, MenuInfo>
     */
    public Map<String, MenuInfo> getMenuMap() {
        Map<String, MenuInfo> menuMap = new HashMap<>();
        List<MenuInfo> menuInfoList = getList();
        for (MenuInfo menuInfo: menuInfoList) {
            menuMap.put(menuInfo.getPkgName(), menuInfo);
        }
        return menuMap;
    }
}
