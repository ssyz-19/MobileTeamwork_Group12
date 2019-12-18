package com.pkuhospital.Utils;

import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    private static final String TAG = "HttpUtil";

    /**
     * 发送用户登录请求
     * @author 杨洲
     * @param address 服务器地址
     * @param username 用户名，即学号
     * @param password 登录密码
     * @param callback
     */
    public static void loginOkHttpRequest(String address,String username,String password,Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("username",username)
                .add("password",password)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

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
