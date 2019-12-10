package com.pkuhospital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.pkuhospital.Fragment.AboutUsFragment;

public class SecondActivity extends AppCompatActivity {
//    private TextView topBar;//该活动顶部显示的文本
    private AboutUsFragment fgAboutUs;
    private FragmentManager fgManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_second);
        fgManager = getSupportFragmentManager();
        FragmentTransaction fgTransaction = fgManager.beginTransaction();
        fgAboutUs = new AboutUsFragment();
        fgTransaction.add(R.id.second_main_body,fgAboutUs);
        fgTransaction.commit();
    }
}
