package com.pkuhospital.Fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pkuhospital.Bean.Label;
import com.pkuhospital.Bean.LabelAdapter;
import com.pkuhospital.R;
import com.pkuhospital.SecondActivity;
import com.pkuhospital.Utils.GlobalVar;

import java.util.LinkedList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * @author 杨洲
 * 个人中心界面
 */
public class PerCenterFragment extends Fragment {
    private static final String TAG = "PerCenterFragment";
    private Context mContext;
    private Button registerButton;
    private ListView listView;
    private Intent intent;

    private List<Label> label = new LinkedList<>();//这里必须分配空间，不能是null

    private void initLabel(){
        label.add(new Label("关于我们",R.mipmap.tab_appointment_pressed));
        label.add(new Label("意见反馈",R.mipmap.tab_center_pressed));
        label.add(new Label("注销账号",R.mipmap.tab_info_pressed));
    }

    /**
     * constructor
     * @param context
     */
    public PerCenterFragment(Context context)
    {
        this.mContext = context;
        initLabel();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        intent = new Intent(mContext, SecondActivity.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content,container,false);
        registerButton = view.findViewById(R.id.button_register);
        listView = view.findViewById(R.id.personal_center_list);
        LabelAdapter mAdapter = new LabelAdapter((LinkedList<Label>)label,mContext);
        if(GlobalVar.isWhetherUserSignIn()){
            registerButton.setText("退出登录");
        }else{
            registerButton.setText("用户登录");
        }
        listView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState); //该语句的作用是？
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedContent = label.get(position).getLabelText();
                if(selectedContent.equals("关于我们")){
                    intent.putExtra("titleTxt","关于我们");
                    startActivity(intent);
                }
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GlobalVar.isWhetherUserSignIn()){
                    AlertDialog.Builder logoutDialog = new AlertDialog.Builder(mContext);
                    logoutDialog.setTitle("提示");
                    logoutDialog.setMessage("您是否要退出登录");
                    logoutDialog.setCancelable(false);
                    logoutDialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            GlobalVar.invertWhetherUserSignIn();  //更改登录状态
                            registerButton.setText("用户登录");
                        }
                    });
                    logoutDialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    logoutDialog.show();
                }else{
                    intent.putExtra("titleTxt","登录");
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onResume() {
        if(GlobalVar.isWhetherUserSignIn()){
            registerButton.setText("退出登录");
        }else{
            registerButton.setText("用户登录");
        }
        super.onResume();
    }
}
