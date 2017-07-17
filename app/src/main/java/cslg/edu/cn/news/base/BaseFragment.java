package cslg.edu.cn.news.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 18049 on 2017/6/13.
 *基本fragment，leftmenufragment和contentfragment将继承这个基类
 */

public abstract class BaseFragment extends Fragment {

    public Activity context;  //MainActivitu
    /**
     * 当fragment被创建时回调
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
    }

    /**
     * 当视图被创建的时候回调
     * @param inflater
     * @param container
     * @param savedInstanceState
     * 创建了视图
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView();
    }

    /**
     * 让孩子实现自己的视图，达到特有的效果
     * @return
     */
    public abstract View initView();

    /**
     * 当activity被创建后被回调
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDate();
    }

    /**
     *1. 如果子页面没有数据，联网请求数据，并且绑定到initView初始化视图上
     *2.绑定到initView初始化视图上
     */
    public void initDate(){

    }
}
