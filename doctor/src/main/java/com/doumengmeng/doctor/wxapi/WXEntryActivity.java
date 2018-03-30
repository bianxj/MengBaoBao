package com.doumengmeng.doctor.wxapi;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.doumengmeng.doctor.base.BaseActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2018/3/30.
 */

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private final String APP_ID= "wx82018bdb470014f2";
    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, APP_ID, false);
        api.registerApp(APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
//        switch(baseResp.errCode){
//            case BaseResp.ErrCode.ERR_OK:
//                Toast.makeText(WXEntryActivity.this,"ERR_OK",Toast.LENGTH_SHORT).show();
//                break;
//            case BaseResp.ErrCode.ERR_USER_CANCEL:
//                Toast.makeText(WXEntryActivity.this,"ERR_USER_CANCEL",Toast.LENGTH_SHORT).show();
//                break;
//            case BaseResp.ErrCode.ERR_AUTH_DENIED:
//                Toast.makeText(WXEntryActivity.this,"ERR_AUTH_DENIED",Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                Toast.makeText(WXEntryActivity.this,"UN_KNOW",Toast.LENGTH_SHORT).show();
//                break;
//        }
        finish();
    }
}
