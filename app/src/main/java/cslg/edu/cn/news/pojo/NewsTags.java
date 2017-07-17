package cslg.edu.cn.news.pojo;

import java.util.List;

/**
 * Created by 18049 on 2017/6/14.
 */

public class NewsTags {

    /**
     * retcode : 200
     * tags : [{"id":8,"tagName":"java","postNum":null},{"id":14,"tagName":"spring","postNum":null},{"id":20,"tagName":"test","postNum":null},{"id":9,"tagName":"php","postNum":null},{"id":10,"tagName":"nginx","postNum":null},{"id":11,"tagName":"mysql","postNum":null},{"id":12,"tagName":"windows","postNum":null},{"id":13,"tagName":"WNMP","postNum":null},{"id":15,"tagName":"AOP","postNum":null},{"id":16,"tagName":"springMVC","postNum":null}]
     */

    private String retcode;
    private List<TagsBean> tags;

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public static class TagsBean {
        /**
         * id : 8
         * tagName : java
         * postNum : null
         */

        private int id;
        private String tagName;
        private Object postNum;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public Object getPostNum() {
            return postNum;
        }

        public void setPostNum(Object postNum) {
            this.postNum = postNum;
        }
    }
}
