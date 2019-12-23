package com.pkuhospital.Utils;

import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    private static final String TAG = "HttpUtil";

    /**
     * post用户登录请求、获取可预约时间等
     * 这个后面可以改一下，把post的字段以json格式数据一起传进去然后解析
     * @author yangzhou
     * @param type login：登录请求 dateInfo:获取预约时间段 confirm:发送预约请求 cancel:取消预约
     * @param address 服务器地址
     * @param arg1 type:login 用户名，即学号   type：confirm 想要预约的医生的id
     * @param arg2 type:login 表示登录密码 type:dateInfo 表示医生id type：confirm 表示预约时间段
     * @param callback
     */
    public static void postOkHttpRequest(String type,String address,String arg1,String arg2,Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().build();
        if("login".equals(type)) {
            address = address + "/login";
            requestBody = new FormBody.Builder()
                    .add("username", arg1)
                    .add("password", arg2)
                    .build();
        }else if("dateInfo".equals(type)){
            address = address + "/dateInfo";
            requestBody = new FormBody.Builder()
                    .add("id",arg2)
                    .build();
        }else if("confirm".equals(type)){
            if(TextUtils.isEmpty(GlobalVar.getStuId())){
                Log.w(TAG, "postOkHttpRequest: 用户未登录");
                return;
            }
            else {
                address = address + "/confirm";
                requestBody = new FormBody.Builder()
                        .add("username", GlobalVar.getStuId())
                        .add("doctorId", arg1)
                        .add("date", arg2)
                        .build();
            }
        }else if("cancel".equals(type)){
            address = address + "/cancel";
            requestBody = new FormBody.Builder()
                    .add("username", GlobalVar.getStuId())
                    .build();
        }
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 用于请求科室或医生数据
     * @author yangzhou
     * @time 2019.12.19
     * @param address
     * @param callback
     */
    public static void sendOkHttpRequest(String address,Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
//                .header("Connection","close")
//                .header("Content-Length","100")
//                .header("Content-Type","application/json;charset=UTF-8")
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

}
