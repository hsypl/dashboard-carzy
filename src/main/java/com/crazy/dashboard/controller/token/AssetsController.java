package com.crazy.dashboard.controller.token;

import com.crazy.code.annotation.Command;
import com.crazy.code.annotation.Module;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by developer2 on 2018/7/4.
 */
@Controller
@RequestMapping(AssetsController.URL_PREFIX)
@Module(value = AssetsController.MODULE_NAME, order = 1, icon="fa fa-user-md")
public class AssetsController {

    public static final String URL_PREFIX = "/dashboard/token/assets";

    public static final String MODULE_NAME = "资产信息";

    @RequestMapping("/index")
    @Command(value = MODULE_NAME + "首页", isInlet = true, order = 1)
    public void index(){

    }

    @RequestMapping("/list")
    @Command(value = MODULE_NAME + "列表",  order = 2)
    public void list(Model model){
    }
}
