package com.doumengmeng.customer.entity;

import android.text.TextUtils;

import com.doumengmeng.customer.net.UrlAddressList;

/**
 * Created by Administrator on 2017/12/11.
 */

public class HospitalEntity {

    private String hospitalid;
    private String hospitalicon;
    private String hospitalname;
    private String hospitaladdress;

    public String getHospitalicon() {
        if (!TextUtils.isEmpty(hospitalicon)){
            return UrlAddressList.IMAGE_URL + hospitalicon;
        }
        return hospitalicon;
    }

    public void setHospitalicon(String hospitalicon) {
        this.hospitalicon = hospitalicon;
    }

    public String getHospitalname() {
        return hospitalname;
    }

    public void setHospitalname(String name) {
        this.hospitalname = name;
    }

    public String getHospitaladdress() {
        return hospitaladdress;
    }

    public void setHospitaladdress(String hospitaladdress) {
        this.hospitaladdress = hospitaladdress;
    }

    public String getHospitalid() {
        return hospitalid;
    }

    public void setHospitalid(String hospitalid) {
        this.hospitalid = hospitalid;
    }


}
