package com.doumengmeng.customer.util;

import com.doumengmeng.customer.entity.DoctorEntity;

import java.util.Comparator;

/**
 * Created by Administrator on 2018/5/22.
 */

public class DoctorComparator implements Comparator<DoctorEntity> {
    @Override
    public int compare(DoctorEntity t1, DoctorEntity t2) {
        return Integer.parseInt(t1.getDoctororder()) - Integer.parseInt(t2.getDoctororder());
    }
}
