package com.wfw.controller;

import com.wfw.vo.user.LoginRequestVO;
import com.wfw.vo.user.LoginResponseVO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by killer9527 on 2018/3/16.
 */
@RestController
@RequestMapping(path = "/user")
@Api(value = "/user", description = "用户相关接口")
public class UserController extends BaseController {

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    LoginResponseVO login(@RequestBody LoginRequestVO request){
        LoginResponseVO response = new LoginResponseVO();
        response.setToken(request.getUserName() + request.getPassword());
        response.setMessage("OK");
        response.setResult(true);
        return response;
    }
}
