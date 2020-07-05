package com.example.hibana.englishproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishproject.R;
import com.example.hibana.englishproject.ConnectUtils.HttpConnectionUtils;
import com.example.hibana.englishproject.ConnectUtils.MD5Utils;
import com.example.hibana.englishproject.ConnectUtils.StreamChangeStrUtils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

public class LoginPage extends Activity {

    //组件
    private TextView register,forgetpassword;
    private EditText phonenumber,password;
    private Button tijiao;

    //Handler类型
    private final int ERROR=0;
    private final int LOGINSUCCESS=1;

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case ERROR:
                    Toast.makeText(LoginPage.this,(String)msg.obj,Toast.LENGTH_SHORT).show();
                    break;
                case LOGINSUCCESS:
                    SharedPreferences preferences;
                    SharedPreferences.Editor editor;
                    preferences=getSharedPreferences("user",MODE_PRIVATE);
                    editor=preferences.edit();
                    if(getSharedPreferences("phonenumber",MODE_PRIVATE)!=null){
                        editor.remove("phonenumber");
                    }
                    String phonenumber=msg.obj.toString();
                    editor.putString("phonenumber",phonenumber);
                    editor.commit();
                    Toast.makeText(LoginPage.this,"登录成功",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginPage.this,MainActivity.class);
                    startActivity(intent);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        initView();//初始化组件
    }

    public void initView(){
        this.register=findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPage.this,RegisterPage.class));
            }
        });
        this.forgetpassword=findViewById(R.id.forgetpassword);
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPage.this,ChangePwdPage.class));
            }
        });
        this.phonenumber=findViewById(R.id.phonenumber);
        this.password=findViewById(R.id.password);
        this.tijiao=findViewById(R.id.tijiao);
        tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(phonenumber.getText().toString(),password.getText().toString());
            }
        });
    }

    //登录事件
    public void login(final String phonenumber, String password){
        if(phonenumber.trim().equals("")|| password.trim().equals("")){
            Toast.makeText(LoginPage.this,"手机号或密码为空",Toast.LENGTH_SHORT).show();
        }else{
            final String safepassword=MD5Utils.md5Password(password);
            final String param=MD5Utils.md5Password("我起了，一枪秒了，有什么好说的");
            new Thread(){
                private HttpURLConnection connection;
                @Override
                public void run() {
                    try {
                        String data="phonenumber="+URLEncoder.encode(phonenumber,"utf-8")+"&password="+URLEncoder.encode(safepassword,"utf-8")+"&param="+URLEncoder.encode(param,"utf-8");
                        connection=HttpConnectionUtils.getLoginConnection(data);
                        int code=connection.getResponseCode();
                        if(code==200){
                            InputStream inputStream=connection.getInputStream();
                            String str=StreamChangeStrUtils.toChange(inputStream);
                            if(str.equals("登录成功")){
                                Message message=Message.obtain();
                                message.what=LOGINSUCCESS;
                                message.obj=phonenumber;
                                handler.sendMessage(message);
                            }else if(str.equals("账号或密码错误")){
                                Message message=Message.obtain();
                                message.what=ERROR;
                                message.obj=str;
                                handler.sendMessage(message);
                            }else if(str.equals("没有这个用户")){
                                Message message=Message.obtain();
                                message.what=ERROR;
                                message.obj=str;
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

}
