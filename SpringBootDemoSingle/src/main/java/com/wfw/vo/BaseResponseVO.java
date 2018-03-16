package com.wfw.vo;

/**
 * Created by killer9527 on 2018/3/5.
 */
public class BaseResponseVO {
    private String message;
    private Boolean result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}
