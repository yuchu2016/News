package cslg.edu.cn.news.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by 18049 on 2017/6/16.
 * 水平新方向滑动的ViewPager
 */

public class HorizontalScrollViewPager extends ViewPager {
    public HorizontalScrollViewPager(Context context) {
        super(context);
    }

    public HorizontalScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 其实坐标
     */
    private  float startX;
    private float startY;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                //1.记录其实坐标
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = ev.getX();
                float endY = ev.getY();
                //3.计算偏移量
                float distanceX = endX-startX;
                float distanceY =endY-startY;
                //4.判断滑动方向
                if (Math.abs(distanceX)>Math.abs(distanceY)){
                    //水平滑动
                    //当滑动到第0个页面
                    if (getCurrentItem()==0&&distanceX>0)
                        getParent().requestDisallowInterceptTouchEvent(false);
                    //当滑动到最后一个页面，并且是从右往左滑动
                    else if ((getCurrentItem()==(getAdapter().getCount()-1))&& distanceX<0)
                        getParent().requestDisallowInterceptTouchEvent(false);
                    //中间部分
                    else
                        getParent().requestDisallowInterceptTouchEvent(true);
                }else {
                    //垂直滑动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }
}
