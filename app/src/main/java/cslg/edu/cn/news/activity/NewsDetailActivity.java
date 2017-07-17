package cslg.edu.cn.news.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import cslg.edu.cn.news.R;

public class NewsDetailActivity extends Activity implements View.OnClickListener {
    private TextView tvTitle;
    private ImageButton ibMenu;
    private ImageButton ibBack;
    private ImageButton ibTextsize;
    private ImageButton ibShare;
    private WebView webview;
    private ProgressBar pbLoading;
    private String url;


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-06-17 15:46:11 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        tvTitle = (TextView)findViewById( R.id.tv_title );
        ibMenu = (ImageButton)findViewById( R.id.ib_menu );
        ibBack = (ImageButton)findViewById( R.id.ib_back );
        ibTextsize = (ImageButton)findViewById( R.id.ib_textsize );
        ibShare = (ImageButton)findViewById( R.id.ib_share );
        webview = (WebView)findViewById( R.id.webview );
        pbLoading = (ProgressBar)findViewById( R.id.pb_loading );


        ibMenu.setVisibility(View.GONE);
        tvTitle.setVisibility(View.GONE);
        ibBack.setVisibility(View.VISIBLE);
        ibTextsize.setVisibility(View.VISIBLE);
        ibShare.setVisibility(View.VISIBLE);

        ibBack.setOnClickListener( this );
        ibTextsize.setOnClickListener( this );
        ibShare.setOnClickListener( this );
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-06-17 15:46:11 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == ibBack ) {
            // Handle clicks for ibBack
            finish();
        } else if ( v == ibTextsize ) {
            Toast.makeText(NewsDetailActivity.this,"设置文字大小",Toast.LENGTH_SHORT).show();
            // Handle clicks for ibTextsize
        } else if ( v == ibShare ) {
            // Handle clicks for ibShare
            Toast.makeText(NewsDetailActivity.this,"分享",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        findViews();
        //获取url
        getDate();
    }

    private void getDate() {
       url =  getIntent().getStringExtra("url");
        WebSettings webSettings = webview.getSettings();
        //设置支持JS
        webSettings.setJavaScriptEnabled(true);
        //设置双击变大变小
       // webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        //设置增加缩放按钮
        webSettings.setBuiltInZoomControls(true);
        //设置webclient,不让跳转到浏览器
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbLoading.setVisibility(View.GONE);
            }
        });
        webview.loadUrl(url);

    }
}
