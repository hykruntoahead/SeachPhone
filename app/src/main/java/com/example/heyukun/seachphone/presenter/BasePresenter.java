package com.example.heyukun.seachphone.presenter;

import android.content.Context;

/**
 * Created by heyukun on 2017/8/16.
 */

public class BasePresenter {
    Context mContext;
    public void onAttach(Context context){
        mContext = context;
    }

    public void onStart(){}
    public void onResume(){}

    public void onDetach(){
        mContext = null;
    }

}
