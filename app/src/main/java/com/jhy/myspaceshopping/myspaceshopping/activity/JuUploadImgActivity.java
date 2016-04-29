package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.app.Activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.object.MyUser;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by 俊峰 on 2016/4/23.
 */

public class JuUploadImgActivity extends Activity {

    private ImageView img;
    private ImageView back;
    private TextView enter;
    Context context = this;
    String path;
    Bitmap bm;

    public static final int REQUEST_CODE =4;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ju_upload);
        img = (ImageView) findViewById(R.id.ju_upload_img);
        back = (ImageView) findViewById(R.id.ju_upload_back);
        enter = (TextView) findViewById(R.id.ju_upload_enter);


        img.setOnClickListener(Click);
        back.setOnClickListener(Click);
        enter.setOnClickListener(Click);
        MyUser   user = BmobUser.getCurrentUser(this, MyUser.class);
        if(user.getIcon()!=null){
            Picasso.with(this).load("http://file.bmob.cn/"+user.getIcon().getUrl().toString()).into(img);
        }else{
            Picasso.with(this).load("http://file.bmob.cn/M03/46/56/oYYBAFcfIiGAIh3gAAAEw_gSloU510.png").into(img);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setMyBit();
    }

    private void setMyPhoto(){
        //调用系统的相册
        if (Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED)) {
            Intent getImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getImageIntent.addCategory(Intent.CATEGORY_OPENABLE);
            getImageIntent.setType("image/jpeg");
            startActivityForResult(getImageIntent, REQUEST_CODE);
            setMyBit();
        } else {
            Toast.makeText(getApplicationContext(), "SD卡不可用", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != RESULT_OK) {  //此处的 RESULT_OK 是系统自定义得一个常量
            Log.i("result","ActivityResult resultCode error");
            return;
        }

        //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = getContentResolver();
        //此处的用于判断接收的Activity是不是你想要的那个
        if (requestCode == REQUEST_CODE) {
            try {

                Uri originalUri = data.getData();        //获得图片的uri
                bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);        //显得到bitmap图片

              //  这里开始的第二部分，获取图片的路径：
                String[] proj = {MediaStore.Images.Media.DATA};
                //好像是android多媒体数据库的封装接口，具体的看Android文档
                Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                //按我个人理解 这个是获得用户选择的图片的索引值
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                //将光标移至开头 ，这个很重要，不小心很容易引起越界
                cursor.moveToFirst();
                //最后根据索引值获取图片路径
                 path = cursor.getString(column_index);
                Log.i("result","IMG---------"+path);
            }catch (IOException e) {
                Log.e("result",e.toString());
            }
        }
    }




    View.OnClickListener Click = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ju_upload_img:
                      setMyPhoto();
                    break;
                case R.id.ju_upload_back:
                    Intent intent = new Intent(JuUploadImgActivity.this,JuPersonActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.ju_upload_enter:
                    if (path!=null){
                        uploadImg();
                    }
                    break;
            }
        }
    };

    //通过获取图片路径来显示图片
    private void setMyBit(){
        if(path!=null){
            File file = new File(path);
            if(file.exists()){
                Bitmap bm = BitmapFactory.decodeFile(path);
                img.setImageBitmap( bm);
            }
        }
    }

    private void uploadImg(){
       if(path!=null){
        String picPath = path;
       final BmobFile bmobFile = new BmobFile(new File(picPath));
        bmobFile.uploadblock(context, new UploadFileListener() {
            MyUser user = BmobUser.getCurrentUser(context, MyUser.class);

            @Override
            public void onSuccess() {
                //设置内容

                user.setIcon(bmobFile);
                 Log.i("result","BmobFile---------"+bmobFile.getFileUrl(JuUploadImgActivity.this));
//                user.setIcon(bmobFile.getFileUrl(JuUploadImgActivity.this));




                user.update(JuUploadImgActivity.this, user.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Intent intent = new Intent(JuUploadImgActivity.this,JuPersonActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    @Override
                    public void onFailure(int i, String s) {
                    }
                });

                Toast.makeText(JuUploadImgActivity.this, "上传文件成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(JuUploadImgActivity.this, "上传文件失败"+msg, Toast.LENGTH_SHORT).show();

            }
        });
       }
    }


}
















