package com.example.heyukun.seachphone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mPhoneEt;
    private Button mSearchBtn;
    private TextView mPhoneTv,mProvinceTv,mTypeTv,mCarrierTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
    }

    private void initWidgets() {
        mPhoneEt = (EditText) findViewById(R.id.input_phone);
        mSearchBtn = (Button) findViewById(R.id.btn_search);
        mSearchBtn.setOnClickListener(this);

        mPhoneTv = (TextView) findViewById(R.id.tv_phone);
        mProvinceTv = (TextView) findViewById(R.id.tv_province);
        mTypeTv = (TextView) findViewById(R.id.tv_type);
        mCarrierTv = (TextView) findViewById(R.id.tv_carrier);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getApplicationContext(),"点击查询",Toast.LENGTH_SHORT).show();

    }
}
