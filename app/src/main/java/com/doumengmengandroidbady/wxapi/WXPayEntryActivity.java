package com.doumengmengandroidbady.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.doumengmengandroidbady.activity.DoctorInfoActivity;
import com.doumengmengandroidbady.config.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			Intent intent = new Intent(DoctorInfoActivity.PayBroadcastReceiver.ACTION_IWX_PAY_RESULT);
			intent.setPackage(getPackageName());
			intent.putExtra(DoctorInfoActivity.PayBroadcastReceiver.PARAM_RESULT_CODE,resp.errCode);
			sendBroadcast(intent);
			finish();
		}
	}
}