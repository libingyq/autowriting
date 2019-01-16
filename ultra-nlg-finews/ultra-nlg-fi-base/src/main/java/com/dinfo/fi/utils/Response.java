package com.dinfo.fi.utils;


import java.io.Serializable;

/**
 * 服务层响应封装对象
 */
public final class Response<T> implements Serializable {

    private static final long serialVersionUID = 3727205004706510648L;

    public static final Integer OK = 200;

    /**
     * 默认错误码
     */
    public static final Integer ERR = 500;

    /**
     * 响应码
     */
    private Integer status;

    /**
     * 错误信息
     */
    private String err;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 执行上下文
     */
    private String context;

    private Response() {
    }

    public static <T> Response<T> ok(){
        Response r = new Response();
        r.status = OK;
        return r;
    }

    public static <T> Response<T> ok(Object data){
        Response r = new Response();
        r.status = OK;
        r.data = data;
        return r;
    }

    public static <T> Response<T> notOk(String err){
        Response r = new Response();
        r.status = ERR;
        r.err =err;
        return r;
    }

    public Boolean isSuccess(){
        return status.equals(OK);
    }

    public Boolean isNotSuccess(){
        return !isSuccess();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        if(null!=data){
            status = OK;
        }
        this.data = data;
    }

    public String getContext() {
        return context;
    }



    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", err='" + err + '\'' +
                ", data=" + data +
                ", context='" + context + '\'' +
                '}';
    }

}