package com.pkuhospital.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.pkuhospital.R;
import com.pkuhospital.Utils.GlobalVar;
import com.pkuhospital.Utils.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 挂号记录页面对应的碎片
 * @author 杨洲
 * @time 2019.12.6
 */
public class RecordFragment extends Fragment {
    private static final String TAG = "RecordFragment";

    private Context mContext;
//    private List<Label> recordInfo = new LinkedList<>();//这里必须分配空间，不能是null

    private View view;
    private LinearLayout recordLayout;
    private TextView doctorNameText;
    private TextView doctorInfoText;
    private TextView appointTimeText;
    private Button cancelButton;

    public RecordFragment(Context context)
    {
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fg_record,container,false);
//        ListView listView = view.findViewById(R.id.record_list);
        recordLayout = view.findViewById(R.id.appointment_record);
        doctorNameText = view.findViewById(R.id.doctor_name);
        doctorInfoText = view.findViewById(R.id.doctor_info);
        appointTimeText = view.findViewById(R.id.appointment_time);
        cancelButton = view.findViewById(R.id.cancel_appoint);
//        if(recordInfo.size() > 0){
//            LabelAdapter mAdapter = new LabelAdapter((LinkedList<Label>)recordInfo,mContext);
//            listView.setAdapter(mAdapter);
//        }else{
//            view.findViewById(R.id.div_line).setVisibility(View.INVISIBLE);
//        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        cancelButton.setOnClickListener(new View.OnClickListener() {//发送取消预约请求
            @Override
            public void onClick(View v) {
                AlertDialog.Builder cancelDialog = new AlertDialog.Builder(mContext);
                cancelDialog.setTitle("提示");
                cancelDialog.setMessage("是否取消预约？");
                cancelDialog.setCancelable(false);

                cancelDialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HttpUtil.postOkHttpRequest("cancel", GlobalVar.getServerUrl(), "", "", new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext,"客户端发送取消预约请求失败",Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                });
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String responseTxt = response.body().string();
                                final String result;
                                try{
                                     result = new JSONObject(responseTxt).getString("status");
                                     if("成功取消预约".equals(result)){
                                         SharedPreferences.Editor editor = getActivity().getSharedPreferences(GlobalVar.getStuId(),
                                                 Context.MODE_PRIVATE).edit();
                                         editor.putBoolean("invalid",false);
                                         editor.apply();
                                     }
                                     getActivity().runOnUiThread(new Runnable() {
                                         @Override
                                         public void run() {
                                             Toast.makeText(mContext,result,Toast.LENGTH_SHORT)
                                                     .show();
                                             onResume();//刷新页面
                                         }
                                     });
                                }catch(JSONException e){
                                    e.printStackTrace();
                                }

                            }
                        });
                    }
                });

                cancelDialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                cancelDialog.show();
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        if(GlobalVar.isWhetherUserSignIn()){
            SharedPreferences pref = getActivity().getSharedPreferences(GlobalVar.getStuId(),Context.MODE_PRIVATE);
            boolean invalid = pref.getBoolean("invalid",false);
            if(invalid){
                String doctorName = pref.getString("doctor_name","");
                String doctorInfo = pref.getString("doctor_info","");
                String time = pref.getString("time","");

                recordLayout.setVisibility(View.VISIBLE);
                doctorNameText.setText(doctorName);
                doctorInfoText.setText(doctorInfo);
                appointTimeText.setText(time);
            }else{
                recordLayout.setVisibility(View.GONE);
            }
        }else{
            recordLayout.setVisibility(View.GONE);
            Toast.makeText(mContext,"用户未登录",Toast.LENGTH_SHORT).show();
        }
        super.onResume();
    }
}
