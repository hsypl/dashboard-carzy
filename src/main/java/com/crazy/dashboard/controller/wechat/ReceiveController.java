package com.crazy.dashboard.controller.wechat;

import com.crazy.dashboard.service.wechat.WechatService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by developer2 on 2018/2/5.
 */
@Controller
@RequestMapping(ReceiveController.URL_PREFIX)
public class ReceiveController {

    public final static String URL_PREFIX = "/dashboard/wechat";

    private static final Logger log = LoggerFactory.getLogger(ReceiveController.class);

    @Autowired
    private WechatService wechatService;

    @ResponseBody
    @RequestMapping(value = "/receive",method = RequestMethod.POST)
    public String index(@RequestParam(required = false) String echostr,
                        HttpServletRequest request) throws Exception {
        if (StringUtils.isNotBlank(echostr)){
            log.debug("============"+echostr);
            return echostr;
        }else {
            String result = wechatService.reply(request);
            log.debug(result);
            return result;
        }
    }

}
