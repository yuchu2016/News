package cslg.edu.cn.news.page;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cslg.edu.cn.news.activity.MainActivity;
import cslg.edu.cn.news.base.BasePager;
import cslg.edu.cn.news.base.MenuDetailBasePager;
import cslg.edu.cn.news.fragment.LeftmenuFragment;
import cslg.edu.cn.news.menudetailpaager.CatsMenuDetailPager;
import cslg.edu.cn.news.menudetailpaager.NewsMenuDetailPager;
import cslg.edu.cn.news.pojo.NewsCats;
import cslg.edu.cn.news.utils.CacheUtils;
import cslg.edu.cn.news.utils.Constants;
import cslg.edu.cn.news.utils.LogUtil;

/**
 * Created by 18049 on 2017/6/13.
 * 新闻
 */

public class NewsCenterPager extends BasePager {

    /**
     * 左侧菜单对应的分类
     */
    //private List<String> catsNames = new ArrayList<>();
    private List<NewsCats.CatsBean> data;
    /**
     * 详情页面的集合
     */
    private ArrayList<MenuDetailBasePager> detailBasePagers;

    public NewsCenterPager(Context context) {
        super(context);
    }


    @Override
    public void initData() {
        super.initData();
        //LogUtil.e("新闻页面数据被初始化了..");
        ib_menu.setVisibility(View.VISIBLE);
        //1.设置标题
        tv_title.setText("新闻");
        //2.联网请求得到数据，创建视图
        TextView textView = new TextView(context);

        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        //3.把子视图添加到BasePager的framlayout中
        fl_content.addView(textView);
        //4.绑定数据
        textView.setText("新闻内容");
        //5.获取缓存数据
        /*String saveJson = CacheUtils.getString(context,Constants.GET_ALL_CATS);
        if (!TextUtils.isEmpty(saveJson)){
            processeData(saveJson);
        }*/
        //5.联网请求
        getDataFromNet();
    }
    /*
    *使用Xutil请求数据
     */
    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.GET_ALL_CATS);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("使用Xutil3联网请求成功=="+result);
                //缓存数据
                CacheUtils.putString(context,Constants.GET_ALL_CATS,result);
                //设置适配器
                processeData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("使用Xutil3联网请求CATS失败=="+ex.getMessage());
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

    //解析json数据和显示数据
    private void processeData(String json) {
        NewsCats cats = parsedJson(json);
        String catsName = cats.getCats().get(0).getCatName();
        //LogUtil.e("使用Gson解析json数据成功=="+catsName);

        data = cats.getCats();
        MainActivity mainActivity = (MainActivity) context;
        //得到左侧菜单
        LeftmenuFragment leftMenuFragment  = mainActivity.getLeftmenuFrament();
        //添加详情页面
        detailBasePagers = new ArrayList<>();
        detailBasePagers.add(new NewsMenuDetailPager(context));  //新闻详情页面
        detailBasePagers.add(new CatsMenuDetailPager(context));
        detailBasePagers.add(new CatsMenuDetailPager(context));
        detailBasePagers.add(new CatsMenuDetailPager(context));
        //把数据传递给左侧菜单
        leftMenuFragment.setData(data);

    }

    /**
     * 1.使用系统API
     * 2.使用第三方解析：Gson
     * 解析JSON数据
     * @param json
     * @return
     */
    private NewsCats parsedJson(String json) {
        return new Gson().fromJson(json,NewsCats.class);
    }

    /**
     * 根据位置切换详情页面
     * @param position
     */
    public void switchPager(int position) {
        //1.设置标题
        tv_title.setText(data.get(position).getCatName());
        //2.移除内容
        fl_content.removeAllViews();

        //3.添加新内容
        MenuDetailBasePager datailBasePager = detailBasePagers.get(position);
        View rootView = datailBasePager.rootView;
        datailBasePager.initData();//初始化数据
        fl_content.addView(rootView);
    }
}
