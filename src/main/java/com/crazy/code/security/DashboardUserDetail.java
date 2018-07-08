package com.crazy.code.security;

import com.crazy.dashboard.model.UserInfo;
import com.crazy.dashboard.model.system.CommandInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * Created by developer2 on 2017/9/13.
 */
public class DashboardUserDetail implements UserDetails {

    /** 模块权限key集合 */
    private Set<String> privilegeKeySet;

    private UserInfo userInfo;

    public DashboardUserDetail(UserInfo userInfo){
        this.userInfo = userInfo;
        privilegeKeySet = new HashSet<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
            auths.add(new SimpleGrantedAuthority("ROLE_PLATFORMADMIN"));
            auths.add(new SimpleGrantedAuthority("abc"));
        return auths;
    }

    public UserInfo getUserInfo(){
        return userInfo;
    }

    @Override
    public String getPassword() {
        return userInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return userInfo.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * 判断是否是超级管理员
     * @return boolean 是返回true, 否返回false
     */
    public boolean isAdmin() {
        return userInfo.getAdmin() == 1;
    }

    /**
     * 判断用户是否有命令的使用权限,如果为超级管理员或模块信息为空可使用,
     * 否则需要根据用户的权限集合判断是否可用。
     * @param commandInfo CommandInfo 命令信息对象
     * @return 如果允许使用返回 true, 否则返回 false
     */
    public boolean hasPermission(CommandInfo commandInfo) {
        return isAdmin() || (commandInfo == null || privilegeKeySet.contains(commandInfo.getCommandKey()));
    }

//    /**
//     * 判断用户是否有模块的使用权限,如果为超级管理员可使用,
//     * 否则需要根据用户的权限集合判断是否可用。
//     * @param moduleInfo ModuleInfo 模块信息对象
//     * @return 如果允许使用返回 true, 否则返回 false
//     */
//    public boolean hasPermission(ModuleInfo moduleInfo) {
//        return moduleInfo != null && privilegeKeySet.contains(moduleInfo.getModuleKey());
//    }
//
//    /**
//     * 判断用户是否有菜单的使用权限,如果为超级管理员可使用,
//     * 否则需要根据用户的权限集合判断是否可用。
//     * @param menuInfo MenuInfo 菜单信息对象
//     * @return 如果允许使用返回 true, 否则返回 false
//     */
//    public boolean hasPermission(MenuInfo menuInfo) {
//        return menuInfo != null && privilegeKeySet.contains(menuInfo.getMenuKey());
//    }

    /**
     * 添加用户的权限集合, 用户成功登录时会触发调用该方法
     * @param keySet Set<String>
     */
    public void addPrivilegeKeys(Set<String> keySet) {
        privilegeKeySet.addAll(keySet);
    }

}
