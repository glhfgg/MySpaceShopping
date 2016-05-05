package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.object.MyUser;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/4/21.
 */


public class SingupActivity extends Activity{

    EditText name;
    EditText pass;
    EditText email;
    Button enter;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = (EditText) findViewById(R.id.register_edit_user_name);
        pass = (EditText) findViewById(R.id.register_edit_user_password);
        email = (EditText) findViewById(R.id.register_edit_user_mail);
        enter = (Button) findViewById(R.id.btn_login);
        enter.setOnClickListener(click);
    }

    private  void setUser(){

        MyUser bu = new MyUser();
        bu.setUsername(name.getText().toString());
        bu.setPassword(pass.getText().toString());
        bu.setContent("主人很懒什么都没留下...");
        bu.setPersonname(name.getText().toString());
        bu.setCity("武汉");
        bu.setQQ("请输入QQ号");
        bu.setBirthday("请输入生日");
        bu.setSex(true);
        bu.setFriend(true);
//        bu.setSex(true);
        //每个账号对应一个emil地址 （用户填写后会有邮件进行确认）
        bu.setEmail(email.getText().toString());
        //注意：不能用save方法进行注册

        bu.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(SingupActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                Toast.makeText(SingupActivity.this, "注册失败:"+msg, Toast.LENGTH_SHORT).show();
            }
        });
    }














    View.OnClickListener click = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            setUser();



        }
    };

}
