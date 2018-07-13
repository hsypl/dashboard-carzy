package com.crazy.dashboard.controller;

import com.crazy.code.web.CommandResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by developer2 on 2018/7/4.
 */
@Controller
@RequestMapping("/dashboard")
public class AuthController {

    @RequestMapping("/login")
    public void login(){

    }

    @RequestMapping("/index")
    public void index(){

    }

    @RequestMapping("/loginFailed")
    public String loginFailed(Model model){
        CommandResult<Integer> commandResult = new CommandResult<>("");
        commandResult.setMessage("账号或密码有误");
        model.addAttribute("commandResult", commandResult);
        return "/dashboard/login";
    }

    /**
     * 退出成功控制器
     * @param model Model
     * @return String 视图名
     */
    @RequestMapping("/noPermission")
    public String noPermission(Model model) {
        CommandResult<Integer> commandResult = new CommandResult<>("");
        commandResult.setMessage("没有权限");
        model.addAttribute("commandResult", commandResult);
        return "/dashboard/index";
    }

    /**
     * 登录超时控制器
     * @param model Model
     * @return String 视图名
     */
    @RequestMapping("/sessionOut")
    public String sessionOut(Model model, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        CommandResult<Integer> commandResult = new CommandResult<>("");
        commandResult.setMessage("登陆超时");
        model.addAttribute("commandResult", commandResult);
        return "/dashboard/login";
    }

    /**
     * 退出成功控制器
     * @param model Model
     * @return String 视图名
     */
    @RequestMapping("/logoutSuccess")
    public String logoutSuccess(Model model) {
        CommandResult<Integer> commandResult = new CommandResult<>("");
        commandResult.setMessage("退出登陆");
        model.addAttribute("commandResult", commandResult);
        return "/dashboard/login";
    }

    /**
     * 404跳转到首页
     * @return String 视图名
     */
    @RequestMapping("/notFound")
    public String notFound() {
        return "redirect:/dashboard/404";
    }

    /**
     * 404跳转到首页
     */
    @RequestMapping("/404")
    public String notFound404() {
        return "/dashboard/404";
    }
}
