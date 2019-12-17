package com.pkuhospital.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pkuhospital.Bean.Label;
import com.pkuhospital.Bean.LabelAdapter;
import com.pkuhospital.R;
import com.pkuhospital.Utils.GlobalVar;
import com.pkuhospital.Utils.HttpUtil;
import com.pkuhospital.Utils.Utility;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Response;
import okio.Utf8;

/**
 * 选择科室、医生和时间
 * @author 杨洲
 * @time 2019.12.7
 */
public class AppointmentFragment extends Fragment {
    private static final String TAG = "AppointmentFragment";

    private Context mContext;
    private Button backButton; //暂时用作测试按钮
    private TextView titleText;

    private List<Label> departmentInfo = new LinkedList<>();//这里必须分配空间，不能是null

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
        ListView listView = view.findViewById(R.id.department_list);
        backButton.setVisibility(View.VISIBLE);
        titleText.setText("选择科室");
        LabelAdapter mAdapter = new LabelAdapter((LinkedList<Label>)departmentInfo,mContext);
        listView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        HttpUtil.getDepartmentHttpRequest(GlobalVar.getServerUrl() + "/office");
                        HttpUtil.getDepartmentHttpRequest(GlobalVar.getServerUrl() + "/office", new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String responseTxt = response.body().string();
                                Utility.handleOfficeResponse(responseTxt);
                                Log.i(TAG, "onResponse: "+responseTxt);
//                                System.out.println(response.body().contentLength());
//                                System.out.println(response.body().contentType());
                            }
                        });
//                        HttpURLConnection connection = null;
//                        BufferedReader reader = null;
//                        try{
//                            URL url = new URL(GlobalVar.getServerUrl()+"/office");
//                            connection = (HttpURLConnection)url.openConnection();
//                            connection.setRequestMethod("GET");
//                            connection.setConnectTimeout(8000);
//                            InputStream in = connection.getInputStream();
//                            reader = new BufferedReader(new InputStreamReader(in));
////                            StringBuilder response = new StringBuilder();
//                            System.out.println(connection.getResponseCode());
//                            System.out.println(reader.readLine());
////                            Log.i(TAG, "run: "+reader.readLine());
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }finally {
//                            if(reader != null){
//                                try{
//                                    reader.close();
//                                }catch (Exception e){
//                                    e.printStackTrace();
//                                }
//                                if(connection != null){
//                                    connection.disconnect();
//                                }
//                            }
//                        }
                    }
                }).start();
            }
        });
        super.onActivityCreated(savedInstanceState);
    }
}
