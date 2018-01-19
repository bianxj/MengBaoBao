package com.doumengmengandroidbady.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


public class EditTextUtil {
    private int selectionStart;
    private int selectionEnd;
    private EditText editText;

    public EditTextUtil(EditText editText) {
        this.editText = editText;
        editText.addTextChangedListener(watcher);
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            selectionStart = editText.getSelectionStart();
            selectionEnd = editText.getSelectionEnd();
            if (!FormatCheckUtil.isOnlyPointNumber(editText.getText().toString())){
                //删除多余输入的字（不会显示出来）
                editable.delete(selectionStart - 1, selectionEnd);
                editText.setText(editable);
                //隐藏软键盘
                InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText,InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(editText.getWindowToken(),0);
            }
        }
    };

}
