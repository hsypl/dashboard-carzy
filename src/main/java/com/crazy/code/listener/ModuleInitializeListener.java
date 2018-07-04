package com.crazy.code.listener;

import com.crazy.code.annotation.Command;
import com.crazy.code.annotation.Menu;
import com.crazy.code.annotation.Module;
import com.crazy.dashboard.model.system.CommandInfo;
import com.crazy.dashboard.model.system.MenuInfo;
import com.crazy.dashboard.model.system.ModuleInfo;
import com.crazy.dashboard.model.system.ModuleTree;
import com.crazy.code.service.ServiceProcessException;
import com.crazy.dashboard.service.system.CommandInfoService;
import com.crazy.dashboard.service.system.MenuInfoService;
import com.crazy.dashboard.service.system.ModuleInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理模块初始化器
 * 系统启动时，获取所有带 @Menu、@Module、@Command 注解的bean，
 * 构造管理模块的栏目、模块、命令对象，同时注册到相应信息表，并生成树形结构，以用于模块显示、权限管理使用。
 * 相关注解：
 *      <code>@Menu</code>
 *      <code>@Module</code>
 *      <code>@Command</code>
 *      <code>@RequestMapping</code>
 * Created by wanghongwei on 9/2/15.
 */
public class ModuleInitializeListener
        implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger log =
            LoggerFactory.getLogger(ModuleInitializeListener.class);
    private static int callCounts = 0;

    private ModuleTree moduleTree;

    public void setModuleTree(ModuleTree moduleTree) {
        this.moduleTree = moduleTree;
    }

    @Autowired
    private MenuInfoService menuInfoService;

    @Autowired
    private ModuleInfoService moduleInfoService;

    @Autowired
    private CommandInfoService commandInfoService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            if (contextRefreshedEvent.getApplicationContext().getDisplayName().contains("GenericWebApplicationContext")) {
                //只在WebApplicationContext for namespace 'urp-platform-servlet'中调用,
                //避免重复执行
                return;
            }
            callCounts++;
            ApplicationContext context = contextRefreshedEvent.getApplicationContext();
            Map<String, Object> menuBeansMap = context.getBeansWithAnnotation(Menu.class);
            log.debug("Load Menu beans: " + menuBeansMap.size());
            buildMenus(menuBeansMap);

            Map<String,Object> moduleBeansMap = context.getBeansWithAnnotation(Module.class);
            log.debug("Load Module beans：" + moduleBeansMap.size());
            buildModuleAndCommand(moduleBeansMap);
            moduleTree.sort();
            log.debug("ModuleInitializeListener call times：" + callCounts);
        } catch (ServiceProcessException e) {
            log.error("Module init failed.");
            e.printStackTrace();
        }
    }

    /**
     * 将 bean Map 转换成 menu Map
     *  从每个标注 @Menu 注解的 bean 中提取 @Menu注解，构造 MenuInfo 对象，
     *  并以 bean 所在的包名为 key，存入 menu Map。
     *  如果 key 重复，则后解析的省略。
     *
     * @param menuBeansMap Map<String, Object> 加载的 bean Map
     */
    private void buildMenus(Map<String, Object> menuBeansMap) throws ServiceProcessException {
        Map<String, MenuInfo> oldMenuMap = menuInfoService.getMenuMap();
        Map<String, MenuInfo> menuMap = new HashMap<>();
        for (String key : menuBeansMap.keySet()) {
            Class<?> menuType = menuBeansMap.get(key).getClass();
            String packageName = menuType.getPackage().getName();
            oldMenuMap.remove(packageName);
            if (menuMap.containsKey(packageName)) {
                continue;
            }
            Menu menu = AnnotationUtils.findAnnotation(menuType, Menu.class);
            MenuInfo menuInfo = new MenuInfo(menu);
            menuInfo.setPkgName(packageName);
            menuInfo.setPath("");
            menuMap.put(menuInfo.getPkgName(), menuInfo);
        }

        //遍历菜单对象，获取其上级id，并保存（同时得到自身id）
        for (MenuInfo menuInfo: menuMap.values()) {
            if (menuInfo.getId() == null) {
                menuInfo.setParentId(parseParentId(menuInfo.getPkgName(), menuMap));
                menuInfo.setDiscard(false);
                menuInfoService.save(menuInfo);
            }
            this.moduleTree.addMenuToMap(menuInfo);
        }
        this.moduleTree.buildMenuTree();

        //将剩余旧菜单标记为舍弃
        for (MenuInfo menuInfo: oldMenuMap.values()) {
            menuInfo.setDiscard(true);
            menuInfoService.updateDiscard(menuInfo);
        }
    }

    /**
     * 根据菜单包名和菜单集合解析上级id，当上级菜单无id时，递归保存上级菜单并返回对应的id，
     * 直到最外层包。
     * @param pkgName String 当前菜单包名
     * @param menuMap Map<String, MenuInfo> 菜单集合
     * @return Long 上级菜单id
     * @throws ServiceProcessException
     */
    private Long parseParentId(String pkgName, Map<String, MenuInfo> menuMap) throws ServiceProcessException {
        String parentPkgName = moduleTree.getSuperPackageName(pkgName);
        if (parentPkgName == null) {
            return 0L;
        }
        MenuInfo parentMenuInfo = menuMap.get(parentPkgName);
        if (parentMenuInfo != null) {
            if (parentMenuInfo.getId() == null) {
                parentMenuInfo.setParentId(parseParentId(parentMenuInfo.getPkgName(), menuMap));
                menuInfoService.save(parentMenuInfo);
            }
            return parentMenuInfo.getId();
        } else {
            return parseParentId(parentPkgName, menuMap);
        }
    }

    /**
     * 构建模块列表和命令Map
     * @param moduleBeansMap Map<String,Object> 模块 bean Map
     */
    private void buildModuleAndCommand(Map<String, Object> moduleBeansMap)
            throws ServiceProcessException {
        Map<String, ModuleInfo> oldModuleMap = moduleInfoService.getModuleMap();
        Map<String, CommandInfo> oldCommandMap = commandInfoService.getCommandMap();
        for (String key : moduleBeansMap.keySet()) {
            Class<?> moduleType = moduleBeansMap.get(key).getClass();
            String packageName = moduleType.getPackage().getName();
            Module module = AnnotationUtils.findAnnotation(moduleType, Module.class);
            RequestMapping requestMapping =
                    AnnotationUtils.findAnnotation(moduleType, RequestMapping.class);
            ModuleInfo moduleInfo = new ModuleInfo(module);
            moduleInfo.setPkgName(getRealClassName(moduleType.getName()));
            moduleInfo.setPath("");
            moduleInfo.setInletUri("");
            if (requestMapping != null) {
                String[] values = requestMapping.value();
                if (values.length > 0) {
                    moduleInfo.setPath(values[0]);
                }
            }
            MenuInfo superMenu = this.moduleTree.getBelongsToMenu(packageName);
            if (superMenu == null) {
                // 无上级菜单，属于根模块
                moduleInfo.setParentId(0L);
                this.moduleTree.addModuleToRootList(moduleInfo);
            } else {
                moduleInfo.setParentId(superMenu.getId());
                moduleInfo.setMenu(superMenu);
                superMenu.addModule(moduleInfo);
            }
            moduleInfo.setDiscard(false);
            moduleInfoService.save(moduleInfo);
            buildCommand(moduleType, moduleInfo, oldCommandMap);
            this.moduleTree.addModuleToMap(moduleInfo);
            if (StringUtils.isNotBlank(moduleInfo.getInletUri())) {
                //更新模块入口地址
                moduleInfoService.update(moduleInfo);
            }
            oldModuleMap.remove(moduleInfo.getPkgName());
        }
        //将剩余旧模块和命令标记为舍弃
        for (ModuleInfo moduleInfo: oldModuleMap.values()) {
            moduleInfo.setDiscard(true);
            moduleInfoService.updateDiscard(moduleInfo);
        }
        for (CommandInfo commandInfo: oldCommandMap.values()) {
            commandInfo.setDiscard(true);
            commandInfoService.updateDiscard(commandInfo);
        }
    }

    /**
     * 构建命令 Map
     * @param moduleType Class<?> 模块类类型
     * @param moduleInfo ModuleInfo 模块对象
     * @param oldCommandMap Map<String, CommandInfo> 数据库中已有的命令集合
     */
    private void buildCommand(Class<?> moduleType, ModuleInfo moduleInfo,
                              Map<String, CommandInfo> oldCommandMap)
            throws ServiceProcessException {
        Method[] methods = moduleType.getMethods();
        for (Method method: methods) {
            Command command = AnnotationUtils.findAnnotation(method, Command.class);
            RequestMapping requestMapping =
                    AnnotationUtils.findAnnotation(method, RequestMapping.class);
            if (command == null || requestMapping == null) {
                continue;
            }
            CommandInfo commandInfo = new CommandInfo(command);
            commandInfo.setPkgName(getRealClassName(moduleType.getName()) + "." + method.getName());
            commandInfo.setPath(mergePath(moduleInfo.getPath(), requestMapping.value()));
            commandInfo.setModule(moduleInfo);
            commandInfo.setParentId(moduleInfo.getId());
            commandInfo.setDiscard(false);
            moduleInfo.addCommand(commandInfo);
            if (commandInfo.isInlet()) {
                moduleInfo.setInletUri(commandInfo.getPath());
            }
            commandInfoService.save(commandInfo);
            this.moduleTree.addCommandToMap(commandInfo);
            oldCommandMap.remove(commandInfo.getPath());
        }
    }

    /**
     * 获取指定包名的上级包名
     * @param packageName String 包名
     * @return String 上级包名，如果无上级，返回null。
     */
    /*private String getSuperPackageName(String packageName) {
        if (StringUtils.isBlank(packageName)) {
            return null;
        }
        int index = packageName.lastIndexOf(".");
        if (index == -1) {
            return null;
        }
        return packageName.substring(0, index);
    }*/

    /**
     * 将模块路径与命令路径合并
     * @param modulePath String 模块请求路径
     * @param commandPaths String[] 命令请求路径数组
     * @return String 组合后的路径
     */
    private String mergePath(String modulePath, String[] commandPaths) {
        StringBuilder pathBuilder = new StringBuilder("");
        if (StringUtils.isNotBlank(modulePath)) {
            if (!modulePath.startsWith("/")) {
                pathBuilder.append("/");
            }
            pathBuilder.append(modulePath);
            if (!modulePath.endsWith("/")) {
                pathBuilder.append("/");
            }
        }

        if (commandPaths != null && commandPaths.length > 0) {
            String commandPath = commandPaths[0];
            if (StringUtils.isNotBlank(commandPath)) {
                if (commandPath.startsWith("/")) {
                    pathBuilder.append(commandPath.substring(1));
                } else {
                    pathBuilder.append(commandPath);
                }
            }
        }
        return pathBuilder.toString();
    }

    /**
     * 根据类的包名从 menu Map 中获取上级菜单，如果不存在，则递归获取其上级包对应的菜单，
     * 直到获取到或至包名遍历完
     * //@param packageName String 包名
     * @return MenuInfo 包所属直接上级菜单对象
     */
    /*private MenuInfo getBelongsToMenu(String packageName) {
        String superPackageName = packageName;
        while (true) {
            MenuInfo menuInfo = this.moduleTree.getMenu(superPackageName);
            if (menuInfo != null) {
                return menuInfo;
            }
            superPackageName = this.moduleTree.getSuperPackageName(superPackageName);
            if (superPackageName == null) {
                break;
            }
        }
        return null;
    }*/

    private String getRealClassName(String typeName) {
        int index = typeName.indexOf("$");
        return index < 0 ? typeName : typeName.substring(0, index);
    }

    public static void main(String[] args) {
    }
}
