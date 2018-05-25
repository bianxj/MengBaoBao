package com.doumengmeng.doctor.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.base.BaseActivity;
import com.doumengmeng.doctor.base.BaseApplication;
import com.doumengmeng.doctor.util.PictureUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/26.
 */

public class GuideActivity extends BaseActivity {

    private ViewPager vp;
    private List<ImageView> images;
    private Button bt_experience;
//    private Button bt_experience;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        findView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void findView(){
        bt_experience = findViewById(R.id.bt_experience);
        bt_experience.setOnClickListener(listener);

        vp = findViewById(R.id.vp);
        images = new ArrayList<>();
        images.add(createImageView(R.drawable.guide1));
        images.add(createImageView(R.drawable.guide2));
        images.add(createImageView(R.drawable.guide3));
        vp.setAdapter(new ScrollPagerAdapter(images));
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if ( position == 2 ){
                    bt_experience.setEnabled(true);
                } else {
                    bt_experience.setEnabled(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.bt_experience:
                    BaseApplication.getInstance().saveGuideState();
                    startActivity(LoginActivity.class);
                    finish();
                    break;
            }
        }
    };

    private ImageView createImageView(int imageId) {
        ImageView imageView = new ImageView(this);
        ViewGroup.LayoutParams params = null;
        imageView.setScaleType(ImageView.ScaleType.FIT_END);
        imageView.setBackgroundColor(getResources().getColor(R.color.first_white));
        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        Bitmap bitmap = null;
        try {
            bitmap = PictureUtils.loadBitmapFromResouce(this, imageId);
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageView;
    }



    private class ScrollPagerAdapter extends PagerAdapter{
        private List<ImageView> images;

        public ScrollPagerAdapter(List<ImageView> images) {
            this.images = images;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(images.get(position));
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(images.get(position));
            ImageView view = images.get(position);
            return view;
        }
    }

}
