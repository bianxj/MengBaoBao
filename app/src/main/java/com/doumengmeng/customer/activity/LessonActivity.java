package com.doumengmeng.customer.activity;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.base.BaseSwipeActivity;

/**
 * 作者: 边贤君
 * 描述: 萌课堂详情页面
 */
public class LessonActivity extends BaseSwipeActivity {

    public final static String URL_TITLE = "url_title";

    private RelativeLayout rl_back;
    private TextView tv_title;
    private WebView wv;

    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        initData();
    }

    private void initData(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);
        wv = findViewById(R.id.wv);
        tv_title.setText("萌课堂详情");
        url = getIntent().getStringExtra(URL_TITLE);
        rl_back.setOnClickListener(listener);
        initWebView();
    }

    private void initWebView(){
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            settings.setMixedContentMode(settings.getMixedContentMode());
        }

        wv.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if ( -1 == error.getErrorCode() ){
                        wv.loadUrl(url);
                    }
                }
            }
        });
        wv.loadUrl(url);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.rl_back:
                    back();
                    break;
            }
        }
    };

    protected void back(){
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( wv != null ){
            wv.destroy();
        }
    }
}
