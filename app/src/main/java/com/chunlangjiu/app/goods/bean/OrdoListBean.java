package com.chunlangjiu.app.goods.bean;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/9/27
 * @Describe:
 */
public class OrdoListBean {

    private List<OrdoBean> list;

    public List<OrdoBean> getList() {
        return list;
    }

    public void setList(List<OrdoBean> list) {
        this.list = list;
    }

    public class OrdoBean{

        private String odor_id;
        private String odor_name;

        public String getOdor_id() {
            return odor_id;
        }

        public void setOdor_id(String odor_id) {
            this.odor_id = odor_id;
        }

        public String getOdor_name() {
            return odor_name;
        }

        public void setOdor_name(String odor_name) {
            this.odor_name = odor_name;
        }
    }
}
