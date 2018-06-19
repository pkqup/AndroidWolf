package com.android.alcoholwolf.amain.bean;


import io.realm.RealmObject;

/**
 * @CreatedbBy: liucun on 2018/6/17.
 * @Describe:
 */
public class RealmBean extends RealmObject   {



    private String name;
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
