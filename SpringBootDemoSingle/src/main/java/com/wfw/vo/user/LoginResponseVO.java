package com.wfw.vo.user;

import com.wfw.vo.BaseResponseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by killer9527 on 2018/3/16.
 */
@ApiModel(value = "LoginResponseVO", description = "登录响应")
public class LoginResponseVO extends BaseResponseVO {
    @ApiModelProperty(value = "token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
