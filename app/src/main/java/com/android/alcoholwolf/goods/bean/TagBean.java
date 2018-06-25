package com.android.alcoholwolf.goods.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @CreatedbBy: liucun on 2018/6/24.
 * @Describe:
 */
public class TagBean extends RealmObject {

    private int index;

    private String name;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
