package com.example.hibana.englishproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.englishproject.R;
import com.example.hibana.englishproject.ConnectUtils.UserUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;


public class PersonalCenter extends Activity {

    private ImageView back,avator;
    private Bitmap bitmap;

    private TextView username,sex,email,phonenumber;
    private LinearLayout pickavator,changeusername,changesex,changeemail,changepwd,tradepwd;

    //handler消息类型;
    private final int GETUSERINFOSUCCESS=66;
    private final int SETAVATOR=77;
    private final int ERROR=0;

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GETUSERINFOSUCCESS:
                    showData(msg.obj.toString());
                    break;
                case ERROR:
                    break;
                case SETAVATOR:
                    avator.setImageBitmap(bitmap);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_center);
        initview();
        initData();
    }

    public void initview(){
        this.back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersonalCenter.this,MainActivity.class));
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
            }
        });
        this.phonenumber=findViewById(R.id.phonenumber);
        SharedPreferences preferences=getSharedPreferences("user",MODE_PRIVATE);
        phonenumber.setText(preferences.getString("phonenumber",null));
        this.pickavator=findViewById(R.id.pickavator);
        pickavator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PersonalCenter.this,UploadAvator.class);
                intent.setData(bitmaptouri(PersonalCenter.this,bitmap));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });
        this.changeusername=findViewById(R.id.changeusername);
        changeusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PersonalCenter.this,UploadUserName.class);
                intent.putExtra("username",username.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });
        this.username=findViewById(R.id.username);
        this.changesex=findViewById(R.id.changesex);
        changesex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] items=new String[]{"男","女"};
                new AlertDialog.Builder(PersonalCenter.this)
                        .setTitle("选择性别")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, final int which) {
                                sex.setText(items[which]);
                                new Thread(){
                                    @Override
                                    public void run() {
                                        UserUtils.changeUserSexByPhonenumber(phonenumber.getText().toString(),items[which]);
                                    }
                                }.start();
                                //启动线程更新数据
                                dialogInterface.dismiss();
                            }
                        })
                        .create().show();
            }
        });
        this.sex=findViewById(R.id.sex);
        this.changeemail=findViewById(R.id.changeemail);
        changeemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PersonalCenter.this,UploadUserEmail.class);
                intent.putExtra("email",email.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });
        this.email=findViewById(R.id.email);
        this.avator=findViewById(R.id.avator);
        this.changepwd=findViewById(R.id.changepwd);
        changepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PersonalCenter.this,UploadUserPassword.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });
        this.tradepwd=findViewById(R.id.tradepwd);
        tradepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PersonalCenter.this,UploadUserTradePassword.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });
    }

    public void initData(){
        final String phonenumber=this.phonenumber.getText().toString();
        new Thread(){
            @Override
            public void run() {
                String result=UserUtils.getUserInfoByPhonenumber(phonenumber);
                Message message=Message.obtain();
                message.what=GETUSERINFOSUCCESS;
                message.obj=result;
                handler.sendMessage(message);
            }
        }.start();
    }

    public void initAvator(final String imagename){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url=new URL("http://47.100.92.75:8080/EnglishProject/Avator/"+imagename);
                    InputStream is=url.openStream();
                    bitmap=BitmapFactory.decodeStream(is);
                    handler.sendEmptyMessage(SETAVATOR);
                } catch (Exception e) {
                    e.printStackTrace();
                    if(bitmap!=null && !bitmap.isRecycled()){
                        bitmap.recycle();
                        bitmap=null;
                    }
                    System.gc();
                }
            }
        }.start();
    }

    private void showData(String result){
        JSONObject jsonObject;
        try {
            jsonObject=new JSONObject(result);
            JSONObject userObj=jsonObject.getJSONObject("user");
            String avator=userObj.getString("avator");
            String username=userObj.getString("username");
            String sex=userObj.getString("sex");
            String email=userObj.getString("email");
            String phonenumber=userObj.getString("phonenumber");
            //设置TextView
            this.username.setText(username);
            this.sex.setText(sex);
            this.email.setText(email);
            this.phonenumber.setText(phonenumber);
            initAvator(avator);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Uri bitmaptouri(Context context,Bitmap bitmap){
        File path=new File(context.getCacheDir()+File.separator+System.currentTimeMillis()+".jpg");
        try {
            OutputStream os=new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,os);
            os.close();
            return Uri.fromFile(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}


