package cslg.edu.cn.news.pojo;

/**
 * Created by 18049 on 2017/6/14.
 */

import java.util.List;

/**
 * 新闻分类json解析后的数据
 */
public class NewsCats {


    /**
     * retcode : 200
     * cats : [{"id":3,"catName":"java"},{"id":4,"catName":"php"},{"id":5,"catName":"web开发"}]
     */

    private String retcode;
    private List<CatsBean> cats;

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public List<CatsBean> getCats() {
        return cats;
    }

    public void setCats(List<CatsBean> cats) {
        this.cats = cats;
    }

    public static class CatsBean {
        /**
         * id : 3
         * catName : java
         */

        private int id;
        private String catName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCatName() {
            return catName;
        }

        public void setCatName(String catName) {
            this.catName = catName;
        }
    }
}
