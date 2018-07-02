package com.chunlangjiu.app.net;

public class User {

    private String userName;
    private String age;

    public User(String userName,String age){
        this.age = age;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
