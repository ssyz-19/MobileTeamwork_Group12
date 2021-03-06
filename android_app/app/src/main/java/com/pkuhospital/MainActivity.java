package com.pkuhospital;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pkuhospital.Fragment.AppointmentFragment;
import com.pkuhospital.Fragment.PatientInfoFragment;
import com.pkuhospital.Fragment.PerCenterFragment;
import com.pkuhospital.Fragment.RecordFragment;
import com.pkuhospital.Utils.GlobalVar;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";

    //UI Object
    private TextView txt_topbar;
    private TextView txt_appointment;
    private TextView txt_record;
    private TextView txt_info;
    private TextView txt_center;
    private FrameLayout center_content;

    //Fragment Object
    private PerCenterFragment fgPerCenter;
    private RecordFragment fgRecord;
    private PatientInfoFragment fgPatientInfo;
    private AppointmentFragment fgAppointment;

    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        fManager = getSupportFragmentManager();
        bindViews();
        txt_appointment.performClick();   //模拟一次点击，既进去后选择第一项
    }

    //UI组件初始化与事件绑定
    private void bindViews() {
        txt_topbar = findViewById(R.id.txt_topbar);
        txt_appointment = findViewById(R.id.txt_appointment);
        txt_record = findViewById(R.id.txt_record);
        txt_info = findViewById(R.id.txt_info);
        txt_center = findViewById(R.id.txt_center);
        center_content = findViewById(R.id.center_content);

        txt_appointment.setOnClickListener(this);
        txt_record.setOnClickListener(this);
        txt_info.setOnClickListener(this);
        txt_center.setOnClickListener(this);
    }

    //重置所有文本的选中状态
    private void setSelected(){
        txt_appointment.setSelected(false);
        txt_record.setSelected(false);
        txt_info.setSelected(false);
        txt_center.setSelected(false);
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fgAppointment != null)fragmentTransaction.hide(fgAppointment);
        if(fgRecord != null)fragmentTransaction.hide(fgRecord);
        if(fgPatientInfo != null)fragmentTransaction.hide(fgPatientInfo);
        if(fgPerCenter != null)fragmentTransaction.hide(fgPerCenter);
    }


    @Override
    public void onClick(View v) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        RelativeLayout topLabel = (RelativeLayout)findViewById(R.id.ly_top_bar);
        switch (v.getId()){
            case R.id.txt_appointment:
                setSelected();
                txt_appointment.setSelected(true);
                //这里暂时先这样吧
                topLabel.setVisibility(GONE);
//                txt_topbar.setVisibility(GONE);
//                txt_topbar.setText("PKU校医院预约挂号");
                if(!GlobalVar.isWhetherUserSignIn()){
                    Toast.makeText(MainActivity.this,"用户未登录",Toast.LENGTH_SHORT)
                            .show();
                }
                if(fgAppointment == null){
                    fgAppointment = new AppointmentFragment(MainActivity.this);
                    fTransaction.add(R.id.center_content,fgAppointment);
                }else{
                    fTransaction.show(fgAppointment);
                }
                break;
            case R.id.txt_record:
                setSelected();
                txt_record.setSelected(true);
                topLabel.setVisibility(View.VISIBLE);
                txt_topbar.setText("挂号记录");
                if(fgRecord == null){
                    fgRecord = new RecordFragment(MainActivity.this);
                    fTransaction.add(R.id.center_content,fgRecord);
                }else{
                    fgRecord.onResume(); //每次切换都重新判断登录的账号
                    fTransaction.show(fgRecord);
                }
                break;
            case R.id.txt_info:
                setSelected();
                txt_info.setSelected(true);
                topLabel.setVisibility(View.VISIBLE);
                txt_topbar.setText("就诊人");
                if(fgPatientInfo == null){
                    fgPatientInfo = new PatientInfoFragment("添加就诊人",MainActivity.this);
                    fTransaction.add(R.id.center_content,fgPatientInfo);
                }else{
                    fTransaction.show(fgPatientInfo);
                }
                break;
            case R.id.txt_center:
                setSelected();
                txt_center.setSelected(true);
                topLabel.setVisibility(View.VISIBLE);
                txt_topbar.setText("个人中心");
                if(fgPerCenter == null){
                    fgPerCenter = new PerCenterFragment(MainActivity.this);
                    fTransaction.add(R.id.center_content,fgPerCenter);
                }else{
                    fTransaction.show(fgPerCenter);
                }
                break;
        }
        fTransaction.commit();
    }
}