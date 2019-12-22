package com.pkuhospital.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
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


import com.google.gson.JsonIOException;
import com.pkuhospital.Db.Department;
import com.pkuhospital.Db.Doctor;
import com.pkuhospital.R;
import com.pkuhospital.Utils.GlobalVar;
import com.pkuhospital.Utils.HttpUtil;
import com.pkuhospital.Utils.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
    private static final int LEVEL_TIME = 2;
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
     * 选中的医生
     */
    private Doctor selectedDoctor;
    /**
     * 当前预约的阶段
     * 例如currentLevel = 0(LEVEL_DEPARTMENT)，则表明当前处于选择科室的阶段
     */
    private int currentLevel;

    /**
     * 处理获取可预约时间段的异步传输数据
     * @time 2019.12.19
     */
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            int what = msg.what;
            switch (what){
                case 0:
                    closeProgressDialog();
                    adapter.notifyDataSetChanged();
                    listView.setSelection(0);
                    currentLevel = LEVEL_TIME;
                    break;
                default:
                    break;
            }
        }
    };

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
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if(currentLevel == LEVEL_DEPARTMENT){
                    selectedDepartment = departmentList.get(position);
                    queryDoctors();
                }else if(currentLevel == LEVEL_DOCTOR){
                    selectedDoctor = doctorList.get(position);
                    queryDateInfo();
                }else if(currentLevel == LEVEL_TIME){
                    if(!GlobalVar.isWhetherUserSignIn()){
                        Toast.makeText(mContext,"尚未登录",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    AlertDialog.Builder logoutDialog = new AlertDialog.Builder(mContext);
                    logoutDialog.setTitle("提示");
                    logoutDialog.setMessage("是否确定预约该时间段？");
                    logoutDialog.setCancelable(false);

                    logoutDialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            /**
                             * 发送预约请求
                             */
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    final String timeStr = dataList.get(position);
                                    HttpUtil.postOkHttpRequest("confirm", GlobalVar.getServerUrl(),
                                            Integer.toString(selectedDoctor.getDoctorId()),timeStr, new Callback() {

                                                @Override
                                                public void onFailure(Call call, IOException e) {
                                                    Toast.makeText(mContext,"客户端请求错误或服务器错误",Toast.LENGTH_SHORT)
                                                            .show();
                                                }

                                                @Override
                                                public void onResponse(Call call, Response response) throws IOException {
                                                    String responseTxt = response.body().string();
                                                    final String result = Utility.handleConfirmResponse(responseTxt);
                                                    if("预约成功".equals(result)){
                                                        SharedPreferences.Editor editor = getActivity().getSharedPreferences(
                                                                GlobalVar.getStuId(),Context.MODE_PRIVATE
                                                        ).edit();
                                                        editor.putString("username",GlobalVar.getStuId());
                                                        editor.putString("doctor_name",selectedDoctor.getDoctorName());
                                                        editor.putString("time",timeStr);
                                                        editor.putBoolean("invalid",true);//用于判断该记录是否有效
                                                        editor.apply();
                                                    }
                                                    getActivity().runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(mContext,result,Toast.LENGTH_SHORT)
                                                                    .show();
                                                        }
                                                    });
                                                }
                                            });
                                }
                            }).start();
                        }
                    });

                    logoutDialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    logoutDialog.show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLevel == LEVEL_DOCTOR){
                    queryDepartments();
                }else if(currentLevel == LEVEL_TIME){
                    queryDoctors();
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
            currentLevel = LEVEL_DOCTOR;
        }else{
            String address = GlobalVar.getServerUrl()+'/'+selectedDepartment.getOfficeId();
            queryFromServer(address,"doctor");
        }
    }

    /**
     * 获取对应医生的可预约时间
     */
    private void queryDateInfo(){
        titleText.setText("医生:"+selectedDoctor.getDoctorName()+" 可选时间段:");
        showProgressDialog();
        HttpUtil.postOkHttpRequest("dateInfo", GlobalVar.getServerUrl(), "",
                String.valueOf(selectedDoctor.getDoctorId()), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),"error:获取预约时间失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseTxt = response.body().string();
                saveSelectableTime(responseTxt);
            }
        });
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
     * 显示可预约时间
     * @param response 获取到的json格式数据
     * @time 2019.12.19
     */
    private boolean saveSelectableTime(String response){
        /**
         * 直接在函数中解析json数据
         */
        if(!TextUtils.isEmpty(response)){
            try{
                JSONObject jsonObject = new JSONObject(response);
                JSONArray allSelectableTime = jsonObject.getJSONArray("result");
                dataList.clear();
                for(int i=0;i<allSelectableTime.length();i++){
                    dataList.add(allSelectableTime.getJSONObject(i).getString("time"));
                }
                handler.sendEmptyMessage(0);
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
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
