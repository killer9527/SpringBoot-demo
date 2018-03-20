package com.wfw.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by killer9527 on 2018/3/20.
 */
@ApiModel(value = "BasePageRequestVO", description = "分页请求")
public class BasePageRequestVO {
    @ApiModelProperty(value = "页码：从1开始")
    private Integer pageIndex;
    @ApiModelProperty(value = "每页数据量")
    private Integer pageSize;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
