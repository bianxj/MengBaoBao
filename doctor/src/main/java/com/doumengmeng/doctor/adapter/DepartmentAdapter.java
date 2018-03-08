package com.doumengmeng.doctor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.adapter.viewholder.InputContentHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Administrator on 2018/3/8.
 */

public class DepartmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int ITEM_DEPARTMENT = 0x01;
    private final static int ITEM_INPUT_DEPARTMENT = 0x02;

    private WeakReference<InputContentHolder.InputCompleteAction> weakReference;
    private List<DepartmentData> datas;

    public DepartmentAdapter(InputContentHolder.InputCompleteAction activity,List<DepartmentData> datas) {
        this.datas = datas;
        weakReference = new WeakReference<InputContentHolder.InputCompleteAction>(activity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if ( ITEM_DEPARTMENT == viewType ){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_department,null);
            return new DepartmentHolder(view,weakReference);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_input_view,null);
            return new InputContentHolder(view,weakReference);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if ( position < datas.size() ){
            ((DepartmentHolder)holder).initData(datas.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return datas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if ( datas.size() == position ){
            return ITEM_INPUT_DEPARTMENT;
        } else {
            return ITEM_DEPARTMENT;
        }
    }

    public static class DepartmentHolder extends RecyclerView.ViewHolder{

        private DisplayImageOptions options;
        private WeakReference<InputContentHolder.InputCompleteAction> weakReference;
        private DepartmentData data;
        private RelativeLayout rl_department;
        private TextView tv_department;

        public DepartmentHolder(View itemView, WeakReference<InputContentHolder.InputCompleteAction> weakReference) {
            super(itemView);
            this.weakReference = weakReference;

            rl_department = itemView.findViewById(R.id.rl_department);
            tv_department = itemView.findViewById(R.id.tv_department);
            rl_department.setOnClickListener(listener);
        }

        public void initData(DepartmentData data){
            this.data = data;
            tv_department.setText(data.getDepartmentName());
        }

        private View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weakReference.get().selectComplete(data.getDepartmentName());
            }
        };

    }

    public static class DepartmentData{
        private String departmentName;

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }
    }

}
