package com.doumengmeng.doctor.response;

import com.doumengmeng.doctor.response.entity.AssessmentItem;

import java.util.List;

/**
 * Created by Administrator on 2018/3/13.
 */

public class AssessmentListResponse extends BaseResponse {

    private Result result;

    public static class Result{
        private int hasEvaluation;
        private List<AssessmentItem> userRecordList;

        public int getHasEvaluation() {
            return hasEvaluation;
        }

        public void setHasEvaluation(int hasEvaluation) {
            this.hasEvaluation = hasEvaluation;
        }

        public List<AssessmentItem> getUserRecordList() {
            return userRecordList;
        }

        public void setUserRecordList(List<AssessmentItem> userRecordList) {
            this.userRecordList = userRecordList;
        }
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
