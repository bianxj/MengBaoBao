package com.doumengmengandroidbady.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.util.MyDialog;
import com.doumengmengandroidbady.util.PictureUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 */

public class PictureAdapter extends BaseAdapter {

    private final Context context;
    private final List<UploadPicture> pictures;
    private final TackPictureCallBack callBack;

    public PictureAdapter(Context context,TackPictureCallBack callBack) {
        pictures = new ArrayList<>();
        this.context = context;
        this.callBack = callBack;
        BaseApplication.getInstance().clearUploadPicture();
    }

    @Override
    public int getCount() {
        if ( pictures.size() < 6 ){
            return pictures.size() +1;
        }
        return pictures.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if ( view == null ){
            view = LayoutInflater.from(context).inflate(R.layout.item_upload_picture,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if ( pictures.size() < 6 ){
            if ( position == 0 ){
                holder.iv_delete.setVisibility(View.GONE);
                holder.iv_picture.setImageResource(R.drawable.icon_add_picture);
            } else {
                UploadPicture picture = pictures.get(position-1);
                holder.iv_picture.setImageBitmap(picture.getSmallBitmap());
                holder.iv_delete.setVisibility(View.VISIBLE);
            }
            holder.iv_picture.setTag(position-1);
            holder.iv_delete.setTag(position-1);
        } else {
            UploadPicture picture = pictures.get(position);
            holder.iv_picture.setImageBitmap(picture.getSmallBitmap());
            holder.iv_delete.setVisibility(View.VISIBLE);

            holder.iv_picture.setTag(position);
            holder.iv_delete.setTag(position);
        }

        return view;
    }

    private class ViewHolder{
        public ImageView iv_picture = null;
        public ImageView iv_delete = null;
        public ViewHolder(View view) {
            iv_picture = view.findViewById(R.id.iv_picture);
            iv_delete = view.findViewById(R.id.iv_delete);
            iv_picture.setOnClickListener(listener);
            iv_delete.setOnClickListener(listener);
        }

        private final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position;
                switch (view.getId()){
                    case R.id.iv_picture:
                        position = (int) view.getTag();
                        if ( pictures.size() < 6 && position == -1 ){
                            callBack.tackPicture();
                        } else {
                            //TODO 显示照片
                            MyDialog.showPictureDialog(context,pictures.get(position).smallBitmap);
                        }
                        break;
                    case R.id.iv_delete:
                        position = (int) view.getTag();
                        removePicture(position);
                        break;
                }
            }
        };

    }

    public List<UploadPicture> getPictures(){
        return pictures;
    }

    public void addPicture(String picturePath){
        if ( pictures.size() >= 6 ){
            return;
        }
        UploadPicture picture = new UploadPicture();
        picture.setPicturePath(picturePath);
        pictures.add(picture);
        notifyDataSetChanged();
    }

    private void removePicture(int position){
        UploadPicture picture = pictures.get(position);
        pictures.remove(picture);
        notifyDataSetChanged();
        picture.clear();
    }

    public static class UploadPicture{
        private Bitmap smallBitmap;
        private String picturePath;

        public Bitmap getSmallBitmap() {
            if ( smallBitmap == null ){
                DisplayMetrics display = BaseApplication.getInstance().getDisplayInfo();
                smallBitmap = PictureUtils.getSmallBitmap(picturePath,display.widthPixels,display.heightPixels);
            }
            return smallBitmap;
        }

        public String getPicturePath() {
            return picturePath;
        }

        public void setPicturePath(String picturePath) {
            this.picturePath = picturePath;
        }

        public void clear(){
            if ( smallBitmap != null ){
                smallBitmap.recycle();
            }
        }
    }

    public interface TackPictureCallBack{
        void tackPicture();
    }

}
