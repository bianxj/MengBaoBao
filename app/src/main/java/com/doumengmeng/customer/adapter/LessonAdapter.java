package com.doumengmeng.customer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.activity.LessonActivity;
import com.doumengmeng.customer.net.UrlAddressList;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonHolder> {

    private List<LessonData> lessonDataList;

    public LessonAdapter(List<LessonData> lessonDataList) {
        this.lessonDataList = lessonDataList;
    }

    @Override
    public LessonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson,null);
        return new LessonHolder(view);
    }

    @Override
    public void onBindViewHolder(LessonHolder holder, int position) {
        holder.initView(lessonDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return lessonDataList.size();
    }

    public static class LessonHolder extends  RecyclerView.ViewHolder{

        private View view;
        private LessonData data;
        private FrameLayout fl;
        private ImageView img;
        private TextView tv_title,tv_content,tv_date;

        public LessonHolder(View itemView) {
            super(itemView);
            view = itemView;
            fl = itemView.findViewById(R.id.fl);
            img = itemView.findViewById(R.id.img);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_date = itemView.findViewById(R.id.tv_date);
            fl.setOnClickListener(listener);
        }

        private void initView(LessonData data){
            this.data = data;
            img.setImageBitmap(null);
            ImageLoader.getInstance().displayImage(data.getImgUrl(),img,buildOptions());
            tv_title.setText(data.getTitle());
            tv_content.setText(data.getContent());
            tv_date.setText(data.getDate());
        }

        private View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context,LessonActivity.class);
                intent.putExtra(LessonActivity.URL_TITLE,data.getContentUrl());
                context.startActivity(intent);
            }
        };

    }

    private static DisplayImageOptions buildOptions(){
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        builder.cacheOnDisk(true);
        builder.cacheInMemory(true);
        return builder.build();
    }


    public static class LessonData{
        private String imgUrl;
        private String title;
        private String content;
        private String date;
        private String contentUrl;

        public String getImgUrl() {
            return UrlAddressList.IMAGE_URL+imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContentUrl() {
            return UrlAddressList.IMAGE_URL+contentUrl;
        }

        public void setContentUrl(String contentUrl) {
            this.contentUrl = contentUrl;
        }

        public String getDate() {
            if (!TextUtils.isEmpty(date)){
                return date.split(" ")[0];
            }
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

}
