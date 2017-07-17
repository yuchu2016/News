package cslg.edu.cn.news.pojo;

import java.util.List;

/**
 * Created by 18049 on 2017/6/16.
 */

public class TableDetailPagerBean {

    /**
     * retcode : 200
     * posts : [{"id":25,"title":"Spring面向切面编程（AOP）概念","summary":"51. 解释AOP面向切面的编程，或AOP， 是一种编程技术，允许程序模块化横向切割关注点，或横切典型的责任划分，如日志和事务管理。52. Aspect 切面AOP核心就是切面，它","labelImg":"/image/20170406/1491461376933789.jpg","catId":3,"userId":null,"userName":"yuchu","isValid":true,"createdAt":null,"updatedAt":"2017-04-06","content":null,"cats":{"id":null,"catName":"java"},"post_extends":{"id":null,"postId":null,"browser":16,"collect":null,"praise":null,"comment":0}},{"id":26,"title":"SpringMVC使用POJO对象绑定请求参数值","summary":"用处：用处：使用POJO绑定请求参数值，springMVC会根据请求参数名和POJO属性名进行自动装配，自动为该对象填充属性值，同时支持级联属性。1.创建类，并创建get，set方","labelImg":"/image/20170406/1491461755279018.jpg","catId":3,"userId":null,"userName":"yuchu","isValid":true,"createdAt":null,"updatedAt":"2017-04-06","content":null,"cats":{"id":null,"catName":"java"},"post_extends":{"id":null,"postId":null,"browser":89,"collect":null,"praise":null,"comment":0}}]
     */

    private String retcode;
    private List<PostsBean> posts;

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public List<PostsBean> getPosts() {
        return posts;
    }

    public void setPosts(List<PostsBean> posts) {
        this.posts = posts;
    }

    public static class PostsBean {
        /**
         * id : 25
         * title : Spring面向切面编程（AOP）概念
         * summary : 51. 解释AOP面向切面的编程，或AOP， 是一种编程技术，允许程序模块化横向切割关注点，或横切典型的责任划分，如日志和事务管理。52. Aspect 切面AOP核心就是切面，它
         * labelImg : /image/20170406/1491461376933789.jpg
         * catId : 3
         * userId : null
         * userName : yuchu
         * isValid : true
         * createdAt : null
         * updatedAt : 2017-04-06
         * content : null
         * cats : {"id":null,"catName":"java"}
         * post_extends : {"id":null,"postId":null,"browser":16,"collect":null,"praise":null,"comment":0}
         */

        private int id;
        private String title;
        private String summary;
        private String labelImg;
        private int catId;
        private Object userId;
        private String userName;
        private boolean isValid;
        private Object createdAt;
        private String updatedAt;
        private Object content;
        private CatsBean cats;
        private PostExtendsBean post_extends;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getLabelImg() {
            return labelImg;
        }

        public void setLabelImg(String labelImg) {
            this.labelImg = labelImg;
        }

        public int getCatId() {
            return catId;
        }

        public void setCatId(int catId) {
            this.catId = catId;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public boolean isIsValid() {
            return isValid;
        }

        public void setIsValid(boolean isValid) {
            this.isValid = isValid;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Object getContent() {
            return content;
        }

        public void setContent(Object content) {
            this.content = content;
        }

        public CatsBean getCats() {
            return cats;
        }

        public void setCats(CatsBean cats) {
            this.cats = cats;
        }

        public PostExtendsBean getPost_extends() {
            return post_extends;
        }

        public void setPost_extends(PostExtendsBean post_extends) {
            this.post_extends = post_extends;
        }

        public static class CatsBean {
            /**
             * id : null
             * catName : java
             */

            private Object id;
            private String catName;

            public Object getId() {
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }

            public String getCatName() {
                return catName;
            }

            public void setCatName(String catName) {
                this.catName = catName;
            }
        }

        public static class PostExtendsBean {
            /**
             * id : null
             * postId : null
             * browser : 16
             * collect : null
             * praise : null
             * comment : 0
             */

            private Object id;
            private Object postId;
            private int browser;
            private Object collect;
            private Object praise;
            private int comment;

            public Object getId() {
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }

            public Object getPostId() {
                return postId;
            }

            public void setPostId(Object postId) {
                this.postId = postId;
            }

            public int getBrowser() {
                return browser;
            }

            public void setBrowser(int browser) {
                this.browser = browser;
            }

            public Object getCollect() {
                return collect;
            }

            public void setCollect(Object collect) {
                this.collect = collect;
            }

            public Object getPraise() {
                return praise;
            }

            public void setPraise(Object praise) {
                this.praise = praise;
            }

            public int getComment() {
                return comment;
            }

            public void setComment(int comment) {
                this.comment = comment;
            }
        }
    }
}
