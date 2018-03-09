package com.doumengmeng.doctor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */

public class MessageAdapter  extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<MessageData> datas;

    public MessageAdapter(List<MessageData> datas) {
        if ( null == datas ){
            this.datas = new ArrayList<>();
        } else {
            this.datas = datas;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,null);
        return new ViewHolder(view);
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

        private MessageData data;
        private RelativeLayout rl_message;
        private ImageView iv_type;
        private TextView tv_message_content;
        private TextView tv_message_time;
        private TextView tv_delete;

        public ViewHolder(View itemView) {
            super(itemView);
            rl_message = itemView.findViewById(R.id.rl_message);
            iv_type = itemView.findViewById(R.id.iv_type);
            tv_message_content = itemView.findViewById(R.id.tv_message_content);
            tv_message_time = itemView.findViewById(R.id.tv_message_time);
            tv_delete = itemView.findViewById(R.id.tv_delete);
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

            tv_delete.setOnClickListener(listener);
            rl_message.setOnTouchListener(touchListener);
        }

        private View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        };

        private View.OnTouchListener touchListener = new View.OnTouchListener() {
            float historyX = 0;
            boolean isMoved = false;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                System.out.println("motionEvent:"+motionEvent.getX());
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        historyX = motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        move(motionEvent.getX()-historyX);
                        historyX = motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        };

        private void move(float deltaX){
            float left = rl_message.getLeft() +deltaX ;
            System.out.println("deltaX:"+deltaX);
            System.out.println("Left:"+left + "----" + tv_delete.getWidth());
            if ( left > 0 ){
                rl_message.setLeft(0);
                rl_message.setRight(rl_message.getWidth());
            } else if ( Math.abs(left) > tv_delete.getWidth()  ){
                rl_message.setLeft(-1*tv_delete.getWidth());
                rl_message.setRight(rl_message.getWidth()-tv_delete.getWidth());
            } else {
                rl_message.setLeft((int) left);
                rl_message.setRight((int) (rl_message.getRight()+deltaX));
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
        private String messageTitle;
        private String messageDesc;
        private String messageDate;

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
