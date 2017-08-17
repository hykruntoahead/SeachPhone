package com.example.heyukun.seachphone.view;

import com.example.heyukun.seachphone.model.PhoneModel;
import com.example.heyukun.seachphone.presenter.MainPresenterImpl;

/**
 * Created by heyukun on 2017/8/16.
 */

public interface IMainView extends IBaseView{
    void showToast(String toast);
    void updateView(PhoneModel phoneModel);
}
