package com.chunlangjiu.app.goods.bean;

/**
 * @CreatedbBy: liucun on 2018/7/30
 * @Describe:
 */
public class ClassBean {

    private String classId;
    private String className;

    public ClassBean(String classId, String className) {
        this.classId = classId;
        this.className = className;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
