package com.example.hibana.englishproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishproject.R;
import com.example.hibana.englishproject.ConnectUtils.FormFile;
import com.example.hibana.englishproject.ConnectUtils.HttpConnectionUtils;
import com.example.hibana.englishproject.ConnectUtils.SocketHttpRequester;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UploadAvator extends Activity {
    private ImageView avator;
    private TextView avatorpath;
    private Loading_view loading;


    public static final int SELECT_PHOTO=2;

    //Handler消息类型
    private final int UPLOADSUCCESS=666;
    private final int ERROR=777;
    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPLOADSUCCESS:
                    loading=new Loading_view(UploadAvator.this, R.style.CommonDialog);
                    loading.dismiss();
                    Toast.makeText(UploadAvator.this,(String)msg.obj,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UploadAvator.this,PersonalCenter.class));
                    overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                    break;
                case ERROR:
                    loading.dismiss();
                    Toast.makeText(UploadAvator.this,(String)msg.obj,Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadavator);
        ActionBar actionBar=this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("设置头像");
        initView();
    }

    public void initView(){
        this.avator=findViewById(R.id.avator);
        Intent intent=getIntent();
        Uri data=intent.getData();
        avator.setImageURI(data);
        this.avatorpath=findViewById(R.id.avatorpath);
    }


    public void  PickPhoto(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else{
            openAlbum();
        }
    }

    public void openAlbum(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case SELECT_PHOTO:
                if(resultCode==RESULT_OK){
                    if(Build.VERSION.SDK_INT>19){
                        handleImgeOnKitKat(data);
                    }else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImgeOnKitKat(Intent data){
        String imagePath=null;
        Uri uri=data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            String docId=DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                //解析出数字格式的id
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }else if ("content".equalsIgnoreCase(uri.getScheme())) {
                //如果是content类型的uri，则使用普通方式处理
                imagePath = getImagePath(uri,null);
            }else if ("file".equalsIgnoreCase(uri.getScheme())) {
                //如果是file类型的uri，直接获取图片路径即可
                imagePath = uri.getPath();
            }
            //根据图片路径显示图片
            displayImage(imagePath);

        }
    }



    private void handleImageBeforeKitKat(Intent data){
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            avator.setImageBitmap(bitmap);
            avatorpath.setText(imagePath);
        }else {
            Toast.makeText(this,"failed to get image",Toast.LENGTH_SHORT).show();
        }
    }

    private String getImagePath(Uri uri,String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else{
                    Toast.makeText(this,"failed to get image",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=new MenuInflater(this);
        inflater.inflate(R.menu.avator_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return false;
            case R.id.album:
                PickPhoto();
                return false;
            case R.id.confirm:
                loading=new Loading_view(UploadAvator.this,R.style.CommonDialog);
                loading.show();
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            File file=new File(avatorpath.getText().toString());
                            String requestUrl = "http://47.100.92.75:8080/EnglishProject/uploadavator";
                            //上传文件
                            FormFile formfile = new FormFile(file.getName(), file, "image", "application/octet-stream");
                            Map<String,String> params=new HashMap<String, String>();
                            SharedPreferences preferences=getSharedPreferences("user",MODE_PRIVATE);
                            params.put("phonenumber",preferences.getString("phonenumber",null));
                            Boolean result=SocketHttpRequester.post(requestUrl,params,formfile);
                            if(result){
                                Message message=Message.obtain();
                                message.what=UPLOADSUCCESS;
                                message.obj="保存成功";
                                handler.sendMessage(message);
                            }else{
                                Message message=Message.obtain();
                                message.what=ERROR;
                                message.obj="上传失败";
                                handler.sendMessage(message);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                return false;
        }
        return true;

    }


}
