package com.pkuhospital.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.pkuhospital.Db.Department;
import com.pkuhospital.Db.Doctor;
import com.pkuhospital.R;
import com.pkuhospital.Utils.GlobalVar;
import com.pkuhospital.Utils.HttpUtil;
import com.pkuhospital.Utils.Utility;

import org.litepal.crud.DataSupport;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


import static android.view.View.GONE;

/**
 * 选择科室、医生和时间
 * @author yangzhou
 * @time 2019.12.7
 */
public class AppointmentFragment extends Fragment {
    private static final String TAG = "AppointmentFragment";

    private Context mContext;
    private static final int LEVEL_DEPARTMENT = 0;
    private static final int LEVEL_DOCTOR = 1;
    private ProgressDialog progressDialog;
    private Button backButton; //暂时用作测试按钮
    private ListView listView;
    private TextView titleText;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();
    /**
     * 科室列表
     */
    private List<Department> departmentList;
    /**
     * 医生列表
     */
    private List<Doctor> doctorList;
    /**
     * 选中的科室
     */
    private Department selectedDepartment;
    /**
     * 当前预约的阶段
     * 例如currentLevel = 0(LEVEL_DEPARTMENT)，则表明当前处于选择科室的阶段
     */
    private int currentLevel;

    public AppointmentFragment(Context context)
    {
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_appointment,container,false);
        backButton = view.findViewById(R.id.back_button);
        titleText = view.findViewById(R.id.title_text);
        listView = view.findViewById(R.id.department_list);
        adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentLevel == LEVEL_DEPARTMENT){
                    selectedDepartment = departmentList.get(position);
                    queryDoctors();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLevel == LEVEL_DOCTOR){
                    queryDepartments();
                }
            }
        });
        queryDepartments();
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 请求科室数据
     * @author yangzhou
     * @time 2019.12.18
     */
    private void queryDepartments(){
        titleText.setText("选择科室");
        backButton.setVisibility(GONE);
        departmentList = DataSupport.findAll(Department.class);
        if(departmentList.size()>0){
            dataList.clear();
            for(Department department:departmentList){
                dataList.add(department.getOfficeName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_DEPARTMENT;
        }else{
            String address = GlobalVar.getServerUrl()+"/office";
            queryFromServer(address,"department");
        }
    }

    /**
     * 请求医生数据
     * @author yangzhou
     * @time 2019.12.18
     */
    private void queryDoctors(){
        titleText.setText(selectedDepartment.getOfficeName()+":选择医生");
        backButton.setVisibility(View.VISIBLE);
        doctorList = DataSupport.where("departmentId = ?",String.valueOf(selectedDepartment.getOfficeId()))
                .find(Doctor.class);
        if(doctorList.size() > 0){
            dataList.clear();
            for(Doctor doctor:doctorList){
                dataList.add(doctor.getDoctorName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel =LEVEL_DOCTOR;
        }else{
            String address = GlobalVar.getServerUrl()+'/'+selectedDepartment.getOfficeId();
//            Log.i(TAG, "queryDoctors: "+address);
            queryFromServer(address,"doctor");
        }
    }
    /**
     * 数据库没有对应数据，从服务器请求数据
     * @param address url
     * @param type  请求数据的类型，department：请求科室数据 doctor：请求医生数据
     */
    private void queryFromServer(String address,final String type){
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseTxt = response.body().string();
                boolean result = false;
                if("department".equals(type)){
                    result = Utility.handleOfficeResponse(responseTxt);
                }else if("doctor".equals(type)){
                    result = Utility.handleDoctorResponse(responseTxt,
                            selectedDepartment.getOfficeId());
                }
                if(result){//如果返回的数据是正确的，更新UI
                    closeProgressDialog();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if("department".equals(type)){
                                queryDepartments();
                            }else if("doctor".equals(type)){
                                queryDoctors();
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }
}
