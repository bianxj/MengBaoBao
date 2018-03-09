package com.doumengmeng.doctor.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import java.lang.ref.WeakReference;


public class EditTextUtil {

    public static void scrollEditText(EditText editText){
        editText.setOnTouchListener(new CustomOnTouchLister(editText));
    }

    public static void restrictPointNumberEditText(EditText editText){
        editText.addTextChangedListener(new CostomTextWatcher(editText));
    }

    private static class CustomOnTouchLister implements View.OnTouchListener{

        private WeakReference<EditText> editText;

        public CustomOnTouchLister(EditText editText) {
            this.editText = new WeakReference<EditText>(editText);
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (canVerticalScroll(editText.get())) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    view.getParent().requestDisallowInterceptTouchEvent(false);
                }
            }
            return false;
        }

        /**
         * EditText竖直方向是否可以滚动
         * @param editText  需要判断的EditText
         * @return  true：可以滚动   false：不可以滚动
         */
        private boolean canVerticalScroll(EditText editText) {
            //滚动的距离
            int scrollY = editText.getScrollY();
            //控件内容的总高度
            int scrollRange = editText.getLayout().getHeight();
            //控件实际显示的高度
            int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() -editText.getCompoundPaddingBottom();
            //控件内容总高度与实际显示高度的差值
            int scrollDifference = scrollRange - scrollExtent;

            if(scrollDifference == 0) {
                return false;
            }

            return (scrollY > 0) || (scrollY < scrollDifference - 1);
        }

    }

    private static class CostomTextWatcher implements TextWatcher {

        private WeakReference<EditText> editText;

        public CostomTextWatcher(EditText editText) {
            this.editText = new WeakReference<EditText>(editText);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            int selectionStart = editText.get().getSelectionStart();
            int selectionEnd = editText.get().getSelectionEnd();
            if (!FormatCheckUtil.isOnlyPointNumber(editText.get().getText().toString()) && selectionStart > 0){
                //删除多余输入的字（不会显示出来）
                editable.delete(selectionStart - 1, selectionEnd);
                editText.get().setText(editable);
                editText.get().setSelection(editText.get().getText().toString().length());
            }
        }
    }

}
