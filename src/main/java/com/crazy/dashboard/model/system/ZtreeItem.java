package com.crazy.dashboard.model.system;

import java.io.Serializable;

/**
 * 返回Ztree请求的数据模型。
 * 节点id 和父节点id的规则
 * Created by wanghongwei on 3/30/16.
 */
public class ZtreeItem implements Serializable {
    /** 节点id */
    private String id;
    /** 父节点id */
    private String pId;
    /** 节点名称 */
    private String name;
    /** 是否选中状态 */
    private boolean checked;
    /** 是否展开 */
    private boolean open;
    /** 是否是父节点 */
    private boolean isParent;
    /** 子节点数 */
    private Integer childCount;
    /** 条目类型,对应枚举 ItemTypeEnum */
    private Integer itemType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public Integer getChildCount() {
        return childCount;
    }

    public void setChildCount(Integer childCount) {
        this.childCount = childCount;
    }

    public void addChildCount(Integer newCount) {
        this.childCount += newCount;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }
}