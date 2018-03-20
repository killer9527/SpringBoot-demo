package com.wfw.controller;

import com.wfw.service.UserService;
import com.wfw.vo.user.ListUsersRequestVO;
import com.wfw.vo.user.ListUsersResponseVO;
import com.wfw.vo.user.LoginRequestVO;
import com.wfw.vo.user.LoginResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录")
    LoginResponseVO login(@RequestBody LoginRequestVO request){
        LoginResponseVO response = new LoginResponseVO();
        response.setToken(request.getUserName() + request.getPassword());
        response.setMessage("OK");
        response.setResult(true);
        return response;
    }

    @RequestMapping(path = "/listUsers", method = RequestMethod.POST)
    @ApiOperation(value = "获取用户信息列表")
    ListUsersResponseVO listUsers(@RequestBody ListUsersRequestVO request){
        ListUsersResponseVO response = new ListUsersResponseVO();
        response.setData(this.userService.listUsers(request.getPageIndex(), request.getPageSize()));
        response.setTotal(this.userService.countUsers());
        response.setResult(true);
        response.setMessage("OK");
        return response;
    }
}
