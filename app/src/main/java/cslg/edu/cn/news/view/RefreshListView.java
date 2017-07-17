package cslg.edu.cn.news.view;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Date;

import cslg.edu.cn.news.R;

/**
 * Created by 18049 on 2017/6/16.
 * 自定义下拉刷新
 */

public class RefreshListView extends ListView {

    //下拉刷新
    private static final int PULL_DOWN_REFRESH=0;
    //手松刷新
    private static final int RELEASE_REFRESH=1;
    //正在刷新
    private static final int REFRESHING = 2;

    private int currentStatus = PULL_DOWN_REFRESH;
    /**
     * 下拉刷新
     */
    private LinearLayout headerView;

    private View ll_pull_down_refresh;

    private ImageView iv_arrow;

    private ProgressBar pb_status;

    private TextView tv_status;

    private TextView tv_time;

    private Animation upAnimation;
    private Animation downAnimation;
    /**
     * 控件的高
     */
    private int pullDownRefreshHeight;

    public RefreshListView(Context context) {
        this(context,null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        initHeaderView(context);
        initAnimation();
    }


    private void initAnimation() {
        upAnimation = new RotateAnimation(0,-180,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        upAnimation.setDuration(500);
        upAnimation.setFillAfter(true);

        downAnimation = new RotateAnimation(-180,-360,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        downAnimation.setDuration(500);
        downAnimation.setFillAfter(true);
    }

    private void initHeaderView(Context context) {
         headerView = (LinearLayout) View.inflate(context, R.layout.refresh_header,null);
        //下拉刷新控件
        ll_pull_down_refresh = headerView.findViewById(R.id.ll_pull_down_refresh);
        iv_arrow = (ImageView) headerView.findViewById(R.id.iv_arrow);
        pb_status = (ProgressBar)headerView.findViewById(R.id.pb_status);
        tv_status = (TextView) headerView.findViewById(R.id.tv_status);
        tv_time= (TextView) headerView.findViewById(R.id.tv_time);

        //测量
        ll_pull_down_refresh.measure(0,0);
        pullDownRefreshHeight = ll_pull_down_refresh.getMeasuredHeight();
        //默认隐藏
        ll_pull_down_refresh.setPadding(0,-pullDownRefreshHeight,0,0);

        //添加头
        addHeaderView(headerView);
    }

    private float startY;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //1.记录起始坐标
                startY=ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY==-1){
                    startY=ev.getY();
                }
                //如果正在刷新，就不让再刷新
                if (currentStatus==REFRESHING)
                    break;
                //2.来到新的坐标
                float endY =ev.getY();
                //3.计算滑动距离
                float distanceY=endY-startY;
                if (distanceY>0){   //下拉
                    int paddingTop= (int) (-pullDownRefreshHeight+distanceY);

                    if (paddingTop<0&&currentStatus !=PULL_DOWN_REFRESH){
                        //下拉刷新状态
                        currentStatus = PULL_DOWN_REFRESH;
                        //更新状态
                        refreshViewState();
                    }else if (paddingTop>0&&currentStatus!=RELEASE_REFRESH){
                        //手松刷新
                        currentStatus=RELEASE_REFRESH;
                        //更新状态
                        refreshViewState();
                    }
                    ll_pull_down_refresh.setPadding(0,paddingTop,0,0);
                }
                break;
            case MotionEvent.ACTION_UP:
                startY=-1;
                if (currentStatus == PULL_DOWN_REFRESH){
                   // View.setPadding(0,-pullDownRefreshHeight,0,0);
                    ll_pull_down_refresh.setPadding(0,-pullDownRefreshHeight,0,0);
                }else if (currentStatus==RELEASE_REFRESH){
                    currentStatus = REFRESHING;
                    //设置状态正在刷新
                    currentStatus = REFRESHING;
                    refreshViewState();
                   // View.setPadding(0,0,0,0);
                    ll_pull_down_refresh.setPadding(0,0,0,0);
                    //回调接口
                    if (mOnRefreshListener!=null){
                        mOnRefreshListener.onPullDownRefresh();
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void refreshViewState() {
        switch (currentStatus){
            case PULL_DOWN_REFRESH://下了刷新状态
                iv_arrow.startAnimation(downAnimation);
                tv_status.setText("下拉刷新...");
                break;
            case RELEASE_REFRESH://手松刷新状态
                iv_arrow.startAnimation(upAnimation);
                tv_status.setText("手松刷新...");
                break;
            case REFRESHING://正在刷新状态
                tv_status.setText("正在刷新...");
                pb_status.setVisibility(VISIBLE);
                iv_arrow.clearAnimation();
                iv_arrow.setVisibility(GONE);
                break;
        }
    }

    /**
     * 当联网成功和失败时候回调该方法
     * 用于刷新状态的还原
     * @param success
     */
    public void onRefreshFinish(boolean success) {
        tv_status.setText("下拉刷新...");
        currentStatus = PULL_DOWN_REFRESH;
        iv_arrow.clearAnimation();
        pb_status.setVisibility(GONE);
        iv_arrow.setVisibility(VISIBLE);
        //隐藏控件
        ll_pull_down_refresh.setPadding(0,-pullDownRefreshHeight,0,0);
        if (success){
            //设置最新的更新时间
            tv_time.setText("上次更新时间"+getSystemTime());
        }
    }

    /**
     * 得到当前时间
     * @return
     */
    private String getSystemTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return  format.format(new Date());
    }

    /**
     * 监听
     */
    public interface OnRefreshListener{
        /**
         * 当下拉刷新的时候回调这个方法
         */
        public void onPullDownRefresh();
    }
    private OnRefreshListener mOnRefreshListener;
    /**
     * 设置监听刷新，由外界设置
     */
    public void setOnRefreshListener(OnRefreshListener l){
        this.mOnRefreshListener = l;
    }
}
