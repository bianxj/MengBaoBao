package com.doumengmeng.doctor.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.response.AssessmentDetailResponse;
import com.doumengmeng.doctor.util.MyDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 */

public class PictureAdapter extends BaseAdapter {

    private List<AssessmentDetailResponse.ReportImage> images;

    public PictureAdapter(List<AssessmentDetailResponse.ReportImage> images) {
        if ( images == null ) {
            this.images = new ArrayList<>();
        } else {
            this.images = images;
        }
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return images.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if ( view == null ){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_picture,null);
        }
        AssessmentDetailResponse.ReportImage image = images.get(i);
        ImageView iv_picture = view.findViewById(R.id.iv_picture);
        ImageLoader.getInstance().displayImage(image.getImgurl(),iv_picture);
        iv_picture.setOnClickListener(listener);
        iv_picture.setTag(image.getImgurl());
        return view;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MyDialog.showPictureDialog(view.getContext(), (String) view.getTag());
        }
    };

}
