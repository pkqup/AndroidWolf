package com.chunlangjiu.app.user.bean;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/9/15.
 * @Describe:
 */
public class ShopCatIdList {


    private List<Category> category;

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public class Category{

     private List<Children> children;

        public List<Children> getChildren() {
            return children;
        }

        public void setChildren(List<Children> children) {
            this.children = children;
        }
    }


    public class Children{
        private String cat_id;
        private String cat_name;
        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getCat_id() {
            return cat_id;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public String getCat_name() {
            return cat_name;
        }

        public void setCat_name(String cat_name) {
            this.cat_name = cat_name;
        }
    }
}
