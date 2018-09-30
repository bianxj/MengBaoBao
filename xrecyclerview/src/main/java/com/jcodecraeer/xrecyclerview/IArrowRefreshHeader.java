package com.jcodecraeer.xrecyclerview;

public interface IArrowRefreshHeader extends BaseRefreshHeader {

    public void setProgressStyle(int style);
    public void setArrowImageView(int resid);
    public void setState(int state);
    public int getState();
    public void setVisibleHeight(int height);
    public int getVisibleHeight();
    public void reset();
}
