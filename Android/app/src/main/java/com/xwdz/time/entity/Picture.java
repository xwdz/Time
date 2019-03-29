package com.xwdz.time.entity;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * @author xingwei.huang (xwdz9989@gamil.com)
 * @since 2019/3/26
 */
public class Picture implements Serializable {

    private static final long serialVersionUID = -6822986779555238378L;

    private String id;
    private String path;
    private String desc;
    private String name;
    private String uploadTime;
    private String address;


    public Picture() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = encode(desc);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = encode(name);
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = encode(address);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id='" + id + '\'' +
                ", path='" + path + '\'' +
                ", desc='" + desc + '\'' +
                ", name='" + name + '\'' +
                ", uploadTime='" + uploadTime + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public static String encode(String text) {
        return new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }
}
