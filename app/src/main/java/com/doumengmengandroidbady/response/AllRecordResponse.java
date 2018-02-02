package com.doumengmengandroidbady.response;

import com.doumengmengandroidbady.response.entity.RecordResult;

/**
 * Created by Administrator on 2018/1/31.
 */

public class AllRecordResponse extends BaseResponse {

    private RecordResult result;

    public RecordResult getResult() {
        return result;
    }

    public void setResult(RecordResult result) {
        this.result = result;
    }

}
