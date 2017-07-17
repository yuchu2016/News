package cslg.edu.cn.news.menudetailpaager;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cslg.edu.cn.news.R;
import cslg.edu.cn.news.activity.MainActivity;
import cslg.edu.cn.news.base.MenuDetailBasePager;
import cslg.edu.cn.news.menudetailpaager.tabledetailpager.TableDetailPager;
import cslg.edu.cn.news.pojo.NewsCats;
import cslg.edu.cn.news.pojo.NewsTags;
import cslg.edu.cn.news.utils.CacheUtils;
import cslg.edu.cn.news.utils.Constants;
import cslg.edu.cn.news.utils.LogUtil;

/**
 * Created by 18049 on 2017/6/14.
 * 新闻详情页面
 */

public class NewsMenuDetailPager extends MenuDetailBasePager {

    @ViewInject(R.id.tabPageIndicator)
    private TabPageIndicator tabPageIndicator ;
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.ib_tab_next)
    private ImageButton ib_tab_next;

    /**
     * 标签的数据
     */
    private List<NewsTags.TagsBean> data;
    /**
     * 标签的集合
     */
    private ArrayList<TableDetailPager> tableDetailPagers;

    public NewsMenuDetailPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(context,R.layout.newsmenu_detail_pager,null);
        x.view().inject(this,view);

        //设置点击事件
        ib_tab_next.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
        });
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        LogUtil.e("新闻详情页面数据初始化");
       // textView.setText("新闻详情页面内容");
        getDataFromNet2();
        tableDetailPagers =new ArrayList<>();
        for (int i=0;i<data.size();i++){
            tableDetailPagers.add(new TableDetailPager(context,data.get(i)));
        }

        //设置适配器
        viewPager.setAdapter(new MyNewsMenuDetailPagerAdapter());
        //ViewPager和TabPageIndicator关联
        tabPageIndicator.setViewPager(viewPager);
        //以后页面监听变化将用tabpageIndicator监听
        tabPageIndicator.setOnPageChangeListener(new MyOnPageChangeListener());
    }
    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position==0){
                //SlidingMenu可以全屏滑动
                isEnableSlidingMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);
            }else {
                //SlidingMenu不可以滑动
                isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

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
    class MyNewsMenuDetailPagerAdapter extends PagerAdapter{
        @Override
        public CharSequence getPageTitle(int position) {
            return data.get(position).getTagName();
        }

        @Override
        public int getCount() {
            return tableDetailPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TableDetailPager tableDetailPager = tableDetailPagers.get(position);
            View rootview = tableDetailPager.rootView;
            tableDetailPager.initData();  //初始化数据
            container.addView(rootview);
            return rootview;
        }
    }



    private void getDataFromNet2() {
        RequestParams params = new RequestParams(Constants.GET_TAGS);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("使用Xutil3联网请求成功=="+result);
                //缓存数据
                //CacheUtils.putString(context,Constants.GET_TAGS,result);
                //设置适配器
                processeData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("使用Xutil3联网请求TAG失败=="+ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("使用Xutil3联网请求取消=="+cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e("使用Xutils3-onFinished");
            }
        });
    }

    private void processeData(String json) {
        NewsTags tags = parseJson(json);
        data = tags.getTags();
    }

    private NewsTags parseJson(String json) {
        return new Gson().fromJson(json,NewsTags.class);
    }
}
