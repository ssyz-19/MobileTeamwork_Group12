package com.pkuhospital.Utils;

import android.text.TextUtils;
import android.util.Log;

import com.pkuhospital.Db.Department;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {
    private static final String TAG = "Utility";
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
}
