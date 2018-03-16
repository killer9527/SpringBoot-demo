package com.wfw.vo.hello;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by killer9527 on 2018/3/13.
 */
@ApiModel(value = "GreetingRequestVO", description = "Greeting响应模型")
public class GreetingRequestVO {
    @ApiModelProperty(value = "姓名")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
