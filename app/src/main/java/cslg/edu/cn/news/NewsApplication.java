package cslg.edu.cn.news;

import android.app.Application;

import org.xutils.x;

/**
 * Created by 18049 on 2017/6/13.
 * 作用：代表整个软件
 */

public class NewsApplication extends Application {
    /**
     * 所以组件被初始化之前创建
     */
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.setDebug(true);
        x.Ext.init(this);
    }
}
