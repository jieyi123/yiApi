package com.pjieyi.apiinterface.controller;

import com.pjieyi.yiapiclientsdk.model.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @author pjieyi
 * @description 获取用户名
 */
@RestController
@RequestMapping("/name")
public class NameController {


    @GetMapping()
    public String getNameByGet( String name){
        return "GET 我的用户名为："+name;
    }

    @PostMapping()
    public String getUserNameByPost(@RequestParam String name){
        return "POST 我的用户名为："+name;
    }

    @PostMapping(value = "/user")
    public String getNameByPostRestful(@RequestBody User user) {
        System.out.println("POST Restful我的用户名为："+user.getName());
        return "POST Restful我的用户名为："+user.getName();
    }
}
