package com.example.heyukun.seachphone;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heyukun.seachphone.model.PhoneModel;
import com.example.heyukun.seachphone.presenter.MainPresenterImpl;
import com.example.heyukun.seachphone.view.IMainView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,IMainView{
    private EditText mPhoneEt;
    private Button mSearchBtn;
    private TextView mPhoneTv,mProvinceTv,mTypeTv,mCarrierTv;
    private MainPresenterImpl mMainPreImpl;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        mMainPreImpl = new MainPresenterImpl(this);
        mMainPreImpl.onAttach(this);
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
//        Toast.makeText(getApplicationContext(),"点击查询",Toast.LENGTH_SHORT).show();
        mMainPreImpl.searchPhoneInfo(mPhoneEt.getText().toString());

    }

    @Override
    public void showLoading() {
        if(mProgressDialog ==null){
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setTitle("正在加载");
        }
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        mProgressDialog.hide();
    }

    @Override
    public void showToast(String toast) {
        Toast.makeText(getApplicationContext(),toast,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateView(PhoneModel phoneModel) {
        mPhoneTv.setText("手机号："+phoneModel.getTelString());
        mProvinceTv.setText("省份："+phoneModel.getProvince());
        mTypeTv.setText("运营商："+phoneModel.getCatName());
        mCarrierTv.setText("归属运营商："+phoneModel.getCarrier());
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mProgressDialog!=null){
            if(mProgressDialog.isShowing()){
                mProgressDialog.hide();
            }
            mProgressDialog.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPreImpl.onDetach();

    }
}
