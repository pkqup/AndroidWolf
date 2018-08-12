package com.chunlangjiu.app.user.bean;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/8/12.
 * @Describe:
 */
public class LocalAreaBean {

    private List<ProvinceData> region;

    public List<ProvinceData> getRegion() {
        return region;
    }

    public void setRegion(List<ProvinceData> region) {
        this.region = region;
    }

    public class ProvinceData {
        private String id;
        private String value;
        private String parentId;
        private List<City> children;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public List<City> getChildren() {
            return children;
        }

        public void setChildren(List<City> children) {
            this.children = children;
        }

        public class City{
            private String id;
            private String value;
            private String parentId;
            private List<District> children;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public List<District> getChildren() {
                return children;
            }

            public void setChildren(List<District> children) {
                this.children = children;
            }

            public class District{
                private String id;
                private String value;
                private String parentId;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getParentId() {
                    return parentId;
                }

                public void setParentId(String parentId) {
                    this.parentId = parentId;
                }
            }
        }
    }


}
