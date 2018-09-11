package com.crazy.dashboard.controller.twitter;

import com.crazy.code.annotation.Command;
import com.crazy.code.annotation.Module;
import com.crazy.dashboard.controller.token.MonitorController;
import com.crazy.dashboard.service.TwitterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import twitter4j.TwitterException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by developer2 on 2018/7/4.
 */
@Controller
@RequestMapping(MessageController.URL_PREFIX)
@Module(value = MessageController.MODULE_NAME, order = 2, icon="fa fa-user-md")
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    public static final String URL_PREFIX = "/dashboard/twitter/message";

    public static final String MODULE_NAME = "推文";

    @Autowired
    private TwitterService twitterService;

    @RequestMapping
    public void callback(HttpServletRequest request, HttpServletResponse response) throws IOException, TwitterException {
//        twitterService.login(request);
        response.sendRedirect(request.getContextPath()+ "/");
    }

    @RequestMapping("/index")
    @Command(value = MODULE_NAME + "首页", isInlet = true, order = 1)
    public void index(){

    }

    @RequestMapping("/login")
    @Command(value = MODULE_NAME + "列表",  order = 2)
    public void login(){
    }
}
