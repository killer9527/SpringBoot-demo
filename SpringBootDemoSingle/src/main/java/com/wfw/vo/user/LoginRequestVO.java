package com.wfw.vo.user;

import com.wfw.vo.BaseRequestVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by killer9527 on 2018/3/16.
 */
@ApiModel(value = "LoginRequestVO", description = "登录请求")
public class LoginRequestVO extends BaseRequestVO {
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "密码")
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
