package com.xwdz.site.entity;

import com.xwdz.site.utils.CommUtils;

import java.io.Serializable;

public class Picture implements Serializable {

    private static final long serialVersionUID = -6822986779555238378L;

    private String id;
    private String path;
    private String desc;
    private String name;
    private String uploadTime;
    private String address;
    private String ukey;


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
        this.desc = CommUtils.encode(desc);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = CommUtils.encode(name);
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
        this.address = CommUtils.encode(address);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUkey() {
        return ukey;
    }

    public void setUkey(String ukey) {
        this.ukey = ukey;
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
}
