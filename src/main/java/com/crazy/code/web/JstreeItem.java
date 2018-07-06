package com.crazy.code.web;

import com.google.gson.JsonObject;
import com.sungness.core.util.GsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by developer2 on 2018/7/5.
 */
public class JstreeItem {

    private String text;

    private Map<String,Boolean> state;

    private String id ;

    private String icon = "jstree-file";

    private List<JstreeItem> children = new ArrayList<>();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, Boolean> getState() {
        return state;
    }

    public void setState(Map<String, Boolean> state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<JstreeItem> getChildren() {
        return children;
    }

    public void setChildren(JstreeItem jstreeItem) {
        this.children.add(jstreeItem);
    }


    public static void main(String[] args){
//        List<JstreeItem> jstreeMoudleList = new ArrayList<>();
//        List<JstreeItem> jstreeCommendList1 = new ArrayList<>();
//
//        JstreeItem jstreeItem = new JstreeItem();
//        jstreeItem.setText("用户管理");
//        jstreeItem.setId("1-2");
//        Map<String,Boolean> state = new HashMap<>();
//        state.put("opened",true);
//        jstreeItem.setState(state);
//        jstreeItem.setChildren(jstreeMoudleList);
//
//        JstreeItem jstreeItem1 = new JstreeItem();
//        jstreeItem1.setText("用户信息");
//        jstreeItem1.setId("2-1");
//        jstreeItem1.setState(state);
//        jstreeItem1.setChildren(jstreeCommendList1);
//        jstreeMoudleList.add(jstreeItem1);
//
//        JstreeItem jstreeItem2 = new JstreeItem();
//        jstreeItem2.setText("用户列表");
//        jstreeItem2.setId("3-1");
//        jstreeCommendList1.add(jstreeItem2);
//
//        JstreeItem jstreeItem3 = new JstreeItem();
//        jstreeItem3.setText("用户保存");
//        jstreeItem3.setId("3-2");
//        jstreeCommendList1.add(jstreeItem3);
//        System.out.print(GsonUtils.toJson(jstreeItem));

    }
}
