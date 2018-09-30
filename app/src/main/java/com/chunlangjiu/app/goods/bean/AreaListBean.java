package com.chunlangjiu.app.goods.bean;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/9/27
 * @Describe:
 */
public class AreaListBean {

    private List<AreaBean> list;

    public List<AreaBean> getList() {
        return list;
    }

    public void setList(List<AreaBean> list) {
        this.list = list;
    }

    public class AreaBean{

        private String area_id;
        private String area_name;

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }
    }
}
