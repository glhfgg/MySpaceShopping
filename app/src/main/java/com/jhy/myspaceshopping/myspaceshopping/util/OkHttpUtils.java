package com.jhy.myspaceshopping.myspaceshopping.util;

import android.os.Handler;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 俊峰 on 2016/4/20.
 */



    public class OkHttpUtils {
        OkHttpClient client = new OkHttpClient();
        final MediaType MEDIATYPE_PNG = MediaType.parse("image/png");//图片类型
        Request.Builder builder = new Request.Builder();
        private static OkHttpUtils utils;
        private OkHttpUtils(){}

        public static OkHttpUtils getInstance(){
            if (utils ==null){
                utils = new OkHttpUtils();
            }
            return  utils;
        }

        /**
         * 做GET请求
         * @param url 不带参数的 请求地址
         * @param pairs 参数列表
         * @return
         */
        public  OkHttpUtils doGet(String url, Map<String,String> pairs,String apikey){
            builder.url(url+buildGetUrl(pairs)).header("apikey",apikey);
            return utils;
        }

        /**
         *  做POST 请求
         * @param url 不带参数的url
         * @param pairs 请求参数
         * @return
         */
        public  OkHttpUtils doPost(String url,Map<String,String> pairs,String apikey){
            builder.url(url).header("apikey",apikey);
            if (pairs!=null && !pairs.isEmpty()) {
                FormBody.Builder formBuilder = new FormBody.Builder();
                for (String key:pairs.keySet()){
                    formBuilder.add(key,pairs.get(key));
                }
                builder.post(formBuilder.build());
            }
            return utils;
        }

        /**
         * 图片上传（通过File 形式 eg.<input type="file" name="nFime"/> ）
         * @param url 接口地址
         * @param path 图片绝对地址（包含图片名称）
         * @param pairs 需要进行传递 的参数列表，如果没有，传递 null
         * @return
         */
        public OkHttpUtils upLoadImage(String url,String path,Map<String,String> pairs){
            builder.url(url);
            MultipartBody.Builder multBuilder = new MultipartBody.Builder();
            if (pairs!=null && !pairs.isEmpty()){
                for (String key : pairs.keySet()){
                    multBuilder.addFormDataPart(key,pairs.get(key));
                }
            }
            File file = new File(path);
            //nFile 是接口提供的参数名称，不是固定的
            multBuilder.addFormDataPart("nFile",file.getName(), RequestBody.create(MEDIATYPE_PNG,file));
            builder.post(multBuilder.build());
            return  utils;
        }
        Handler handler = new Handler();

        /**
         * 执行最后的请求操作
         * @param back 回调（该操作已经在主线程里）
         */
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
