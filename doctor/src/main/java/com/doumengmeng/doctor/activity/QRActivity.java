package com.doumengmeng.doctor.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.base.BaseActivity;
import com.doumengmeng.doctor.base.BaseApplication;
import com.doumengmeng.doctor.db.DaoManager;
import com.doumengmeng.doctor.response.entity.UserData;
import com.doumengmeng.doctor.util.MyDialog;
import com.doumengmeng.doctor.util.PictureUtils;
import com.doumengmeng.doctor.util.ZxingUtil;
import com.doumengmeng.doctor.view.CircleImageView;
import com.google.zxing.WriterException;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2018/3/1.
 */

public class QRActivity extends BaseActivity {

    private RelativeLayout rl_back,rl_share;
    private TextView tv_title;

    private TextView tv_name,tv_positional_titles,tv_hospital_name,tv_department;
    private CircleImageView civ_head;

    private LinearLayout ll_qr_content;
    private ImageView iv_qr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        findView();
    }

    private void findView(){
        initTitle();
        initBaseInfo();
        initQR();
    }

    private void initTitle(){
        rl_back =findViewById(R.id.rl_back);
        rl_share =findViewById(R.id.rl_share);
        tv_title = findViewById(R.id.tv_title);

        tv_title.setText(R.string.qr_card);
        rl_back.setOnClickListener(listener);
        rl_share.setOnClickListener(listener);
    }

    private void initBaseInfo(){
        tv_name = findViewById(R.id.tv_name);
        tv_positional_titles = findViewById(R.id.tv_positional_titles);
        tv_hospital_name = findViewById(R.id.tv_hospital_name);
        tv_department = findViewById(R.id.tv_department);
        civ_head = findViewById(R.id.civ_head);

        UserData userData = BaseApplication.getInstance().getUserData();

        tv_name.setText(userData.getDoctorName());
        tv_positional_titles.setText(userData.getPositionalTitles());
        tv_hospital_name.setText(DaoManager.getInstance().getHospitalDao().searchHospitalNameById(this,userData.getHospitalId()));
        tv_department.setText(userData.getDepartmentName());
        ImageLoader.getInstance().displayImage(userData.getHeadimg(),civ_head);
    }

    private void initQR(){
        ll_qr_content = findViewById(R.id.ll_qr_content);
        iv_qr = findViewById(R.id.iv_qr);

        try {
            Bitmap bitmap = ZxingUtil.getInstance().encodeQR("http://www.baidu.com/",getResources().getDimensionPixelOffset(R.dimen.x405px));
            iv_qr.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.rl_share:
                    share();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

    private void share(){
        //TODO
        MyDialog.showShareDialog(this, getShareBitmap(ll_qr_content), new MyDialog.ShareCallBack() {
            @Override
            public void sharedToFriend(Bitmap bitmap) {

            }

            @Override
            public void shareToWeixin(Bitmap bitmap) {

            }
        });
    }

    private Bitmap getShareBitmap(View view) {
        //获取自定义view图片的大小
        Bitmap tempBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //使用Canvas，调用自定义view控件的onDraw方法，绘制图片
        Canvas canvas = new Canvas(tempBitmap);
        view.draw(canvas);
        return tempBitmap;
    }

    private void shareToFriend(Bitmap bitmap){
        share(bitmap,false);
    }

    private void shareToWeixin(Bitmap bitmap){
        share(bitmap,false);
    }

    private void share(Bitmap bitmap , boolean isFriend){
        WXImageObject imgObj = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap,300,300,true);
        bitmap.recycle();
        msg.thumbData = PictureUtils.convertBitmapToByte(thumbBmp);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = isFriend?SendMessageToWX.Req.WXSceneTimeline:SendMessageToWX.Req.WXSceneSession;
        getIWXAPI().sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private IWXAPI api;
    private final static String APP_ID = "";
    private IWXAPI getIWXAPI(){
        if ( api == null ){
            api = WXAPIFactory.createWXAPI(this,APP_ID,true);
            api.registerApp(APP_ID);
        }
        return api;
    }

}
