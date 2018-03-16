package com.wfw.vo.greeting;

import com.wfw.vo.BaseResponseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by killer9527 on 2018/3/5.
 */
@ApiModel(value = "GreetingResponseVO", description = "Greeting响应")
public class GreetingResponseVO extends BaseResponseVO {
    @ApiModelProperty(value = "id")
    private long id;
    @ApiModelProperty(value = "内容")
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
