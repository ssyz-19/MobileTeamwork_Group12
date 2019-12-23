package com.pkuhospital.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pkuhospital.R;
import com.pkuhospital.Utils.GlobalVar;
import com.pkuhospital.Utils.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
//    private Context mContext;

    private EditText editStuId;
    private EditText editPasswd;
    private Button loginButton;

    private String inputStuId;      //文本框中输入的学号
    private String inputPassword;   //文本框中输入的密码

    public LoginFragment() {
        // Required empty public constructor
    }

    //UI组件
    private void bindViews(View v){
        editStuId = v.findViewById(R.id.edit_stu_id);
        editPasswd = v.findViewById(R.id.edit_password);
        loginButton = v.findViewById(R.id.button_login);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_login,container,false);
        bindViews(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputStuId = editStuId.getText().toString();
                inputPassword = editPasswd.getText().toString();
                HttpUtil.postOkHttpRequest("login",GlobalVar.getServerUrl(),inputStuId,inputPassword,new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        loginFailure(2);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseText = response.body().string();
                        try {
                            JSONObject jsonObject = new JSONObject(responseText);
                            boolean status = jsonObject.getBoolean("status");
                            if(status&&!GlobalVar.isWhetherUserSignIn()){ //改为登录状态
                                GlobalVar.invertWhetherUserSignIn();
                                GlobalVar.setStuId(inputStuId);
                                getActivity().finish();
                            }else{
                                loginFailure(0);
                            }
                            Log.i(TAG, "登录状态："+status);
                        } catch (JSONException e) {
                            loginFailure(1);
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    private void loginFailure(final int errorCode){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (errorCode){
                    case 0:
                        Toast.makeText(getActivity(),"用户名或密码错误",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getActivity(),"输入不合法或与服务器断开链接",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getActivity(),"登陆失败",
                                Toast.LENGTH_SHORT).show();
                        break;
                    default:
                }
            }
        });
    }
}
