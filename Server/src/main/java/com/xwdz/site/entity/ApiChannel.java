package com.xwdz.site.entity;

/**
 * @author xingwei.huang (xwdz9989@gamil.com)
 * @since 2019-03-25
 */
public class ApiChannel {
    private String key;
    private String email;
    private String contact;
    private String contact_type;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactType() {
        return contact_type;
    }

    public void setContactType(String contact_type) {
        this.contact_type = contact_type;
    }

    @Override
    public String toString() {
        return "ApiChannel{" +
                "key='" + key + '\'' +
                '}';
    }
}
