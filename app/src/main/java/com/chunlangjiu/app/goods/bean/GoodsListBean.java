package com.chunlangjiu.app.goods.bean;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/8/6
 * @Describe:
 */
public class GoodsListBean {

    private List<GoodsListDetailBean> list;

    private Pager pagers;

    public Pager getPagers() {
        return pagers;
    }

    public void setPagers(Pager pagers) {
        this.pagers = pagers;
    }

    public List<GoodsListDetailBean> getList() {
        return list;
    }

    public void setList(List<GoodsListDetailBean> list) {
        this.list = list;
    }

    public class Pager{
        private int total;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}
