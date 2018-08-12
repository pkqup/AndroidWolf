package com.chunlangjiu.app.user.bean;

import java.io.Serializable;

/**
 * @CreatedbBy: liucun on 2018/7/7.
 * @Describe:
 */
public class AddressListDetailBean implements Serializable{

    private String addr_id;
    private String user_id;
    private String name;
    private String area;
    private String addr;
    private String mobile;
    private String def_addr;
    private String region_id;
    private String addrdetail;

    public String getAddr_id() {
        return addr_id;
    }

    public void setAddr_id(String addr_id) {
        this.addr_id = addr_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDef_addr() {
        return def_addr;
    }

    public void setDef_addr(String def_addr) {
        this.def_addr = def_addr;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getAddrdetail() {
        return addrdetail;
    }

    public void setAddrdetail(String addrdetail) {
        this.addrdetail = addrdetail;
    }
}
