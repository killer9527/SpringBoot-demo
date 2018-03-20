package com.wfw.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by killer9527 on 2018/3/20.
 */
@ApiModel(value = "BasePageResponseVO", description = "分页响应")
public class BasePageResponseVO<T> extends BaseResponseVO{
    @ApiModelProperty(value = "总数")
    private Integer total;

    @ApiModelProperty(value = "本页数据")
    private List<T> data;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
