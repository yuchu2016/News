package cslg.edu.cn.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import cslg.edu.cn.news.activity.GuideActivity;
import cslg.edu.cn.news.activity.MainActivity;
import cslg.edu.cn.news.utils.CacheUtils;

public class WelcomeActivity extends Activity {

    /**
     * 静态常量
     */
    public static final String START_MAIN = "start_main";
    private RelativeLayout rl_welcome_root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        rl_welcome_root = (RelativeLayout) findViewById(R.id.rl_welcome_root);

        //渐变动画
        AlphaAnimation aa = new AlphaAnimation(0,1);
        // aa.setDuration(500);//持续播放时间
        aa.setFillAfter(true);
        //缩放动画
        ScaleAnimation sa = new ScaleAnimation(0,1,0,1,ScaleAnimation.RELATIVE_TO_SELF,0.5f,ScaleAnimation.RELATIVE_TO_SELF,0.5f);
        //sa.setDuration(500);
        sa.setFillAfter(true);
        //旋转动画
        RotateAnimation ra = new RotateAnimation(0,360,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        //ra.setDuration(500);
        ra.setFillAfter(true);


        AnimationSet set = new AnimationSet(false);
        //添加三个动画，没有先后顺序，同时播放动画
        set.addAnimation(aa);
        set.addAnimation(sa);
        set.addAnimation(ra);
        set.setDuration(2000);
        rl_welcome_root.startAnimation(set);
        set.setAnimationListener(new MyAnimationListener());
    }

    class MyAnimationListener implements Animation.AnimationListener{

        /*
        当动画开始播放时回调这个方法
         */
        @Override
        public void onAnimationStart(Animation animation){

        }
        /*
        当动画结束播放时回调这个方法
         */
        @Override
        public void onAnimationEnd(Animation animation){
            //判断是否进入过主页面
            boolean isStartMain = CacheUtils.getBoolean(WelcomeActivity.this,START_MAIN);
            Intent intent;
            if(isStartMain) {
                //如果没有进入过主页面，则进入引导页面
                 intent = new Intent(WelcomeActivity.this,MainActivity.class);
            }else {//如果进入过主页面，直接进入主页面
                 intent = new Intent(WelcomeActivity.this,GuideActivity.class);
            }
            startActivity(intent);
            //关闭欢迎页面
            finish();
            //Toast.makeText(WelcomeActivity.this,"动画播放完毕",Toast.LENGTH_SHORT).show();
        }
        /*
        当动画重复播放时回调这个方法
         */
        @Override
        public void onAnimationRepeat(Animation animation){

        }
    }
}
