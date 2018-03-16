package com.doumengmeng.doctor.response;

/**
 * Created by Administrator on 2018/3/16.
 */

public class AccountResponse extends BaseResponse {

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result{
        public double monthPrice;
        public int purchaseThisMonth;
        public int monthEvaluation;
        public int accumulativePurchase;
        public int accumulativeEvaluation;
        public int allFail;

        public double getMonthPrice() {
            return monthPrice;
        }

        public void setMonthPrice(double monthPrice) {
            this.monthPrice = monthPrice;
        }

        public int getPurchaseThisMonth() {
            return purchaseThisMonth;
        }

        public void setPurchaseThisMonth(int purchaseThisMonth) {
            this.purchaseThisMonth = purchaseThisMonth;
        }

        public int getMonthEvaluation() {
            return monthEvaluation;
        }

        public void setMonthEvaluation(int monthEvaluation) {
            this.monthEvaluation = monthEvaluation;
        }

        public int getAccumulativePurchase() {
            return accumulativePurchase;
        }

        public void setAccumulativePurchase(int accumulativePurchase) {
            this.accumulativePurchase = accumulativePurchase;
        }

        public int getAccumulativeEvaluation() {
            return accumulativeEvaluation;
        }

        public void setAccumulativeEvaluation(int accumulativeEvaluation) {
            this.accumulativeEvaluation = accumulativeEvaluation;
        }

        public int getAllFail() {
            return allFail;
        }

        public void setAllFail(int allFail) {
            this.allFail = allFail;
        }
    }

}
