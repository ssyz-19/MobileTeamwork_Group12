package com.pkuhospital.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pkuhospital.Bean.Label;
import com.pkuhospital.Bean.LabelAdapter;
import com.pkuhospital.R;

import java.util.LinkedList;
import java.util.List;

/**
 * 选择科室、医生和时间
 * @author 杨洲
 * @time 2019.12.7
 */
public class AppointmentFragment extends Fragment {
    private Context mContext;

    private List<Label> departmentInfo = new LinkedList<>();//这里必须分配空间，不能是null

    public AppointmentFragment(Context context)
    {
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_appointment,container,false);
        Button backButton = view.findViewById(R.id.back_button);
        TextView titleText = view.findViewById(R.id.title_text);
        ListView listView = view.findViewById(R.id.department_list);
        backButton.setVisibility(View.INVISIBLE);
        titleText.setText("选择科室");
        LabelAdapter mAdapter = new LabelAdapter((LinkedList<Label>)departmentInfo,mContext);
        listView.setAdapter(mAdapter);
        return view;
    }
}
