package com.example.heyukun.seachphone.presenter;

import com.example.heyukun.seachphone.view.IMainView;

/**
 * Created by heyukun on 2017/8/16.
 */

public class MainPresenterImpl extends BasePresenter{
    private IMainView mainView;

    public MainPresenterImpl(IMainView mainView){
        this.mainView = mainView;
    }

    public void searchPhoneInfo(String phoneNumber){
        if(phoneNumber.isEmpty()){
            mainView.showToast("手机号不能为空");
            return;
        }
        if(phoneNumber.length()!=11){
            mainView.showToast("号码格式有误");
            return;
        }
        mainView.showLoading();
        //写上http请求的处理逻辑
    }


}
