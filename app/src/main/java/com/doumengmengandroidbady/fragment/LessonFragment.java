package com.doumengmengandroidbady.fragment;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseFragment;
import com.doumengmengandroidbady.net.UrlAddressList;

/**
 * 作者: 边贤君
 * 描述: 萌课堂
 * 创建日期: 2018/1/8 14:06
 */
public class LessonFragment extends BaseFragment {

    private RelativeLayout rl_back;
    private TextView tv_title;
    private WebView wv;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
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
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if ( -1 == error.getErrorCode() ){
                        wv.loadUrl(UrlAddressList.URL_MENG_LESSION);
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        wv.loadUrl(UrlAddressList.URL_MENG_LESSION);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if ( wv != null ) {
            wv.destroy();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if ( !hidden ){
            wv.reload();
        }
    }
}
