package com.doumengmengandroidbady.activity;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.util.MyDialog;

public class AgreementActivity extends BaseActivity {

    public static final String HIDE_BOTTOM = "hide_bottom";

    private RelativeLayout rl_bottom;
    private TextView tv_title;
    private RelativeLayout rl_back;
    private LinearLayout ll_agreement;
    private Button bt_agree;
    private CheckBox cb_agreement;

    private WebView wv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        findView();
        configView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( wv != null ){
            wv.destroy();
            wv = null;
        }
    }

    private void findView(){
        rl_bottom = findViewById(R.id.rl_bottom);
        tv_title = findViewById(R.id.tv_title);
        rl_back = findViewById(R.id.rl_back);
        ll_agreement = findViewById(R.id.ll_agreement);
        bt_agree = findViewById(R.id.bt_agree);
        cb_agreement = findViewById(R.id.cb_agreement);
        wv = findViewById(R.id.wv);
    }

    private void configView(){
        Intent intent = getIntent();
        if ( null != intent && intent.getBooleanExtra(HIDE_BOTTOM,false) ){
            rl_bottom.setVisibility(View.GONE);
        } else {
            bt_agree.setOnClickListener(listener);
            ll_agreement.setOnClickListener(listener);
        }
        tv_title.setText(R.string.agreement_name);
        rl_back.setOnClickListener(listener);
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
        wv.loadUrl("file:///android_asset/agreement.html");
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.ll_agreement:
                    clickAgreement();
                    break;
                case R.id.bt_agree:
                    agree();
                    break;
            }
        }
    };

    private void back(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    private void clickAgreement(){
        cb_agreement.setChecked(!cb_agreement.isChecked());
    }

    private void agree(){
        if ( cb_agreement.isChecked() ) {
            setResult(Activity.RESULT_OK);
            finish();
        } else {
            MyDialog.showPromptDialog(this,getString(R.string.dialog_content_read_agreement),null);
        }
    }

}
