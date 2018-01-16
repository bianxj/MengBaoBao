package com.doumengmengandroidbady.response;

import java.util.List;

/**
 * Created by Administrator on 2018/1/16.
 */

public class RecordResult {

    private List<Record> recordList;
    private List<ImageData> imgList;

    public List<Record> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
    }

    public List<ImageData> getImgList() {
        return imgList;
    }

    public void setImgList(List<ImageData> imgList) {
        this.imgList = imgList;
    }
}
