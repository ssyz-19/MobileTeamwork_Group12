package com.pkuhospital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pkuhospital.Fragment.AboutUsFragment;
import com.pkuhospital.Fragment.LoginFragment;
import com.pkuhospital.Utils.GlobalVar;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "SecondActivity";
//    private TextView topBar;//该活动顶部显示的文本
    private FragmentManager fgManager;

    private TextView titleText;
    private Button backButton;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_second);
        intent = getIntent();
        titleText = findViewById(R.id.txt_topbar2);
        backButton = findViewById(R.id.button_cancel_login);
        String title = intent.getStringExtra("titleTxt");
        titleText.setText(title);
        fgManager = getSupportFragmentManager();
        FragmentTransaction fgTransaction = fgManager.beginTransaction();

        if(title.equals("关于我们")){
            AboutUsFragment fgAboutUs = new AboutUsFragment();
            fgTransaction.add(R.id.second_main_body,fgAboutUs);
        }else if(title.equals("登录")){
            LoginFragment fgLogin = new LoginFragment();
            fgTransaction.add(R.id.second_main_body,fgLogin);
        }

        fgTransaction.commit();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
