package com.doumengmengandroidbady.activity;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;

/**
 * 作者: 边贤君
 * 描述: 育儿知识详细信息
 * 创建日期: 2018/1/12 15:44
 */
public class BabyKnowledgeDetailsActivity extends BaseActivity {

    public static final String IN_PARAM_TITLE = "title";
    public static final String IN_PARAM_PAGE = "page";

    private RelativeLayout rl_back;
    private TextView tv_title;

    private RelativeLayout rl_content;
    private WebView wv;
    private String page;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_details);
        findView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( wv != null ){
            wv.removeAllViews();
            wv.destroy();
            wv = null;
            rl_content.removeAllViews();
            rl_content = null;
        }
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);

        rl_content =findViewById(R.id.rl_content);
        initView();
    }

    private void initView(){
        rl_back.setOnClickListener(listener);

        Intent intent = getIntent();
        String title = intent.getStringExtra(IN_PARAM_TITLE);
        tv_title.setText(title);
        page = intent.getStringExtra(IN_PARAM_PAGE);

        initWebView();
    }

    private void initWebView(){
        wv = findViewById(R.id.wv);
        wv.setVerticalScrollBarEnabled(false);
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        WebSettings settings = wv.getSettings();
        settings.setSupportZoom(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            settings.setMixedContentMode(settings.getMixedContentMode());
        }

        wv.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }
        });
        wv.loadUrl(page);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rl_back:
                    back();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

}
