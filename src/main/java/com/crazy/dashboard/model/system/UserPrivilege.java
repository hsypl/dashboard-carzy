package com.crazy.dashboard.model.system;

import java.io.Serializable;

/**
* 合作方用户权限信息 Bean
*
* Created by Chwing on 8/31/17.
*/
public class UserPrivilege implements Serializable {

    /** 记录id */
    private Long id;
    /** 系统用户id */
    private String userId;
    /** 条目id */
    private Long itemId;
    /** 条目类型，0-菜单，1-模块，2-命令 */
    private Integer itemType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public String getPrivilegeKey() {
        return String.format("%s-%s", itemType, itemId);
    }
}