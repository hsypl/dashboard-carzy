package com.crazy.dashboard.controller.user;

import com.crazy.code.service.ServiceProcessException;
import com.crazy.code.web.DataTables;
import com.crazy.dashboard.model.CoinMarketCap;
import com.crazy.dashboard.model.UserInfo;
import com.crazy.dashboard.service.UserInfoService;
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
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public static final String URL_PREFIX = "/dashboard/user";

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/index")
    public void index(){

    }

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

    @RequestMapping("/edit")
    public void edit(Model model,@RequestParam(required = false) String uid){
        model.addAttribute("userInfo",userInfoService.getSafety(uid));
    }

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

}
