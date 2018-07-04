package com.crazy.dashboard.controller.token;


import com.crazy.code.annotation.Menu;

/**
 * 管理菜单类，该类仅用于构造管理系统菜单导航结构，由系统自动扫描 @Menu 注解，
 * 获取相应的 “包路径”
 */
@Menu(value = "coin管理", order = 2,icon = "fa fa-btc")
public class TokenMenu {
}