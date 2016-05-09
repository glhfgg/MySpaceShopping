package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.object.MyUser;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/4/21.
 */
public class LoginActivity extends Activity{

    EditText name;
    EditText pass;
    Button login;
    TextView singup;
    Context context = this;
    //QQ登录
    ImageView qq;

    public static String mAppid;
    public static String openidString;
    Bitmap bitmap = null;

    private Tencent mTencent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        Bmob.initialize(this, "83d3230ec6120329ff990b35775b96f3");
    }

    private void init(){
        name = (EditText) findViewById(R.id.edit_user_name);
        pass = (EditText) findViewById(R.id.edit_user_passward);
        login = (Button) findViewById(R.id.btn_login);
        singup = (TextView) findViewById(R.id.btn_register);
        qq = (ImageView) findViewById(R.id.login_qq);


        qq.setOnClickListener(LoginClick);

        login.setOnClickListener(click);
        singup.setOnClickListener(click);
    }

    private void toLogin(){
        MyUser bu = new MyUser();
        bu.setUsername(name.getText().toString());
        bu.setPassword(pass.getText().toString());
        bu.login(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                //通过BmobUser user = BmobUser.getCurrentUser(context)获取登录成功后的本地用户信息
                //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(context,MyUser.class)获取自定义用户信息
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(LoginActivity.this, "登录失败:"+msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            return true;
        } else if(keyCode == KeyEvent.KEYCODE_MENU) {
            //监控/拦截菜单键
        } else if(keyCode == KeyEvent.KEYCODE_HOME) {
            //由于Home键为系统键，此处不能捕获，需要重写onAttachedToWindow()
        }
        return super.onKeyDown(keyCode, event);
    }

    //调用QQ登录
    private void LoginQQ(){
        mAppid = "1105386118";
        mTencent = Tencent.createInstance(mAppid,this);
        mTencent.login(LoginActivity.this,"all", new BaseUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        // TODO Auto-generated method stub

                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        Log.e("qqlife", "-------------" );

                            Log.e("qqlife", "-------------try" );
                            //获得的数据是JSON格式的，获得你想获得的内容
                            //如果你不知道你能获得什么，看一下下面的LOG
                            Log.e("qqlife", "-------------" + response.toString());

                            Log.e("qqlife", "-------------" + openidString);
                            //access_token= ((JSONObject) response).getString("access_token");
                            // expires_in = ((JSONObject) response).getString("expires_in");

                    }

                    @Override
                    public void onError(UiError uiError) {
                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "登录取消", Toast.LENGTH_SHORT).show();
                    }
                });
    }


            View.OnClickListener click = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_register:
                    Intent intent = new Intent(context,SingupActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_login:
                    toLogin();
                    break;
            }
        }
    };

    View.OnClickListener LoginClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
          switch (v.getId()){
              case R.id.login_qq:
                  LoginQQ();
                  break;

          }
        }
    };

private class BaseUiListener implements IUiListener {
    @Override
    public void onComplete(Object response) {
        //V2.0版本，参数类型由JSONObject 改成了Object,具体类型参考api文档
//        mBaseMessageText.setText("onComplete:");
        doComplete((JSONObject) response);
        Toast.makeText(LoginActivity.this, "onComplete", Toast.LENGTH_SHORT).show();
    }
    protected void doComplete(JSONObject values) {
        Toast.makeText(LoginActivity.this, "doComplete", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onError(UiError e) {
//        showResult("onError:", "code:" + e.errorCode + ", msg:"
//                + e.errorMessage + ", detail:" + e.errorDetail);
        Toast.makeText(LoginActivity.this, "onError", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onCancel() {
//        showResult("onCancel", "");
        Toast.makeText(LoginActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
    }
}
}