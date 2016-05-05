package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.object.MyUser;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = (EditText) findViewById(R.id.edit_user_name);
        pass = (EditText) findViewById(R.id.edit_user_passward);
        login = (Button) findViewById(R.id.btn_login);
        singup = (TextView) findViewById(R.id.btn_register);
        login.setOnClickListener(click);
        singup.setOnClickListener(click);
        Bmob.initialize(this, "83d3230ec6120329ff990b35775b96f3");
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

}
