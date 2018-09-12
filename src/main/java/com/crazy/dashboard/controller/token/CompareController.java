package com.crazy.dashboard.controller.token;

import com.crazy.code.annotation.Command;
import com.crazy.code.annotation.Module;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by developer2 on 2018/7/4.
 * 交易对历史数据
 */
@Controller
@RequestMapping(CompareController.URL_PREFIX)
@Module(value = CompareController.MODULE_NAME, order = 2, icon="fa fa-user-md")
public class CompareController {

    public static final String URL_PREFIX = "/dashboard/token/compare";

    public static final String MODULE_NAME = "price compare";

    @RequestMapping("/index")
    @Command(value = MODULE_NAME + "首页", isInlet = true, order = 1)
    public void index(){

    }

}
