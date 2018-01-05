package com.doumengmengandroidbady.fragment;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseFragment;

/**
 * Created by Administrator on 2017/12/7.
 */
public class LessonFragment extends BaseFragment {

    private RelativeLayout rl_back;
    private TextView tv_title;
    private WebView wv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson, null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        rl_back = view.findViewById(R.id.rl_back);
        tv_title = view.findViewById(R.id.tv_title);
        wv = view.findViewById(R.id.wv);
        initView();
    }

    private void initView() {
        rl_back.setVisibility(View.INVISIBLE);
        tv_title.setText(R.string.meng_lesson);

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
//                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }
        });
        wv.loadUrl("https://www.baidu.com");
    }

    @Override
    public void onResume() {
        if ( wv != null ) {
            wv.onResume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if ( wv != null ) {
            wv.onPause();
        }
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if ( wv != null ) {
            wv.destroy();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if ( !hidden ){
            wv.loadUrl("https://www.baidu.com");
        }
    }
}
