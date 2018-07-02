package com.chunlangjiu.app.net;

import java.util.List;

/**
 * Created by LiuCun on 2017/12/8.<br>
 * Describe
 */

public class WeiXinNewsBean {

    private int code;
    private String msg;
    private List<NewsBean> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewsBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(
        List<NewsBean> newslist) {
        this.newslist = newslist;
    }

    public class NewsBean {

        private String ctime;
        private String title;
        private String description;
        private String picUrl;
        private String url;

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "NewsBean{" +
                "ctime='" + ctime + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", url='" + url + '\'' +
                '}';
        }
    }


    @Override
    public String toString() {
        return "WeiXinNewsBean{" +
            "code=" + code +
            ", msg='" + msg + '\'' +
            ", newslist=" + newslist +
            '}';
    }
}
