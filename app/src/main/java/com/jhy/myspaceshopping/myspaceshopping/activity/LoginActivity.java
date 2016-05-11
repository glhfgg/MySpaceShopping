package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
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
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.OtherLoginListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2016/4/21.
 */
public class LoginActivity extends Activity {

    EditText name;
    EditText pass;
    Button login;
    TextView singup;
    Context context = this;
    //QQ登录
    ImageView qqimg;
    Bitmap bitmap = null;
    public static String mAppid;

    private Tencent mTencent;

    String TAG = "qqlife";

    private  String nicknameString;
    private   String img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        Bmob.initialize(this, "83d3230ec6120329ff990b35775b96f3");
    }

    //QQ第三方登录回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, new BaseUiListener());
    }

    private void init() {
        name = (EditText) findViewById(R.id.edit_user_name);
        pass = (EditText) findViewById(R.id.edit_user_passward);
        login = (Button) findViewById(R.id.btn_login);
        singup = (TextView) findViewById(R.id.btn_register);
        qqimg = (ImageView) findViewById(R.id.login_qq);


        qqimg.setOnClickListener(LoginClick);

        login.setOnClickListener(click);
        singup.setOnClickListener(click);
    }

    private void toLogin() {
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
                Toast.makeText(LoginActivity.this, "登录失败:" + msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
            //监控/拦截菜单键
        } else if (keyCode == KeyEvent.KEYCODE_HOME) {
            //由于Home键为系统键，此处不能捕获，需要重写onAttachedToWindow()
        }
        return super.onKeyDown(keyCode, event);
    }

    //调用QQ登录
    private void LoginQQ() {
        mAppid = "1105386118";
        mTencent = Tencent.createInstance(mAppid, this);
        mTencent.login(LoginActivity.this, "all", new BaseUiListener());
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_register:
                    Intent intent = new Intent(context, SingupActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_login:
                    toLogin();
                    break;
            }
        }
    };

    View.OnClickListener LoginClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_qq:
                    LoginQQ();
                    break;
            }
        }
    };

    private void User(String name,String url) {

        MyUser newUser = new MyUser();
        newUser.setFriend(true);
        newUser.setPersonname(name);
        newUser.setLoginQQ(true);
        BmobFile bmobfile =new BmobFile(name+".png","",url);
        Log.i(TAG,"URL---------"+url);
        newUser.setIcon(bmobfile);

        MyUser bmobUser = BmobUser.getCurrentUser(this,MyUser.class);
        newUser.update(context, bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
//                toast("更新用户信息成功:");
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
//                toast("更新用户信息失败:" + msg);
            }
        });
    }



    public void getUserInfoInThread() {

        QQToken qqToken = mTencent.getQQToken();
        UserInfo info = new UserInfo(getApplicationContext(), qqToken);
        info.getUserInfo(new IUiListener() {

            public void onComplete(final Object response) {
                // TODO Auto-generated method stub

                    try {
                        nicknameString =((JSONObject)response).getString("nickname");
                        img =((JSONObject)response).getString("figureurl_qq_2");
                        User(nicknameString,img);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
            }

            public void onCancel() {
                Log.i(TAG, "--------------onCancel");
                // TODO Auto-generated method stub
            }

            public void onError(UiError arg0) {
                // TODO Auto-generated method stub
                Log.i(TAG, "---------------onError" + ":" + arg0);
            }
        });
    }

    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

            try {
                String userId = ((JSONObject) response).getString("openid");
                String snsType = "qq";
                String accessToken = ((JSONObject) response).getString("access_token");
                String expiresIn = ((JSONObject) response).getString("expires_in");

                mTencent.setOpenId(userId);
                mTencent.setAccessToken(accessToken, expiresIn);

                BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(snsType, accessToken, expiresIn, userId);
                BmobUser.loginWithAuthData(context, authInfo, new OtherLoginListener() {
                    @Override
                    public void onSuccess(JSONObject userAuth) {
                        // TODO Auto-generated method stub
                        Log.i(TAG, "第三方登陆成功");
                        getUserInfoInThread();

                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        // TODO Auto-generated method stub
                        Log.i(TAG, "第三方登陆失败：" + msg);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void onError(UiError arg0) {
        }

        @Override
        public void onCancel() {
        }
    }

}