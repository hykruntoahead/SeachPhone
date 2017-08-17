package com.example.heyukun.seachphone.network;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by heyukun on 2017/8/16.
 */

public class HttpUtil {
    String mUrl;
    Map<String, String> mParam;
    private OkHttpClient okhttpClient = new OkHttpClient();
    private HttpResponse mHttpResponse;
    //全局 主线程
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    //请求结果 回调接口
    public interface HttpResponse {
        void onSuccess(String responseResultStr);

        void onFail(String error);
    }

    public HttpUtil(HttpResponse httpResponse) {
        mHttpResponse = httpResponse;
    }

    //post 方式请求
    public void sendPostHttp(String url, Map<String, String> param) {
        sendHttp(url, param, true);
    }

    //get 方式请求
    public void sendGetHttp(String url, Map<String, String> param) {
        sendHttp(url, param, false);
    }

    private void sendHttp(String url, Map<String, String> param, boolean isPost) {
        mUrl = url;
        mParam = param;
        //编写http请求
        reqData(isPost);
    }

    private void reqData(boolean isPost) {
        Request request = createRequest(isPost);
        //创建请求队列
        okhttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (mHttpResponse != null) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mHttpResponse.onFail("请求失败");
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (mHttpResponse == null) return;
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!response.isSuccessful()) {
                            mHttpResponse.onFail("请求失败");
                        } else {
                            try {
                                mHttpResponse.onSuccess(response.body().string());
                            } catch (IOException e) {
                                mHttpResponse.onFail("结果转换失败");
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });

    }

    private Request createRequest(boolean isPost) {
        Request request;
        if (isPost) {
            MultipartBody.Builder builder
                    = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);

            //遍历map请求参数
            Iterator<Map.Entry<String, String>> iterator = mParam.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
            request = new Request.Builder().url(mUrl).post(builder.build()).build();
        } else {
            String urlStr = mUrl + "?" + mapParamToStr(mParam);
            request = new Request.Builder().url(urlStr).build();
        }
        return request;
    }

    private String mapParamToStr(Map<String, String> param) {
        StringBuilder stringBuild = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = param.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            stringBuild.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return stringBuild.toString().substring(0, stringBuild.length() - 1);
    }


}
