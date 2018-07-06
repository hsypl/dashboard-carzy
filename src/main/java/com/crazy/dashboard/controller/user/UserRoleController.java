package com.crazy.dashboard.controller.user;

import com.crazy.code.annotation.Command;
import com.crazy.code.annotation.Module;
import com.crazy.code.service.ServiceProcessException;
import com.crazy.code.web.DataTables;
import com.crazy.dashboard.model.UserInfo;
import com.crazy.dashboard.service.UserInfoService;
import com.crazy.dashboard.service.system.UserPrivilegeService;
import com.google.gson.reflect.TypeToken;
import com.sungness.core.util.GsonUtils;
import com.sungness.core.util.UuidGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by huangshuoying on 2018/6/29.
 */
@Controller
@RequestMapping(UserRoleController.URL_PREFIX)
@Module(value = UserRoleController.MODULE_NAME, order = 2, icon="fa fa-user-md")
public class UserRoleController {
    private static final Logger log = LoggerFactory.getLogger(UserRoleController.class);

    public static final String URL_PREFIX = "/dashboard/user/role";

    public static final String MODULE_NAME = "用户权限";

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserPrivilegeService userPrivilegeService;

    @Command(value = MODULE_NAME + "首页", isInlet = true, order = 1)
    @RequestMapping("/index")
    public void index(Model model){
        String userId = "5a3cb43b00f5f42f00000001";
        model.addAttribute("privilegeList",GsonUtils.toJson(userPrivilegeService.getUserPrivilege()));
       log.debug(GsonUtils.toJson(userPrivilegeService.getUserPrivilege()));
    }

}
