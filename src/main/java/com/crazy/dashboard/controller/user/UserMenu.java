package com.crazy.dashboard.controller.user;


import com.crazy.code.annotation.Menu;

/**
 * 管理菜单类，该类仅用于构造管理系统菜单导航结构，由系统自动扫描 @Menu 注解，
 * 获取相应的 “包路径”
 */
@Menu(value = "用户管理", order = 1, icon = "fa fa-user-md")
public class UserMenu {
}