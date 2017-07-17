package cslg.edu.cn.news.utils;

/**
 * Created by 18049 on 2017/6/13.
 * 常量定义类
 */

public class Constants {
    /**
     * IP地址和端口
     */
    public static final String BASE_URL = "http://112.74.74.117";
    /**
     * 所有新闻的网络地址
     */
    public static final String GET_ALL_NEWS = BASE_URL+"/api/getAllPost";
    /**
     * 新闻分类的网络地址
     */
    public static final String GET_ALL_CATS=BASE_URL+"/api/getCats";
    /**
     * 根据分类显示新闻的网络地址+id
     */
    public static final  String GET_NEWS_BY_CATS=BASE_URL+"/api/getPostByCat/";
    /**
     * 根据标签显示文章列表
     */
    public static final String GET_NEWS_BY_TAG=BASE_URL+"/api/getPostByTag/";
    /**
     * 显示新闻详情的网络地址+id
     */
    public static final  String GET_POSTS = BASE_URL+"/api/getPost/";
    /**
     * 显示分类的网络地址
     */
    public static final String GET_TAGS=BASE_URL+"/api/getTags";
}
