package com.jcodecraeer.xrecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/12/20.
 */

public abstract class BaseLoadMoreFooter extends LinearLayout {

    public final static int DO_NOTHING = -1;
    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;
    public final static int STATE_FAILED = 3;

    public int state = DO_NOTHING;

    public BaseLoadMoreFooter(Context context) {
        super(context);
    }

    public BaseLoadMoreFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    protected abstract void loading();
    protected abstract void loadingComplete();
    protected abstract void noMoreLoading();
    protected abstract void loadMoreFailed();

    public void fresh(){
        setState(state);
    }

    public void  setState(int state) {
        switch(state) {
            case STATE_LOADING:
                loading();
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_COMPLETE:
                loadingComplete();
                this.setVisibility(View.GONE);
                break;
            case STATE_NOMORE:
                noMoreLoading();
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_FAILED:
                loadMoreFailed();
                this.setVisibility(View.VISIBLE);
                break;
        }
        this.state = state;
    }

}
