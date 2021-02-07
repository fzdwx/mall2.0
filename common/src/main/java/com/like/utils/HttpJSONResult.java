package com.like.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: 自定义响应数据结构
 * ######
 * #            200：成功
 * #            500：错误
 * #            501：bean验证错误,以map形式返回
 * #            502：拦截器验证用户token错误
 * #            555：异常抛出信息
 * #            556：用户qq校验异常
 * #####
 * @since 2021-02-07 12:31
 */
public class HttpJSONResult<V> {

    /**
     * 自定义jackson对象
     */
    public static final ObjectMapper MAPPER = new ObjectMapper();
    /**
     * 响应状态状态
     */
    private Integer status;

    /**
     * 响应消息
     */
    private String msg;
    /**
     * 响应数据
     */
    private V data;
    @JsonIgnore
    private String ok; // 不适用


    public static HttpJSONResult build(Integer status, String msg, Object data) {
        return new HttpJSONResult(status, msg, data);
    }

    public static HttpJSONResult build(Integer status, String msg, Object data, String ok) {
        return new HttpJSONResult(status, msg, data, ok);
    }

    public static HttpJSONResult ok(Object data) {
        return new HttpJSONResult(data);
    }

    public static HttpJSONResult ok() {
        return new HttpJSONResult(null);
    }

    public static HttpJSONResult errorMsg(String msg) {
        return new HttpJSONResult(500, msg, null);
    }

    public static HttpJSONResult errorMap(Object data) {
        return new HttpJSONResult(501, "error", data);
    }

    public static HttpJSONResult errorTokenMsg(String msg) {
        return new HttpJSONResult(502, msg, null);
    }

    public static HttpJSONResult errorException(String msg) {
        return new HttpJSONResult(555, msg, null);
    }

    public static HttpJSONResult errorUserQQ(String msg) {
        return new HttpJSONResult(556, msg, null);
    }

    public HttpJSONResult() {

    }

    public HttpJSONResult(Integer status, String msg, V data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public HttpJSONResult(Integer status, String msg, V data, String ok) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.ok = ok;
    }

    public HttpJSONResult(V data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.status == 200;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(V data) {
        this.data = data;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }
}