package com.doumengmeng.doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.activity.MainActivity;
import com.doumengmeng.doctor.view.SwipeLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<MessageData> datas;
    private List<ViewHolder> holders;

    public MessageAdapter(List<MessageData> datas) {
        holders = new ArrayList<>();
        if ( null == datas ){
            this.datas = new ArrayList<>();
        } else {
            this.datas = datas;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,null);
        ViewHolder holder = new ViewHolder(view,this);
        holders.add(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.initView(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private WeakReference<MessageAdapter> weakReference;
        private MessageData data;
        private SwipeLayout sl;
        private RelativeLayout rl_message;
        private ImageView iv_type;
        private TextView tv_message_content;
        private TextView tv_message_time;
        private TextView tv_delete;
        private TextView tv_unread;

        public ViewHolder(View itemView,MessageAdapter adapter) {
            super(itemView);
            sl = itemView.findViewById(R.id.sl);
            rl_message = itemView.findViewById(R.id.rl_message);
            iv_type = itemView.findViewById(R.id.iv_type);
            tv_message_content = itemView.findViewById(R.id.tv_message_content);
            tv_message_time = itemView.findViewById(R.id.tv_message_time);
            tv_delete = itemView.findViewById(R.id.tv_delete);
            tv_unread = itemView.findViewById(R.id.tv_unread);

            weakReference = new WeakReference<MessageAdapter>(adapter);
        }

        public void initView(MessageData data){
            this.data = data;
            int icon;
            if ( MESSAGE_TYPE.HOLIDAY == data.getMessageType() ){
                icon = R.drawable.icon_hoilday;
            } else if ( MESSAGE_TYPE.MAINTAIN == data.getMessageType() ){
                icon = R.drawable.icon_maintain;
            } else if ( MESSAGE_TYPE.OVER_TIME == data.getMessageType() ){
                icon = R.drawable.icon_over_time;
            } else {
                icon = R.drawable.icon_assessment;
            }
            iv_type.setImageResource(icon);
            tv_message_content.setText(data.getMessageTitle());
            tv_message_time.setText(data.getMessageDate());

            sl.setOnSwipeLayoutClick(onSwipeLayoutClick);
        }

        private SwipeLayout.OnSwipeLayoutClick onSwipeLayoutClick = new SwipeLayout.OnSwipeLayoutClick() {
            @Override
            public void onFrontClick(Context context,boolean isOpen) {
                if ( isOpen ){
                    close();
                } else {
                    goMessageDetailActivity();
                }
            }

            @Override
            public void onBackClick(Context context) {
                weakReference.get().removeMessage(data);
            }

            @Override
            public void onStatusChanged(boolean isOpen) {
            }

            @Override
            public void onTouch(Context context) {
                if ( !sl.isOpen() ) {
                    weakReference.get().closeOpenedView();
                }
            }
        };

        public boolean isOpen(){
            return sl.isOpen();
        }

        public void close(){
            sl.close();
        }

        private void goMessageDetailActivity(){
            ((MainActivity)sl.getContext()).switchFragment(MainActivity.PAGE_MESSAGE_DETAIL,data);
        }

    }

    public void removeMessage(MessageData data){
        datas.remove(data);
        notifyDataSetChanged();
    }

    public void closeOpenedView(){
        for (ViewHolder holder:holders){
            if ( holder.isOpen() ){
                holder.close();
            }
        }
    }

    public enum MESSAGE_TYPE{
        HOLIDAY,
        MAINTAIN,
        OVER_TIME,
        WAIT_ASSESSMENT
    }

    public static class MessageData{
        private MESSAGE_TYPE messageType;
        private String messageId;
        private String messageTitle;
        private String messageDesc;
        private String messageDate;

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        public MESSAGE_TYPE getMessageType() {
            return messageType;
        }

        public void setMessageType(MESSAGE_TYPE messageType) {
            this.messageType = messageType;
        }

        public String getMessageTitle() {
            return messageTitle;
        }

        public void setMessageTitle(String messageTitle) {
            this.messageTitle = messageTitle;
        }

        public String getMessageDesc() {
            return messageDesc;
        }

        public void setMessageDesc(String messageDesc) {
            this.messageDesc = messageDesc;
        }

        public String getMessageDate() {
            return messageDate;
        }

        public void setMessageDate(String messageDate) {
            this.messageDate = messageDate;
        }
    }

}
