package com.chunlangjiu.app.user.bean;

/**
 * @CreatedbBy: liucun on 2018/9/17
 * @Describe:
 */
public class UserInfoBean {


    private String login_account;
    private String username;
    private String name;
    private String head_portrait;

    public String getHead_portrait() {
        return head_portrait;
    }

    public void setHead_portrait(String head_portrait) {
        this.head_portrait = head_portrait;
    }

    public String getLogin_account() {
        return login_account;
    }

    public void setLogin_account(String login_account) {
        this.login_account = login_account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
