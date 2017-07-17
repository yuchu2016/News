package cslg.edu.cn.news.base;

import android.content.Context;
import android.view.View;

import cslg.edu.cn.news.pojo.NewsCats;

/**
 * Created by 18049 on 2017/6/14.
 * 详情页面的基类
 */

public abstract class MenuDetailBasePager  {
    /**
     * 上下文
     */
    public final Context context;
    /**
     * 代表各个详情页的视图
     */
    public View rootView;

    public MenuDetailBasePager(Context context){
        this.context = context;
        rootView = initView();
    }

    /**
     * 抽象方法，每个页面实现不同的效果
     * @return
     */
    public abstract View initView();

    /**
     * 子页面需要绑定数据，联网请求数据，重写该方法
     */
    public void initData(){

    }
}
