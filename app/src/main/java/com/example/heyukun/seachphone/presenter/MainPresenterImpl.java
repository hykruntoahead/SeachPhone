package com.example.heyukun.seachphone.presenter;

import com.example.heyukun.seachphone.config.Constant;
import com.example.heyukun.seachphone.model.PhoneModel;
import com.example.heyukun.seachphone.network.HttpUtil;
import com.example.heyukun.seachphone.view.IMainView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
        sendData(phoneNumber);
    }

    private void sendData(String phone) {
        Map<String,String> map = new HashMap<>();;
        map.put("tel",phone);
        HttpUtil httpUtil = new HttpUtil(new HttpUtil.HttpResponse() {
            @Override
            public void onSuccess(String responseResultStr) {
             int index = responseResultStr.indexOf("{");
                String json = responseResultStr.substring(index,responseResultStr.length());
                mainView.updateView(parseModelWithJsonStr(json));
                mainView.hideLoading();
            }

            @Override
            public void onFail(String error) {
               mainView.showToast(error);
               mainView.hideLoading();
            }
        });
        httpUtil.sendGetHttp(Constant.BASE_URL,map);
    }

    private PhoneModel parseModelWithJsonStr(String json){
        PhoneModel phoneModel = new PhoneModel();

        try {
            JSONObject jsonObj = new JSONObject(json);
            String value = jsonObj.optString("telString");
            phoneModel.setTelString(value);

            value = jsonObj.optString("province");
            phoneModel.setProvince(value);

            value = jsonObj.optString("catName");
            phoneModel.setCatName(value);

            value = jsonObj.optString("carrier");
            phoneModel.setCarrier(value);

        } catch (JSONException e) {
            e.printStackTrace();
        }
      return phoneModel;
    }



}
