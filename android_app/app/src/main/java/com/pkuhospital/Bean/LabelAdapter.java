package com.pkuhospital.Bean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pkuhospital.R;

import java.util.LinkedList;

/**
 * 个人中心界面的标签的adapter
 */
public class LabelAdapter extends BaseAdapter {
    private LinkedList<Label> mLabel;
    private Context mContext;

    public LabelAdapter(LinkedList<Label> labels,Context context){
        mLabel = labels;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mLabel.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.label_item,parent,false);
        ImageView img_label = convertView.findViewById(R.id.label_image);
        TextView txt_label = convertView.findViewById(R.id.txt_label);
        img_label.setBackgroundResource(mLabel.get(position).getLabelIcon());
        txt_label.setText(mLabel.get(position).getLabelText());
        return convertView;
    }
}

