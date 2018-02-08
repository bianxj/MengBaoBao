package com.doumengmengandroidbady.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.util.ZxingUtil;
import com.doumengmengandroidbady.view.AutoScrollViewPager;
import com.doumengmengandroidbady.view.GraphView;
import com.google.zxing.WriterException;

/**
 * Created by Administrator on 2017/12/5.
 */
public class GuideActivity extends BaseActivity {

    private AutoScrollViewPager asvp;
    private Button bt_guide_register , bt_guide_login;

    private GraphView d_weight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        if (BaseApplication.getInstance().hasAccountData()){
            Intent intent = new Intent(this,LoadingActivity.class);
            intent.putExtra(LoadingActivity.IN_PARAM_AUTO_LOGIN,true);
            startActivity(intent);
        } else {
            findView();
            initView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void findView(){
//        asvp = findViewById(R.id.asvp);
        bt_guide_register = findViewById(R.id.bt_guide_register);
        bt_guide_login = findViewById(R.id.bt_guide_login);
    }

    private void initView(){
        bt_guide_register.setOnClickListener(listener);
        bt_guide_login.setOnClickListener(listener);

        ImageView iv_qr = findViewById(R.id.iv_qr);

        try {
            Bitmap bitmap = ZxingUtil.getInstance().encodeQR("http://www.baidu.com/",getResources().getDimensionPixelOffset(R.dimen.x720px));
            iv_qr.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

//        int[] images = new int[]{R.drawable.v1,R.drawable.v2,R.drawable.v3};
//        asvp.setImageList(images);
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.bt_guide_register:
                    startActivity(RegisterActivity.class);
                    break;
                case R.id.bt_guide_login:
                    startActivity(LoginActivity.class);
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //截取返回键
        if ( keyCode == KeyEvent.KEYCODE_BACK ){
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
