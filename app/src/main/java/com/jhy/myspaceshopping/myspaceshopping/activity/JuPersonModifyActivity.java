package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.app.Activity;
import android.app.AlertDialog;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jhy.myspaceshopping.myspaceshopping.R;

import com.jhy.myspaceshopping.myspaceshopping.object.MyUser;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 俊峰 on 2016/4/22.
 */
public class JuPersonModifyActivity extends Activity {
    //Dialog
    TextView title;

    ImageView back;
    TextView name;     //登录名
    TextView pername;  //昵称
    TextView sex;
    TextView city;
    TextView birthday;
    TextView qq;
    TextView content;
    TextView cancalLogin; //对出登录

    MyUser user;
    String sexs;
    Boolean sexb;

           //Dialog 对话框标题
    String texts;
    MyUser newUser = new MyUser();

    DatePickerDialog dpd;
    AlertDialog.Builder sexDialg;
    AlertDialog.Builder dialog;

    private int i; //表示用的测定值


    //DatePickerDialog;
    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_personmodify);
        user = BmobUser.getCurrentUser(this, MyUser.class);

        init();
        setText();
        setDialog();
        setDataPocker();
        setSexDialog();
        setClick();

    }

    private void init() {

        back = (ImageView) findViewById(R.id.modify_back);
        name = (TextView) findViewById(R.id.modify_name);
        pername = (TextView) findViewById(R.id.modify_personname);
        sex = (TextView) findViewById(R.id.modify_sex);
        city = (TextView) findViewById(R.id.modify_city);
        birthday = (TextView) findViewById(R.id.modify_birthday);
        qq = (TextView) findViewById(R.id.modify_qq);
        content = (TextView) findViewById(R.id.modify_content);
        cancalLogin = (TextView) findViewById(R.id.modify_cancalLogin);
    }

    private void setClick() {
        back.setOnClickListener(click);
        name.setOnClickListener(TextClick);
        pername.setOnClickListener(TextClick);
        sex.setOnClickListener(TextClick);
        city.setOnClickListener(TextClick);
        birthday.setOnClickListener(TextClick);
        qq.setOnClickListener(TextClick);
        content.setOnClickListener(TextClick);
        cancalLogin.setOnClickListener(click);
    }

    private void setText() {
        if (user.getSex()) {
            sexs = "男";
        } else {
            sexs = "女";
        }
        name.setText(user.getUsername());
        pername.setText(user.getPersonname());
        sex.setText(sexs);
        city.setText(user.getCity());
        birthday.setText(user.getBirthday());
        qq.setText(user.getQQ());
        content.setText(user.getContent());
    }

    private void setDialog() {

        LayoutInflater inflater = LayoutInflater.from(this);
        final View v = inflater.inflate(R.layout.dialog_ju_modify, null);
        final EditText text = (EditText) v.findViewById(R.id.ju_dialog_message);
        dialog = new AlertDialog.Builder(this);
        dialog.setView(v);

        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                texts = text.getText().toString();
                //判断dialog是哪个TextView点击弹出的
                switch (i) {
                    case 0:
                        newUser.setPersonname(texts);
                        pername.setText(texts);
                        break;
                    case 1:
                        break;
                    case 2:
                        newUser.setContent(texts);
                        content.setText(texts);
                        break;
                    case 3:
                        break;
                    case 4:
                        newUser.setQQ(texts);
                        qq.setText(texts);
                        break;

                }
                setBmob();
                ((ViewGroup) v.getParent()).removeView(v);
            }

        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((ViewGroup) v.getParent()).removeView(v);
            }
        });


    }


    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.modify_back:
                    finish();
                    break;
                case R.id.modify_cancalLogin:
                    BmobUser.logOut(JuPersonModifyActivity.this);   //清除缓存用户对象
                    Intent intent = new Intent(JuPersonModifyActivity.this,LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(JuPersonModifyActivity.this, "已退出", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }

        }
    };

    View.OnClickListener TextClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.modify_personname:
                    dialog.setTitle("编辑昵称");
                    dialog.create().show();
                    i = 0;
                    break;

                case R.id.modify_sex:
                    sexDialg.create().show();
                    i = 1;
                    break;


                case R.id.modify_content:
                    dialog.setTitle("编辑内容");
                    dialog.create().show();
                    i = 2;
                    break;

                case R.id.modify_birthday:
                    dpd.setTitle("编辑生日");
                    dpd.show();
                    i = 3;
                    break;

                case R.id.modify_qq:
                    dialog.setTitle("编辑QQ");
                    dialog.create().show();
                    i = 4;
                    break;

            }
        }
    };


    private void setBmob() {

        newUser.update(this, MyUser.getCurrentUser(this).getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                Toast.makeText(JuPersonModifyActivity.this, "更新用户信息成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                Toast.makeText(JuPersonModifyActivity.this, "更新用户信息失败:" + msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSexDialog() {
        sexDialg = new AlertDialog.Builder(this);
        sexDialg.setTitle("选择性别");

        sexDialg.setSingleChoiceItems(new String[]{"男", "女"}, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        sexb = true;
                        break;
                    case 1:
                        sexb = false;
                        break;
                }
            }
        });

        sexDialg.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newUser.setSex(sexb);
                Log.i("result", "________________----" + sexb);
                if(sexb){
                    sex.setText("男");
                }else{
                    sex.setText("女");
                }
                setBmob();
            }
        });

        sexDialg.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    private void setDataPocker() {
         dpd = new DatePickerDialog(this, Datelistener, year, month, day);

    }

    private DatePickerDialog.OnDateSetListener Datelistener = new DatePickerDialog.OnDateSetListener() {
        /**
         * params：view：该事件关联的组件
         * params：myyear：当前选择的年
         * params：monthOfYear：当前选择的月
         * params：dayOfMonth：当前选择的日
         */

        @Override
        public void onDateSet(DatePicker view, int myyear, int monthOfYear, int dayOfMonth) {
            //修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值

            year = myyear;
            month = monthOfYear;
            day = dayOfMonth;
            dpd.setButton("确认", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    birthday.setText(year+"-"+month+"-"+day);
                    newUser.setBirthday(year+"-"+month+"-"+day);

                    setBmob();
                }
            });
          }
       };
    }







