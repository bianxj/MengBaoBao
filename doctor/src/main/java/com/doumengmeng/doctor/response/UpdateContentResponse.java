package com.doumengmeng.doctor.response;

import java.util.List;

/**
 * Created by Administrator on 2018/2/11.
 */

public class UpdateContentResponse extends BaseResponse {

    public Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result{
        public List<String> versionData;

        public List<String> getVersionData() {
            return versionData;
        }

        public void setVersionData(List<String> versionData) {
            this.versionData = versionData;
        }
    }

}
