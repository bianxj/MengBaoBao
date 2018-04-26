package com.doumengmeng.doctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.activity.AssessmentActivity;
import com.doumengmeng.doctor.base.BaseApplication;
import com.doumengmeng.doctor.response.entity.AssessmentItem;
import com.doumengmeng.doctor.util.FormulaUtil;
import com.doumengmeng.doctor.util.GsonUtil;
import com.doumengmeng.doctor.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.ref.WeakReference;
import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.ViewHolder> {

    private List<AssessmentItem> items;

    public AssessmentAdapter(List<AssessmentItem> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assessment,null);
        return new ViewHolder(parent.getContext(),view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.initView(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private WeakReference<Context> weakReference;
        private AssessmentItem item;
        private RelativeLayout rl_assessment;
        private CircleImageView civ_head;
        private TextView tv_month_age,tv_over_time,tv_accessment_time;

        public ViewHolder(Context context,View itemView) {
            super(itemView);
            weakReference = new WeakReference<Context>(context);
            rl_assessment = itemView.findViewById(R.id.rl_assessment);
            civ_head = itemView.findViewById(R.id.civ_head);
            tv_month_age = itemView.findViewById(R.id.tv_month_age);
            tv_over_time = itemView.findViewById(R.id.tv_over_time);
            tv_accessment_time = itemView.findViewById(R.id.tv_accessment_time);
            rl_assessment.setOnClickListener(listener);
        }

        public void initView(AssessmentItem item){
            this.item = item;
            ImageLoader.getInstance().displayImage(item.getHeadImgUrl(),civ_head);
            tv_month_age.setText(String.format(weakReference.get().getString(R.string.format_month_age),Integer.parseInt(item.getMonthAge()),Integer.parseInt(item.getMonthDay())));
            tv_accessment_time.setText(item.getRecordTime().split(" ")[0].replace("-","."));
            tv_over_time.setText(FormulaUtil.getTimeDifference(item.getValidityTime()));
        }

        private View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(weakReference.get(), AssessmentActivity.class);
                intent.putExtra(AssessmentActivity.IN_PARAM_ASSESSMENT_ITEM, GsonUtil.getInstance().toJson(item));
                weakReference.get().startActivity(intent);
            }
        };

    }

    private void loadHeadImg(ImageView imageView,boolean isMale, String urlHeadImg){
        DisplayImageOptions.Builder builder = BaseApplication.getInstance().defaultDisplayImage();
        if ( isMale ){
            builder.showImageOnLoading(R.drawable.default_icon_boy);
            builder.showImageForEmptyUri(R.drawable.default_icon_boy);
            builder.showImageOnFail(R.drawable.default_icon_boy);
        } else {
            builder.showImageOnLoading(R.drawable.default_icon_girl);
            builder.showImageForEmptyUri(R.drawable.default_icon_girl);
            builder.showImageOnFail(R.drawable.default_icon_girl);
        }
        ImageLoader.getInstance().displayImage(urlHeadImg,imageView,builder.build());
    }

}
