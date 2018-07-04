package com.crazy.dashboard.controller;

import com.crazy.code.web.CommandResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by developer2 on 2018/7/4.
 */
@Controller
@RequestMapping("/dashboard")
public class AuthController {

    @RequestMapping("/login")
    public void login(){

    }

    @RequestMapping("/loginFailed")
    public String loginFailed(Model model){
        CommandResult<Integer> commandResult = new CommandResult<>("");
        commandResult.setMessage("账号或密码有误");
        model.addAttribute("commandResult", commandResult);
        return "/dashboard/login";
    }
}
