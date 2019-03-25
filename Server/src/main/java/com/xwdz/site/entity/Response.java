package com.xwdz.site.entity;

public class Response<T> {

    public String code;
    public String message;
    public T data;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public static <T> Response<T> ok(T t) {
        Response<T> response = new Response<>();
        response.setMessage("success");
        response.setCode("200");
        response.setData(t);
        return response;
    }

    public static <T> Response<T> fail(String errorCode, String message) {
        Response<T> response = new Response<>();
        response.setMessage(message);
        response.setCode(errorCode);
        response.setData(null);
        return response;
    }

}
