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

import java.util.LinkedList;
import java.util.List;

/**
 * @author 杨洲
 * 该碎片暂时放在主界面
 * 草稿碎片，后期可以再此基础上改动
 */
public class MyFragment extends Fragment {
    private String buttonContent;
    private Context mContext;

    private List<Label> mLabel = new LinkedList<>();//这里必须分配空间，不能是null

    private void initmLabel(){
        mLabel.add(new Label("关于我们",R.mipmap.tab_appointment_pressed));
        mLabel.add(new Label("意见反馈",R.mipmap.tab_center_pressed));
        mLabel.add(new Label("关于我们",R.mipmap.tab_info_pressed));
    }

    /**
     * 只有文字
     * @param content 显示在布局中心的文字
     * @param context
     */
    public MyFragment(String content,Context context)
    {
        initmLabel();
        this.buttonContent = content;
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content,container,false);
        Button button = view.findViewById(R.id.test_button);
        ListView listView = view.findViewById(R.id.personal_center_list);
        LabelAdapter mAdapter = new LabelAdapter((LinkedList<Label>)mLabel,mContext);
        button.setText(buttonContent);
        listView.setAdapter(mAdapter);
        return view;
    }

}
