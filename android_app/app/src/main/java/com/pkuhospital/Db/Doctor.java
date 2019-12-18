package com.pkuhospital.Db;

import org.litepal.crud.DataSupport;

/**
 * 医生类
 * 包含医生姓名、医生信息、id
 * @author yangzhou
 */
public class Doctor extends DataSupport {
    private String doctorName;
    private String doctorInfo;
    private int doctorId;
    private String departmentId;

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorInfo(String doctorInfo) {
        this.doctorInfo = doctorInfo;
    }

    public String getDoctorInfo() {
        return doctorInfo;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentId() {
        return departmentId;
    }
}
