package com.pkuhospital.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.pkuhospital.Bean.Label;
import com.pkuhospital.Bean.LabelAdapter;
import com.pkuhospital.R;

import java.util.LinkedList;
import java.util.List;

/**
 * 挂号记录页面对应的碎片
 * @author 杨洲
 * @time 2019.12.6
 */
public class RecordFragment extends Fragment {
    private Context mContext;

    private List<Label> recordInfo = new LinkedList<>();//这里必须分配空间，不能是null

    public RecordFragment(Context context)
    {
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_record,container,false);
        ListView listView = view.findViewById(R.id.record_list);
        if(recordInfo.size() > 0){
            LabelAdapter mAdapter = new LabelAdapter((LinkedList<Label>)recordInfo,mContext);
            listView.setAdapter(mAdapter);
        }else{
            view.findViewById(R.id.div_line).setVisibility(View.INVISIBLE);
        }
        return view;
    }
}
