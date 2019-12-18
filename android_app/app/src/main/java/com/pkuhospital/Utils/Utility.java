package com.pkuhospital.Utils;

import android.text.TextUtils;
import android.util.Log;

import com.pkuhospital.Db.Department;
import com.pkuhospital.Db.Doctor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {
    private static final String TAG = "Utility";

    /**
     * 解析科室数据
     * @param response 服务器返回的json格式数据
     * @return 解析成功返回true，否则返回false
     */
    public static boolean handleOfficeResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONObject result = new JSONObject(response);
                JSONArray allOffices = result.getJSONArray("result");
                for(int i=0;i<allOffices.length();i++){
                   JSONObject departmentObject = allOffices.getJSONObject(i);
                   Department department = new Department();
                   department.setOfficeId(departmentObject.getString("office_id"));
                   department.setOfficeName(departmentObject.getString("office_name"));
                   department.save();
//                    Log.i(TAG, "handleOfficeResponse: "+department.getOfficeId());
//                    Log.i(TAG, "handleOfficeResponse: "+department.getOfficeName());
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleDoctorResponse(String response,String departmentId){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONObject result = new JSONObject(response);
                JSONArray allDoctors = result.getJSONArray("result");
                for(int i=0;i<allDoctors.length();i++) {
                    JSONObject doctorObject = allDoctors.getJSONObject(i);
                    Doctor doctor = new Doctor();
                    doctor.setDoctorName(doctorObject.getString("doctor_name"));
                    doctor.setDoctorInfo(doctorObject.getString("doctor_title"));
                    doctor.setDoctorId(doctorObject.getInt("id"));
                    doctor.setDepartmentId(departmentId);
                    Log.i(TAG, "handleDoctorResponse:"+doctor.getDepartmentId());
                    doctor.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }
}
