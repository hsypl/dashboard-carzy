package com.crazy.dashboard.controller.user;

import com.crazy.code.annotation.Command;
import com.crazy.code.annotation.Module;
import com.crazy.code.service.ServiceProcessException;
import com.crazy.code.web.DataTables;
import com.crazy.dashboard.model.CoinMarketCap;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.rmi.server.UID;
import java.util.List;

/**
 * Created by huangshuoying on 2018/6/29.
 */
@Controller
@RequestMapping(UserController.URL_PREFIX)
@Module(value = UserController.MODULE_NAME, order = 1, icon="fa fa-user-md")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public static final String URL_PREFIX = "/dashboard/user/info";

    public static final String MODULE_NAME = "用户信息";

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserPrivilegeService userPrivilegeService;

    @Command(value = MODULE_NAME + "首页", isInlet = true, order = 1)
    @RequestMapping("/index")
    public void index(){

    }

    @Command(value = "列表" + MODULE_NAME, order = 2)
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public DataTables<List<UserInfo>> list(@RequestBody String data){
        Type type = new TypeToken<DataTables<List<UserInfo>>>() {
        }.getType();
        DataTables<List<UserInfo>> dataTables = GsonUtils.fromJson(data,type);
        List<UserInfo> userInfoList = userInfoService.
                getList(dataTables.getPagination(),dataTables.getFilter());
        dataTables.setData(userInfoList);
        dataTables.setCode(0);
        return dataTables;
    }

    @Command(value = "编辑" + MODULE_NAME, order = 3)
    @RequestMapping("/edit")
    public void edit(Model model,@RequestParam(required = false) String uid){
        model.addAttribute("userInfo",userInfoService.getSafety(uid));
    }

    @Command(value = "删除" + MODULE_NAME, order = 4)
    @RequestMapping("/delete")
    public String edit(@RequestParam(required = true) String uid) throws ServiceProcessException {
        userInfoService.delete(uid);
        return "/dashboard/user/index";
    }

    @Command(value = "保存" + MODULE_NAME, order = 5)
    @RequestMapping("/save")
    public String save(UserInfo userInfo) throws ServiceProcessException {
        if(StringUtils.isBlank(userInfo.getUid())){
            userInfo.setUid(UuidGenerator.nextUid());
            userInfoService.insert(userInfo);
        }else {
            userInfoService.update(userInfo);
        }
        return "redirect:/dashboard/user/edit?uid="+userInfo.getUid();
    }

    @Command(value = MODULE_NAME + "权限编辑",order = 6)
    @RequestMapping("/authority")
    public void index(Model model,@RequestParam String uid){
        model.addAttribute("privilegeList",GsonUtils.toJson(userPrivilegeService.getUserPrivilege(uid)));
        model.addAttribute("uid",uid);
    }

    @Command(value = MODULE_NAME + "权限保存", order = 7)
    @RequestMapping("/authority/save")
    public String save(@RequestParam String uid,@RequestParam String select) throws ServiceProcessException {
        userPrivilegeService.save(uid,select);
        return "redirect:"+URL_PREFIX+"/authority?uid="+uid;

    }

}
