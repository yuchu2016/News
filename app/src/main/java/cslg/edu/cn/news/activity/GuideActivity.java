package cslg.edu.cn.news.activity;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import cslg.edu.cn.news.R;
import cslg.edu.cn.news.WelcomeActivity;
import cslg.edu.cn.news.utils.CacheUtils;
import cslg.edu.cn.news.utils.DensityUtil;

public class GuideActivity extends Activity {

    private static final String TAG = GuideActivity.class.getSimpleName();
    private ViewPager viewpager;
    private Button btn_start_main;
    private LinearLayout ll_point_group;

    private ImageView iv_red_point;
    private ArrayList<ImageView> imageViews;

    /**
     * 两点的间距
     */
    private int leftmax;

    private  int widthdpi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //以下根本没有用、、
        /*//透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);*/
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        btn_start_main = (Button) findViewById(R.id.btn_start_main);
        ll_point_group = (LinearLayout) findViewById(R.id.ll_point_group);
        iv_red_point = (ImageView) findViewById(R.id.iv_red_point);
        //准备数据
        int[] ids = new int[]{
                R.drawable.guide_1,
                R.drawable.guide_2,
                R.drawable.guide_3,
        };

        widthdpi = DensityUtil.dip2px(this,10);

        imageViews = new ArrayList<>();
        for (int i = 0;i<ids.length;i++){
            ImageView imageView = new ImageView(this);
            //设置背景
            imageView.setBackgroundResource(ids[i]);
            //添加到集合中
            imageViews.add(imageView);

            //创建  点
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_normal);
            /*
             *单位是像素
             * 把单位当成dp转成对应像素
             */
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthdpi,widthdpi);
            if(i!=0){
                //不包括第0个，所有的点距离左边有10个像素
                params.leftMargin = widthdpi ;
            }
            point.setLayoutParams(params);
            //添加到线性布局中
            ll_point_group.addView(point);
        }

        //设置ViewPage的适配器
        viewpager.setAdapter(new MypagerAdapter());
        //根据View的生命周期，当视图执行到onlayout或者ondraw的时候，视图的高和宽都有了
        iv_red_point.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());
        //得到屏幕滑动的百分比
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());

        //设置按钮的点击事件
        btn_start_main.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //1.保存曾经进入过主页面的
                CacheUtils.putBoolean(GuideActivity.this, WelcomeActivity.START_MAIN,true);
                //2.跳转到主页面
                Intent intent = new Intent(GuideActivity.this,MainActivity.class);
                startActivity(intent);
                //3.关闭引导页
                finish();
            }
        });
    }
    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{
        /**
         * 当页面滚动的时候回调这个方法
         * @param position 当前滑动页面的位置
         * @param positionOffset  页面滑动的百分比
         * @param positionOffsetPixels 滑动的像素
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //int leftmargin = (int) (positionOffset*leftmax);
           // Log.e(TAG,"position="+position+",positionOffset="+positionOffset+".positionOffsetPixels="+positionOffsetPixels);

            int leftmargin = (int)(position*leftmax+(positionOffset*leftmax));
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_red_point.getLayoutParams();
            params.leftMargin=leftmargin;
            iv_red_point.setLayoutParams(params);
        }

        /**
         * 当页面被选中的时候回调的方法
         * @param position 被选中页面对应的位置
         */
        @Override
        public void onPageSelected(int position) {
            if (position==imageViews.size()-1){
                btn_start_main.setVisibility(View.VISIBLE);
            }else {
                btn_start_main.setVisibility(View.GONE);
            }

        }

        /**
         * 当ViewPager页面滑动状态发生变化的时候
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener{
        @Override
        public void onGlobalLayout() {
            //执行不止一次
            iv_red_point.getViewTreeObserver().removeOnGlobalLayoutListener(this);

            leftmax = ll_point_group.getChildAt(1).getLeft()- ll_point_group.getChildAt(0).getLeft();
        }
    }
    class MypagerAdapter extends PagerAdapter{
        //返回数据的总个数
        @Override
        public int getCount() {
            return imageViews.size();
        }

        /**
         *
         * @param container   ViewPager
         * @param position  要创建页面的位置
         * @return  返回和创建当前界面关系的值
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            //添加到容器中
            container.addView(imageView);
            //return position;
            return imageView;
            // return super.instantiateItem(container, position);
        }
        //判断当前创建的视图，object=>上面instantiateItem返回的结果值
        @Override
        public boolean isViewFromObject(View view, Object object) {
            //return view==imageViews.get(Integer.parseInt((String)object));
            return view==object;
        }

        /**
         * 销毁页面
         * @param container
         * @param position 要销毁页面的位置
         * @param object  要销毁的页面
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //  super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

    }
}
