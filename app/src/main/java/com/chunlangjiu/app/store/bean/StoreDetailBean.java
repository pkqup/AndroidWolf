package com.chunlangjiu.app.store.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/8/29
 * @Describe:
 */
public class StoreDetailBean implements Serializable {

    private List<StoreBean> detail;

    public List<StoreBean> getDetail() {
        return detail;
    }

    public void setDetail(List<StoreBean> detail) {
        this.detail = detail;
    }

    public class StoreBean implements Serializable {
        private String chateau_id;
        private String name;
        private String img;
        private String chateaucat_id;
        private String modified;
        private String ifpub;
        private String content;
        private String intro;
        private String phone;
        private List<Grade> grade;

        public String getChateau_id() {
            return chateau_id;
        }

        public void setChateau_id(String chateau_id) {
            this.chateau_id = chateau_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getChateaucat_id() {
            return chateaucat_id;
        }

        public void setChateaucat_id(String chateaucat_id) {
            this.chateaucat_id = chateaucat_id;
        }

        public String getModified() {
            return modified;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }

        public String getIfpub() {
            return ifpub;
        }

        public void setIfpub(String ifpub) {
            this.ifpub = ifpub;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }


        public List<Grade> getGrade() {
            return grade;
        }

        public void setGrade(List<Grade> grade) {
            this.grade = grade;
        }
    }

    public class Grade {
        private String year;
        private String rp;
        private String ws;
        private String jr;

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getRp() {
            return rp;
        }

        public void setRp(String rp) {
            this.rp = rp;
        }

        public String getWs() {
            return ws;
        }

        public void setWs(String ws) {
            this.ws = ws;
        }

        public String getJr() {
            return jr;
        }

        public void setJr(String jr) {
            this.jr = jr;
        }
    }

}
