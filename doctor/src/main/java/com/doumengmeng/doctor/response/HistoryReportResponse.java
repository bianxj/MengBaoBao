package com.doumengmeng.doctor.response;

import com.doumengmeng.doctor.response.entity.HistoryReport;
import com.doumengmeng.doctor.response.entity.ImageData;

import java.util.List;

/**
 * Created by Administrator on 2018/1/31.
 */

public class HistoryReportResponse extends BaseResponse {

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {

        private List<HistoryReport> recordList;
        private List<ImageData> imgList;

        public List<HistoryReport> getRecordList() {
            return recordList;
        }

        public void setRecordList(List<HistoryReport> recordList) {
            this.recordList = recordList;
        }

        public List<ImageData> getImgList() {
            return imgList;
        }

        public void setImgList(List<ImageData> imgList) {
            this.imgList = imgList;
        }
    }

}
