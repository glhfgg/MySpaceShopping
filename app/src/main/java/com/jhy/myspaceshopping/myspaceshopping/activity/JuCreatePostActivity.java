package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.object.MyUser;
import com.jhy.myspaceshopping.myspaceshopping.object.Post;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;


/**
 * Created by Administrator on 2016/4/25.
 */
public class JuCreatePostActivity extends Activity{

    ImageView back;
    TextView enter;
    ImageView add;
    ImageView camera;
    ImageView img;
    EditText content;
    String path;
    Post post;
    public static final int POST_REQUEST_CODE = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ju_createpost);
        post = new Post();
        init();
        setClick();
    }

    private void init(){
        camera = (ImageView) findViewById(R.id.ju_create_camera);
        back = (ImageView) findViewById(R.id.ju_create_back);
        enter = (TextView) findViewById(R.id.ju_create_enter);
        add = (ImageView) findViewById(R.id.ju_create_addimg);
        img = (ImageView) findViewById(R.id.ju_create_img);
        content = (EditText) findViewById(R.id.ju_create_content);

    }

    private void setClick(){

        back.setOnClickListener(Click);
        enter.setOnClickListener(Click);
        add.setOnClickListener(AddClick);
        camera.setOnClickListener(AddClick);
    }





    private void setData(){
        MyUser user = BmobUser.getCurrentUser(this,MyUser.class);
        post.setContent(content.getText().toString());
        //添加一对一关联
        post.setAuthor(user);
        post.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
            }
            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void uploadImg(){
        if(path!=null){
            String picPath = path;
            final BmobFile bmobFile = new BmobFile(new File(picPath));
            bmobFile.uploadblock(this, new UploadFileListener() {
                @Override
                public void onSuccess() {
                    //设置内容
                    post.setImage(bmobFile);
                    post.update(JuCreatePostActivity.this, post.getObjectId(), new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            Log.i("bmob","更新成功：");
                        }
                        @Override
                        public void onFailure(int code, String msg) {
                            // TODO Auto-generated method stub
                            Log.i("bmob","更新失败："+msg);
                        }
                    });
                    Toast.makeText(JuCreatePostActivity.this, "上传文件成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
                @Override
                public void onProgress(Integer value) {
                    // 返回的上传进度（百分比）
                }
                @Override
                public void onFailure(int code, String msg) {
                    Toast.makeText(JuCreatePostActivity.this, "上传文件失败"+msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != RESULT_OK) {  //此处的 RESULT_OK 是系统自定义得一个常量
            Log.i("result","ActivityResult resultCode error");
            return;
        }
        Bitmap bm = null;
        //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = getContentResolver();
        //此处的用于判断接收的Activity是不是你想要的那个
        if (requestCode == POST_REQUEST_CODE) {
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
            }catch (IOException e) {
                Log.e("result",e.toString());
            }
        }


      //相机
        String name = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        Toast.makeText(this, name, Toast.LENGTH_LONG).show();
        Bundle bundle = data.getExtras();
        Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
        FileOutputStream b = null;
        File file = new File("/sdcard/myImage/");
        path = "/sdcard/myImage/"+name;
        file.mkdirs();// 创建文件夹
        String fileName = "/sdcard/myImage/"+name;
        try {
            b = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        img.setImageBitmap(bitmap);// 将图片显示在ImageView里
    }







    private void addImg(){
        if (Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED)) {
            Intent getImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getImageIntent.addCategory(Intent.CATEGORY_OPENABLE);
            getImageIntent.setType("image/jpeg");
            startActivityForResult(getImageIntent, POST_REQUEST_CODE);
            setMyBit();

        } else {
            Toast.makeText(getApplicationContext(), "SD卡不可用", Toast.LENGTH_SHORT).show();
        }

    }

    private void setMyBit(){
        if(path!=null){
            File file = new File(path);
            if(file.exists()){
                Bitmap bm = BitmapFactory.decodeFile(path);
                img.setImageBitmap(bm);
            }
        }
    }



    View.OnClickListener AddClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ju_create_addimg:
                    addImg();
                    break;
                case R.id.ju_create_camera:
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 1);
                    break;
            }



        }
    };

    View.OnClickListener Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ju_create_back:
                    finish();
                    break;

                case R.id.ju_create_enter:
                    setData();
                    if(path != null){
                        uploadImg();
                    }else{
                        finish();
                    }
                    break;


            }
        }
    };




}
