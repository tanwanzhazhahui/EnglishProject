package com.example.hibana.englishproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.englishproject.R;
import com.example.hibana.englishproject.ConnectUtils.HttpConnectionUtils;
import com.example.hibana.englishproject.ConnectUtils.MD5Utils;
import com.example.hibana.englishproject.ConnectUtils.StreamChangeStrUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

public class UploadUserPassword extends Activity {

    private ImageView back;
    private Button confirm;
    private EditText oldpwd,newpwd1,newpwd2;

    //handler消息类型
    private static final int CHANGESUCCESS=66;
    private static final int CHANGEFAIL=77;
    private static final int ERROR=0;

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CHANGESUCCESS:
                    Toast.makeText(UploadUserPassword.this,(String)msg.obj,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UploadUserPassword.this,PersonalCenter.class));
                    overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                    break;
                case CHANGEFAIL:
                    oldpwd.setText("");
                    newpwd1.setText("");
                    newpwd2.setText("");
                    Toast.makeText(UploadUserPassword.this,(String)msg.obj,Toast.LENGTH_SHORT).show();
                    break;
                case ERROR:
                    oldpwd.setText("");
                    newpwd1.setText("");
                    newpwd2.setText("");
                    Toast.makeText(UploadUserPassword.this,(String)msg.obj,Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploaduserpassword);
        initView();
    }

    public void initView(){
        this.back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UploadUserPassword.this,PersonalCenter.class));
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
            }
        });
        this.confirm=findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(oldpwd.getText().toString().trim().equals("") || newpwd1.getText().toString().trim().equals("") || newpwd2.getText().toString().trim().equals("")){
                    Toast.makeText(UploadUserPassword.this,"密码格式错误",Toast.LENGTH_SHORT).show();
                }else if(!newpwd1.getText().toString().trim().equals(newpwd2.getText().toString().trim())){
                    Toast.makeText(UploadUserPassword.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences preferences=getSharedPreferences("user",MODE_PRIVATE);
                    final String phonenumber=preferences.getString("phonenumber",null);
                    final String safeoldpassword=MD5Utils.md5Password(oldpwd.getText().toString());
                    final String safenewpassword=MD5Utils.md5Password(newpwd2.getText().toString());
                    final String param=MD5Utils.md5Password("我起了，一枪秒了，有什么好说的");
                    new Thread(){
                        private HttpURLConnection connection;
                        @Override
                        public void run() {
                            try {
                                String data="phonenumber="+URLEncoder.encode(phonenumber,"utf-8")+"&password="+URLEncoder.encode(safeoldpassword,"utf-8")+"&param="+URLEncoder.encode(param,"utf-8");
                                connection=HttpConnectionUtils.getLoginConnection(data);
                                int code=connection.getResponseCode();
                                if(code==200){
                                    InputStream inputStream=connection.getInputStream();
                                    String str=StreamChangeStrUtils.toChange(inputStream);
                                    if(str.equals("登录成功")){
                                        changepassword(phonenumber,safenewpassword,param);
                                    }else{
                                        Message message=Message.obtain();
                                        message.what=CHANGEFAIL;
                                        message.obj="旧密码错误";
                                        handler.sendMessage(message);
                                    }
                                }else{
                                    Message message=Message.obtain();
                                    message.what=ERROR;
                                    message.obj="请求异常...稍后尝试";
                                    handler.sendMessage(message);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Message message=Message.obtain();
                                message.what=ERROR;
                                message.obj="连接异常";
                                handler.sendMessage(message);
                            }
                        }
                    }.start();
                }
            }
        });
        this.oldpwd=findViewById(R.id.oldpwd);
        this.newpwd1=findViewById(R.id.newpwd1);
        this.newpwd2=findViewById(R.id.newpwd2);
    }

    public void changepassword(final String phonenumber, final String password,final String param){
        new Thread(){
            private HttpURLConnection connection;
            @Override
            public void run() {
                try {
                    String data="phonenumber="+URLEncoder.encode(phonenumber,"utf-8")+"&password="+URLEncoder.encode(password,"utf-8")+"&param="+URLEncoder.encode(param,"utf-8");
                    connection=HttpConnectionUtils.getChangePasswordConnection(data);
                    int code=connection.getResponseCode();
                    if(code==200){
                        InputStream inputStream=connection.getInputStream();
                        String str=StreamChangeStrUtils.toChange(inputStream);
                        if(str.equals("更改成功")){
                            Message message=Message.obtain();
                            message.what=CHANGESUCCESS;
                            message.obj=str;
                            handler.sendMessage(message);
                        }else if(str.equals("此手机没有注册记录")){
                            Message message=Message.obtain();
                            message.what=CHANGEFAIL;
                            message.obj=str;
                            handler.sendMessage(message);
                        }
                    }else{
                        Message message=Message.obtain();
                        message.what=ERROR;
                        message.obj="请求异常，稍后尝试";
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Message message=Message.obtain();
                    message.what=ERROR;
                    message.obj="连接异常";
                    handler.sendMessage(message);
                }
            }
        }.start();
    }
}
