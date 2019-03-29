package com.xwdz.time.entity;

/**
 * @author xingwei.huang (xwdz9989@gamil.com)
 * @since 2019/3/26
 */
public class Response<T> {

    public final String code;
    public final String message;
    public final T      data;

    public Response(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
