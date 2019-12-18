package com.pkuhospital.Db;

import org.litepal.crud.DataSupport;

/**
 * 科室类
 * 包括科室id和科室名称
 * @author yangzhou
 */
public class Department extends DataSupport {
    private String officeId;
    private String officeName;

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }
}
