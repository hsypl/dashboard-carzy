package com.crazy.dashboard.controller.token;

import com.crazy.code.annotation.Command;
import com.crazy.code.annotation.Module;
import com.crazy.code.service.ServiceProcessException;
import com.crazy.code.util.tools.LongTools;
import com.crazy.dashboard.model.MonitorSymbol;
import com.crazy.dashboard.model.enu.ExchangeEnum;
import com.crazy.dashboard.model.enu.ExchangeTypeEnum;
import com.crazy.dashboard.service.exchangeApi.MonitorSymbolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by developer2 on 2018/7/4.
 */
@Controller
@RequestMapping(MonitorController.URL_PREFIX)
@Module(value = MonitorController.MODULE_NAME, order = 1, icon="fa fa-user-md")
public class MonitorController {

    private static final Logger log = LoggerFactory.getLogger(MonitorController.class);

    public static final String URL_PREFIX = "/dashboard/token/monitor";

    public static final String MODULE_NAME = "价格监控";

    @Autowired
    private MonitorSymbolService monitorSymbolService;

    @RequestMapping("/index")
    @Command(value = MODULE_NAME + "首页", isInlet = true, order = 1)
    public void index(Model model){
        model.addAttribute("monitorList",monitorSymbolService.getList());
    }

    @RequestMapping("/edit")
    @Command(value = MODULE_NAME + "编辑", order = 1)
    public void edit(@RequestParam(required = false) Long id, Model model){
        MonitorSymbol monitorSymbol = monitorSymbolService.getSafety(id);
        model.addAttribute("exchangeList", ExchangeEnum.getEnumList());
        model.addAttribute("changeType", ExchangeTypeEnum.getEnumList());
        model.addAttribute("monitorSymbol",monitorSymbol);
    }


    @RequestMapping("/save")
    public String save(MonitorSymbol monitorSymbol) throws ServiceProcessException {
        if(LongTools.lessEqualZero(monitorSymbol.getId())){
            monitorSymbolService.insert(monitorSymbol);
        }else {
            monitorSymbolService.update(monitorSymbol);
        }
        return "redirect:"+URL_PREFIX+"/index";
    }

    @RequestMapping("/delete")
    public String delete(Long id) throws ServiceProcessException {
        monitorSymbolService.delete(id);
        return "redirect:"+URL_PREFIX+"/index";
    }

}
