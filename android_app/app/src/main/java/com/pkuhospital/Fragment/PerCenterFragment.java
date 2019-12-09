package com.pkuhospital.Fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.pkuhospital.Bean.Label;
import com.pkuhospital.Bean.LabelAdapter;
import com.pkuhospital.R;
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

    private List<Label> Label = new LinkedList<>();//这里必须分配空间，不能是null

    private void initLabel(){
        Label.add(new Label("关于我们",R.mipmap.tab_appointment_pressed));
        Label.add(new Label("意见反馈",R.mipmap.tab_center_pressed));
        Label.add(new Label("注销账号",R.mipmap.tab_info_pressed));
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
        final Button button = view.findViewById(R.id.button_register);
        ListView listView = view.findViewById(R.id.personal_center_list);
        LabelAdapter mAdapter = new LabelAdapter((LinkedList<Label>)Label,mContext);
        if(GlobalVar.isWhetherUserSignIn()){
            button.setText("退出登录");
        }else{
            button.setText("用户登录");
        }
        listView.setAdapter(mAdapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GlobalVar.isWhetherUserSignIn()){

                }else{

                }
            }
        });
        return view;
    }
}
