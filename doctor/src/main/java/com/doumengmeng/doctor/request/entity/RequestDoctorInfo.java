package com.doumengmeng.doctor.request.entity;

/**
 * Created by Administrator on 2018/3/9.
 */

public class RequestDoctorInfo {

    private String doctorId;
    private DoctorInfo doctorInfo;

    public RequestDoctorInfo() {
        doctorInfo = new DoctorInfo();
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setDoctorName(String doctorName) {
        doctorInfo.setDoctorName(doctorName);
    }

    public void setHospitalName(String hospitalName) {
        doctorInfo.setHospitalName(hospitalName);
    }

    public void setDepartmentName(String departmentName) {
        doctorInfo.setDepartmentName(departmentName);
    }

    public void setPositionalTitles(String positionalTitles) {
        doctorInfo.setPositionalTitles(positionalTitles);
    }

    public void setCost(String cost) {
        doctorInfo.setCost(cost);
    }

    public void setSpeciality(String speciality) {
        doctorInfo.setSpeciality(speciality);
    }

    public void setDoctorDesc(String doctorDesc) {
        doctorInfo.setDoctorDesc(doctorDesc);
    }

    public void setDoctorCode(String doctorCode) {
        doctorInfo.setDoctorCode(doctorCode);
    }

    private static class DoctorInfo{
        private String doctorName;
        private String hospitalName;
        private String departmentName;
        private String positionalTitles;
        private String cost;
        private String speciality;
        private String doctorDesc;
        private String doctorCode;

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public void setHospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public void setPositionalTitles(String positionalTitles) {
            this.positionalTitles = positionalTitles;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public void setSpeciality(String speciality) {
            this.speciality = speciality;
        }

        public void setDoctorDesc(String doctorDesc) {
            this.doctorDesc = doctorDesc;
        }

        public void setDoctorCode(String doctorCode) {
            this.doctorCode = doctorCode;
        }
    }

}
