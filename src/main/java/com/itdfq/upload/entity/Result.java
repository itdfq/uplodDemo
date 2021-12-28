package com.itdfq.upload.entity;

import java.io.Serializable;

/**
 * @Author GocChin
 * @Date 2021/12/28 20:23
 * @Blog: itdfq.com
 * @QQ: 909256107
 * @Description:
 */
public class Result<T extends Object> implements Serializable {

    private static final long serialVersionUID = -9147616776579758668L;
    private static final int SUCCESS_CODE = 10000;
    private static final int FAIL_CODE = 410;

    private int code;
    private String msg;
    private T data;

    public Result() {
        // 无参构造 Result
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> newSuccess() {
        return new Result<T>(SUCCESS_CODE, "Success", null);
    }

    public static <T> Result<T> newSuccess(T data) {
        return new Result<T>(SUCCESS_CODE, "Success", data);
    }

    public static <T> Result<T> newFailure(String errorMsg) {
        return new Result<T>(FAIL_CODE, errorMsg, null);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return code == SUCCESS_CODE;
    }

    @Override
    public String toString() {
        return "Result [code=" + code + ", msg=" + msg + ", data=" + data + "]";
    }

}

