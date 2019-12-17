package com.pkuhospital.Db;

/**
 * 科室类
 * 包括科室id和科室名称
 * @author yangzhou
 */
public class Department{
    private String officeId;
    private String officeName;

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getOfficeName() {
        return officeName;
    }
}
