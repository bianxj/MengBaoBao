package com.doumengmeng.doctor.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.doumengmeng.doctor.R;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/3/8.
 */

public class InputContentHolder extends RecyclerView.ViewHolder {
    private WeakReference<InputCompleteAction> action;
    private EditText et_content;
    private Button bt_commit;

    public InputContentHolder(View itemView,WeakReference<InputCompleteAction> action) {
        super(itemView);
        this.action = action;
        et_content = itemView.findViewById(R.id.et_content);
        bt_commit = itemView.findViewById(R.id.bt_commit);

        bt_commit.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if ( !TextUtils.isEmpty(et_content.getText().toString().trim()) ) {
                action.get().selectComplete(et_content.getText().toString().trim());
            } else {
                action.get().error(0);
            }
        }
    };

    public interface InputCompleteAction{
        public void selectComplete(String content);
        public void error(int type);
    }

}
