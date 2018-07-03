package com.crazy.dashboard.model;

import java.util.List;

/**
 * Created by developer2 on 2017/9/13.
 */
public class UserInfo {

    private String uid ;

    private String username;

    private String password;

    private String name;

    private Integer admin;

    private List<Long> moduleIdList;

    public List<Long> getModuleIdList() {
        return moduleIdList;
    }

    public void setModuleIdList(List<Long> moduleIdList) {
        this.moduleIdList = moduleIdList;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }
}
