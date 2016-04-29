package com.jhy.myspaceshopping.myspaceshopping.util;

import android.os.Handler;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络操作的公共类
 * Created by Administrator on 2016/4/20 0020.
 */
public class OkHttpUtils {
    OkHttpClient client = new OkHttpClient();
    Request.Builder builder = new Request.Builder();//最好每个函数中都new一个Builder
    private static OkHttpUtils utils;
    private OkHttpUtils(){}

    public static OkHttpUtils getInstance(){
        if (utils ==null){
            utils = new OkHttpUtils();
        }
        return  utils;
    }
    public  OkHttpUtils doGet(String url, Map<String,String> pairs){
        builder.url(url+buildGetUrl(pairs)).header("apikey","eca37bd5318ddde48b144c6a37bd82e5");
        return utils;
    }
    public  OkHttpUtils doPost(String url,Map<String,String> pairs){
        builder.url(url);
        if (pairs!=null && !pairs.isEmpty()) {
            FormBody.Builder formBuilder = new FormBody.Builder();
            for (String key:pairs.keySet()){
                formBuilder.add(key,pairs.get(key));
            }
            builder.post(formBuilder.build()).header("apikey","eca37bd5318ddde48b144c6a37bd82e5");
        }
        return utils;
    }
    Handler handler = new Handler();
    public void excute(final OKCallBack back){

        client.newCall(builder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (back!=null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            back.onFailure("获取网络数据失败");
                        }
                    });

                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (back!=null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            back.onResponse(result);
                        }
                    });

                }
            }
        });
    }
    private String buildGetUrl(Map<String,String > pairs){
        if (pairs!=null && !pairs.isEmpty()){
            boolean isFirst = true;
            StringBuilder sb = new StringBuilder();
            for (String key:pairs.keySet()) {
                String value = pairs.get(key);
                if (isFirst){
                    sb.append("?");
                    isFirst = false;
                }else {
                    sb.append("&");
                }
                sb.append(key);
                sb.append("=");
                sb.append(value);
            }
            return sb.toString();
        }
        return "";
    }

    public interface OKCallBack{
        void onFailure(String message);
        void onResponse(String message);
    }
}
