package com.crazy.dashboard.model.system;


import com.crazy.code.annotation.Command;

/**
 * 管理系统命令信息Bean，从 @Command 注解解析出代码、名称、路径等信息，构造 CommandInfo 对象。
 * Created by wanghongwei on 9/7/15.
 */
public class CommandInfo extends BaseInfo {
    private static final long serialVersionUID = 7291638968139425511L;
    /** 所属模块 */
    private ModuleInfo module;
    /** 是否是模块入口 */
    private boolean inlet;

    /** 是否在顶部导航的模块下级菜单显示 */
    private boolean showInMenu;

    /** 命令别名，当showInMenu为 true 时，在导航中显示此别名（一般设置为新增xxx）*/
    private String alias;

    public CommandInfo() {
    }

    public CommandInfo(Command command) {
        code = command.code();
        value = command.value();
        orderNumber = command.order();
        enable = command.enable();
        inlet = command.isInlet();
        showInMenu = command.showInMenu();
        alias = command.alias();
        icon = command.icon();
        discard = false;
    }

    public ModuleInfo getModule() {
        return module;
    }

    public void setModule(ModuleInfo module) {
        this.module = module;
    }

    public boolean isInlet() {
        return inlet;
    }

    public void setInlet(boolean inlet) {
        this.inlet = inlet;
    }

    public boolean isShowInMenu() {
        return showInMenu;
    }

    public void setShowInMenu(boolean showInMenu) {
        this.showInMenu = showInMenu;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }


    public String getCommandKey() {
        return String.format("%s-%s", ItemTypeEnum.COMMAND.getValue(), id);
    }

    public String getParentKey() {
        return String.format("%s-%s", ItemTypeEnum.MODULE.getValue(), parentId);
    }
}
