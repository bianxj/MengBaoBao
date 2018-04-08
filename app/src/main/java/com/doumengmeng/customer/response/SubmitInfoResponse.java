package com.doumengmeng.customer.response;

import com.doumengmeng.customer.response.entity.DayList;
import com.doumengmeng.customer.response.entity.Growth;

import java.util.List;

/**
 * Created by Administrator on 2018/1/31.
 */

public class SubmitInfoResponse extends BaseResponse {

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result{
        private int isSaveUser;
        private DayList DayList;
        private List<Growth> GrowthList;

        public int getIsSaveUser() {
            return isSaveUser;
        }

        public void setIsSaveUser(int isSaveUser) {
            this.isSaveUser = isSaveUser;
        }

        public DayList getDayList() {
            return DayList;
        }

        public void setDayList(DayList dayList) {
            DayList = dayList;
        }

        public List<Growth> getGrowthList() {
            return GrowthList;
        }

        public void setGrowthList(List<Growth> growthList) {
            GrowthList = growthList;
        }
    }

}
