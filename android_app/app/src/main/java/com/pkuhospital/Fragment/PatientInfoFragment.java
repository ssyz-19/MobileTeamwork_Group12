package com.pkuhospital.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.pkuhospital.Bean.Label;
import com.pkuhospital.Bean.LabelAdapter;
import com.pkuhospital.R;

import java.util.LinkedList;
import java.util.List;

/**
 * 添加就诊人信息碎片
 * @author 杨洲
 * @time 2019.12.6
 */
public class PatientInfoFragment extends Fragment {
    private String buttonContent;
    private Context mContext;

    private List<Label> patientInfo = new LinkedList<>();//这里必须分配空间，不能是null

    public PatientInfoFragment(String content,Context context)
    {
        this.buttonContent = content;
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_patient_info,container,false);
        Button button = view.findViewById(R.id.button_add_patient);
        ListView listView = view.findViewById(R.id.patient_info_list);
        LabelAdapter mAdapter = new LabelAdapter((LinkedList<Label>)patientInfo,mContext);
        button.setText(buttonContent);
        listView.setAdapter(mAdapter);
        return view;
    }
}
