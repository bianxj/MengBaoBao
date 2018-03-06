package com.doumengmeng.doctor.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;


public class EditTextUtil {
    private final EditText editText;

    public EditTextUtil(EditText editText) {
        this.editText = editText;
        editText.addTextChangedListener(watcher);
    }

    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            int selectionStart = editText.getSelectionStart();
            int selectionEnd = editText.getSelectionEnd();
            if (!FormatCheckUtil.isOnlyPointNumber(editText.getText().toString()) && selectionStart > 0){
                //删除多余输入的字（不会显示出来）
                editable.delete(selectionStart - 1, selectionEnd);
                editText.setText(editable);
                editText.setSelection(editText.getText().toString().length());
//                //隐藏软键盘
//                InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                if ( imm != null ) {
//                    imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
//                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
//                }
            }
        }
    };

}
