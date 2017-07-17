package cslg.edu.cn.news.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import cslg.edu.cn.news.R;
import cslg.edu.cn.news.activity.MainActivity;
import cslg.edu.cn.news.base.BaseFragment;
import cslg.edu.cn.news.base.BasePager;
import cslg.edu.cn.news.page.GovaffairPager;
import cslg.edu.cn.news.page.HomePager;
import cslg.edu.cn.news.page.NewsCenterPager;
import cslg.edu.cn.news.page.SettingPager;
import cslg.edu.cn.news.page.SmartServicePager;
import cslg.edu.cn.news.utils.LogUtil;
import cslg.edu.cn.news.view.NoScrollViewPager;
import cslg.edu.cn.news.adapter.ContentFragmentAdapt;

/**
 * Created by 18049 on 2017/6/13.
 */

public class ContentFragment extends BaseFragment {
    //2.初始化控件
    @ViewInject(R.id.viewpager)
    private NoScrollViewPager viewpager;

    @ViewInject(R.id.rg_main)
    private RadioGroup rg_main;
    //五个页面的集合
    private ArrayList<BasePager> basePagers;

    @Override
    public View initView() {
        LogUtil.e("正文Flagment页面初始化");
        View view = View.inflate(context, R.layout.content_fragment,null);
        //viewpager = (ViewPager)view.findViewById(R.id.viewpager);
        //rg_main = (RadioGroup) view.findViewById(R.id.rg_main);
        //1.把视图注入到框架中，让ContentFragment.this和view关联
        x.view().inject(ContentFragment.this,view);
        return view;
    }

    @Override
    public void initDate() {
        super.initDate();
        LogUtil.e("正文Flagment数据初始化");
        //初始化五个界面并放入集合中
        basePagers = new ArrayList<>();
        basePagers.add(new HomePager(context));//主页面
        basePagers.add(new NewsCenterPager(context)); //新闻界面
        basePagers.add(new SmartServicePager(context)); //智慧服务界面
        basePagers.add(new GovaffairPager(context));   //政要指南页面
        basePagers.add(new SettingPager(context));  //设置页面

        //设置Viewpager的适配器
        viewpager.setAdapter( new ContentFragmentAdapt(basePagers));
        //设置radiogroup的选中状态改变的监听
        rg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //监听某个页面被选中，初始化对应的页面的数据
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());
        //设置默认选中
        rg_main.check(R.id.rb_home);
        basePagers.get(0).initData();
        isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
    }

    /**
     * 得到新闻中心
     * @return
     */
    public NewsCenterPager getNewsCenterPager() {
        return (NewsCenterPager) basePagers.get(1);
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * 当某个页面被选中的时候回调这个方法
         * @param position   被选中页面的位置
         */
        @Override
        public void onPageSelected(int position) {
            //调用被选中的页面的initDate方法
            basePagers.get(position).initData();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{
        /**
         *
         * @param group RadioGroup
         * @param checkedId 被选中的RadioButtom的ID
         */
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_home:                   //主页面
                    viewpager.setCurrentItem(0,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_newscenter:            //新闻
                    viewpager.setCurrentItem(1,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);
                    break;
                case R.id.rb_smartservice:          //智慧服务
                    viewpager.setCurrentItem(2,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_govaffair:             //政务
                    viewpager.setCurrentItem(3,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_setting:               //设置
                    viewpager.setCurrentItem(4,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
            }
        }
    }

    /**
     * 根据传入参数设置是否可以滑出SlidingMenu
     * @param touchmodeFullscreen
     */
    private void isEnableSlidingMenu(int touchmodeFullscreen) {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.getSlidingMenu().setTouchModeAbove(touchmodeFullscreen);
    }
}
