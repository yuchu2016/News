package cslg.edu.cn.news.menudetailpaager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import cslg.edu.cn.news.base.MenuDetailBasePager;
import cslg.edu.cn.news.utils.LogUtil;

/**
 * Created by 18049 on 2017/6/14.
 * 分类详情页面
 */

public class CatsMenuDetailPager extends MenuDetailBasePager {

    private TextView textView;

    public CatsMenuDetailPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();

        LogUtil.e("新闻详情页面数据初始化");
        textView.setText("新闻详情页面内容");
    }
}
