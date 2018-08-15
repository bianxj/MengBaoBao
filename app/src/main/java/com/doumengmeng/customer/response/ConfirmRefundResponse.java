package com.doumengmeng.customer.response;

import java.util.List;

public class ConfirmRefundResponse extends BaseResponse {

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result{
        private float totalAmount;
        private int recordtimes;
        private List<RefundItem> refundResult;

        public float getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(float totalAmount) {
            this.totalAmount = totalAmount;
        }

        public List<RefundItem> getRefundResult() {
            return refundResult;
        }

        public void setRefundResult(List<RefundItem> refundResult) {
            this.refundResult = refundResult;
        }

        public String getRecordtimes() {
            return recordtimes+"";
        }

        public void setRecordtimes(int recordtimes) {
            this.recordtimes = recordtimes;
        }
    }

    public static class RefundItem{
        private String refundMoney;
        private String doctorDetail;

        public String getRefundMoney() {
            return refundMoney;
        }

        public void setRefundMoney(String refundMoney) {
            this.refundMoney = refundMoney;
        }

        public String getDoctorDetail() {
            return doctorDetail;
        }

        public void setDoctorDetail(String doctorDetail) {
            this.doctorDetail = doctorDetail;
        }
    }

}
