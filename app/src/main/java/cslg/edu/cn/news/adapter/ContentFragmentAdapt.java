package cslg.edu.cn.news.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cslg.edu.cn.news.base.BasePager;

/**
 * Created by 18049 on 2017/6/13.
 */

public class  ContentFragmentAdapt extends PagerAdapter {
    private ArrayList<BasePager> basePagers;

    public ContentFragmentAdapt(ArrayList<BasePager> basePagers){
        this.basePagers=basePagers;
    }
    @Override
    public int getCount() {
        return basePagers.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BasePager basePager =  basePagers.get(position);//各个页面的实例
        View rootview = basePager.rootView;//各个子界面
        //调用各个页面的initDate();
        //basePager.initData();

        container.addView(rootview);
        return rootview;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view==object;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}