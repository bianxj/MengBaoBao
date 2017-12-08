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
import android.widget.Button;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseFragment;

/**
 * Created by Administrator on 2017/12/7.
 */

public class LessonFragment extends BaseFragment {

    private Button bt_back;
    private TextView tv_title;
    private WebView wv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson, null);
        findView(view);configView();
        return view;
    }

    private void findView(View view) {
        bt_back = view.findViewById(R.id.bt_back);
        tv_title = view.findViewById(R.id.tv_title);
        wv = view.findViewById(R.id.wv);
    }

    private void configView() {
        bt_back.setVisibility(View.INVISIBLE);
        tv_title.setText(R.string.meng_lesson);

        initWebView();
    }

    private void initWebView(){
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setLoadsImagesAutomatically(true);
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
    }

    @Override
    public void onResume() {
        if ( wv != null ) {
            wv.onResume();
            wv.resumeTimers();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if ( wv != null ) {
            wv.onPause();
            wv.pauseTimers();
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
}
