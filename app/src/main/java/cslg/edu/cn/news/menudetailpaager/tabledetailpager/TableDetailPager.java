package cslg.edu.cn.news.menudetailpaager.tabledetailpager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.CustomViewAbove;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

import cslg.edu.cn.news.R;
import cslg.edu.cn.news.activity.NewsDetailActivity;
import cslg.edu.cn.news.base.MenuDetailBasePager;
import cslg.edu.cn.news.pojo.NewsCats;
import cslg.edu.cn.news.pojo.NewsTags;
import cslg.edu.cn.news.pojo.TableDetailPagerBean;
import cslg.edu.cn.news.utils.CacheUtils;
import cslg.edu.cn.news.utils.Constants;
import cslg.edu.cn.news.utils.LogUtil;
import cslg.edu.cn.news.view.RefreshListView;

/**
 * Created by 18049 on 2017/6/14.
 *标签详情页面
 */

public class TableDetailPager extends MenuDetailBasePager {

    public static final String READ_ARRAY_ID = "read_array_id";
    private ViewPager viewPager;
    //private TextView textView;
    private TextView tv_title;
    private LinearLayout ll_point_group;
    private RefreshListView listView;
    private NewsTags.TagsBean tagsBean;
    private String url;
    private List<TableDetailPagerBean.PostsBean> posts;
    private int topSize;
    private TabDetailPagerListAdapt adapter;
    private ImageOptions imageOptions;

    public TableDetailPager(Context context, NewsTags.TagsBean tagsBean) {
        super(context);
        this.tagsBean=tagsBean;
        imageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(100), DensityUtil.dip2px(100))
                .setRadius(DensityUtil.dip2px(5))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                //.setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.drawable.news_pic_default)
                .setFailureDrawableId(R.drawable.news_pic_default)
                .build();
    }

    @Override
    public View initView() {
      /*  textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);*/
        View view = View.inflate(context, R.layout.tabdetail_pager,null);
        listView  = (RefreshListView) view.findViewById(R.id.listview);

        View topNewsView = view.inflate(context,R.layout.topnews,null);
        viewPager = (ViewPager) topNewsView.findViewById(R.id.viewpager);
        tv_title = (TextView) topNewsView.findViewById(R.id.tv_title);
        ll_point_group = (LinearLayout) topNewsView.findViewById(R.id.ll_point_group);

        //把顶部轮播图以头的方式添加到listview
        listView.addHeaderView(topNewsView);

        //设置监听下拉刷新
        listView.setOnRefreshListener(new MyOnRefreshListener());
        //设置ListView的item的点击监听
        listView.setOnItemClickListener(new MyOnItemClickListener());
        return view;
    }
    class MyOnItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int realPosition = position - 2;
            TableDetailPagerBean.PostsBean post =  posts.get(realPosition);
            //Toast.makeText(context,"post==id=="+post.getId()+",post.title=="+post.getTitle(),Toast.LENGTH_SHORT).show();

            //1.取出保存的ID集合
            String idArray = CacheUtils.getString(context, READ_ARRAY_ID);
            //2.判断是否存在，如果不存在，才保存，并且刷新适配器
            if (!idArray.contains(post.getId()+"")){
                CacheUtils.putString(context,READ_ARRAY_ID,idArray+post.getId()+",");
                //刷新适配器

                adapter.notifyDataSetChanged();
            }
            //跳转到文章浏览页面
            Intent intent = new Intent(context,NewsDetailActivity.class);
            intent.putExtra("url",Constants.GET_POSTS+post.getId());
            context.startActivity(intent);
        }
    }

    class MyOnRefreshListener implements RefreshListView.OnRefreshListener{
        @Override
        public void onPullDownRefresh() {
            Toast.makeText(context,"页面刷新中",Toast.LENGTH_SHORT).show();
            getDataFromNet();
        }
    }

    @Override
    public void initData() {
        super.initData();
        //textView.setText(catsBean.getCatName());
        //textView.setText(tagsBean.getTagName());
        url = Constants.GET_NEWS_BY_TAG+tagsBean.getId();
        //把之前缓存的数据取出
        String saveJson = CacheUtils.getString(context,url);
        if (!TextUtils.isEmpty(saveJson)){
            //解析数据
            processDate(saveJson);
        }
        LogUtil.e(tagsBean.getTagName()+"的联网地址=="+url);
        //联网请求
        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(4000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //缓存数据
                CacheUtils.putString(context,url,result);
                LogUtil.e(tagsBean.getTagName()+"-页面数据请求成功=="+result);
                //解析和处理显示数据
                processDate(result);
                //隐藏下拉刷新-更新时间
                listView.onRefreshFinish(true);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e(tagsBean.getTagName()+"-页面数据请求失败=="+ex.getMessage());
                //隐藏下拉刷新-不更新时间
                listView.onRefreshFinish(false);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e(tagsBean.getTagName()+"-页面数据请求取消=="+cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e(tagsBean.getTagName()+"-页面数据请求结束");
            }
        });
    }

    /**
     * 之前点高亮显示的位置
     */
    private int prePosition;
    private void processDate(String json) {
        TableDetailPagerBean bean = prasedJson(json);
        LogUtil.e(bean.getPosts().get(prePosition).getTitle());
        posts = bean.getPosts();
        //设置轮播适配器
        viewPager.setAdapter(new TabDetailPagerTopNewsAdapter());
        //添加点
        addPoint();

        //监听页面改变，设置红点和文本变化
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        tv_title.setText(posts.get(0).getTitle());
        //设置ListView适配器
        adapter = new TabDetailPagerListAdapt();
        listView.setAdapter(adapter);

    }
    class TabDetailPagerListAdapt extends BaseAdapter{
        @Override
        public int getCount() {
            return posts.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView==null){
                convertView =View.inflate(context,R.layout.item_tabdetail_pager,null);
                viewHolder = new ViewHolder();
                viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //根据位置得到数据
            TableDetailPagerBean.PostsBean post = posts.get(position);
            String imageurl = Constants.BASE_URL+post.getLabelImg();
            //请求图片Xutil3
           //x.image().bind(viewHolder.iv_icon,imageurl);
            //请求图片Glide
           Glide.with(context)
                    .load(imageurl)
                   .diskCacheStrategy(DiskCacheStrategy.ALL)
                   .into(viewHolder.iv_icon);
            //设置标题
            viewHolder.tv_title.setText(post.getTitle());
            //设置时间
            viewHolder.tv_time.setText(post.getUpdatedAt());

            String idArray = CacheUtils.getString(context,READ_ARRAY_ID);
            if (idArray.contains(post.getId()+"")){
                //设置灰色
                viewHolder.tv_title.setTextColor(Color.GRAY);
            }else {
                //设置黑色
                viewHolder.tv_title.setTextColor(Color.BLACK);
            }
            return convertView;
        }
    }
    static class ViewHolder{
        ImageView iv_icon;
        TextView tv_title;
        TextView tv_time;
    }

    private void addPoint() {
        ll_point_group.removeAllViews();


        for (int i=0;i<topSize;i++){
            //设置背景选择器
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(R.drawable.point_selector);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(5),DensityUtil.dip2px(5));
            if (i==0){
                imageView.setEnabled(true);
            }else {
                imageView.setEnabled(false);
                params.leftMargin = DensityUtil.dip2px(8);
            }

            imageView.setLayoutParams(params);

            ll_point_group.addView(imageView);
        }
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //1.设置文本
            tv_title.setText(posts.get(position).getTitle());

            //2.对应红点高亮
            //把之前的变成灰色
            ll_point_group.getChildAt(prePosition).setEnabled(false);
            //把当前的设置红色
            ll_point_group.getChildAt(position).setEnabled(true);

            prePosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    class TabDetailPagerTopNewsAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            if (posts.size()<4){
                topSize = posts.size();
                return posts.size();
            }
            else{
                topSize = 4;
                return 4;
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            //设置默认图片
            imageView.setBackgroundResource(R.drawable.home_scroll_default);
            //X和Y轴拉伸
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //把图片添加到容器Viewoager
            container.addView(imageView);
            TableDetailPagerBean.PostsBean post = posts.get(position);
            //图片请求地址
            String imageurl = Constants.BASE_URL+post.getLabelImg();
            //联网请求图片
            x.image().bind(imageView,imageurl,imageOptions);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private TableDetailPagerBean prasedJson(String json) {
        return new Gson().fromJson(json,TableDetailPagerBean.class);
    }


}
