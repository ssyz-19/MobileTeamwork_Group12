package com.pkuhospital.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

/**
 * @author 杨洲
 * 该碎片暂时放在主界面
 * 草稿碎片，后期可以再此基础上改动
 */
public class PerCenterFragment extends Fragment {
    private Context mContext;
    private Button registerButton;
    private ListView listView;

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
                    Intent intent = new Intent(mContext, SecondActivity.class);
                    startActivity(intent);
                }
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GlobalVar.isWhetherUserSignIn()){

                }else{

                }
            }
        });
    }
}
