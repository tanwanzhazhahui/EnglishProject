package com.example.hibana.englishproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.englishproject.R;
import com.example.hibana.englishproject.ConnectUtils.HttpConnectionUtils;
import com.example.hibana.englishproject.ConnectUtils.MD5Utils;
import com.example.hibana.englishproject.ConnectUtils.StreamChangeStrUtils;
import com.mob.tools.network.HttpConnection;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterPage extends AppCompatActivity {

    //组件
    private EditText phonenumber,pwd1,pwd2,yanzhengma;
    private Button huoquyanzhengma,tijiao;

    //短信SDK组件
    public EventHandler eh;//事件接受器
    private TimeCount mTimeCount;//计时器

    //Handler类型
    private final int ERROR=0;
    private final int REGISTERSUCCESS=1;

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case  ERROR:
                    Toast.makeText(RegisterPage.this,(String)msg.obj,Toast.LENGTH_SHORT).show();
                    break;
                case  REGISTERSUCCESS:
                    //注册成功
                    Toast.makeText(RegisterPage.this,(String)msg.obj,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterPage.this,LoginPage.class));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        //设置ActionBar返回按钮
        ActionBar actionBar=this.getSupportActionBar();
        actionBar.setTitle("用户注册");
        actionBar.setDisplayHomeAsUpEnabled(true);

        initView();//初始化组件
        init();//初始化事件接收器
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initView(){
        this.phonenumber=findViewById(R.id.phonenumber);
        this.pwd1=findViewById(R.id.pwd1);
        this.pwd2=findViewById(R.id.pwd2);
        this.yanzhengma=findViewById(R.id.yanzhengma);
        this.huoquyanzhengma=findViewById(R.id.huoquyanzhengma);
        huoquyanzhengma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yanzhengClick(huoquyanzhengma);
            }
        });
        this.tijiao=findViewById(R.id.tijiao);
        tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check(tijiao);
            }
        });
        mTimeCount=new TimeCount(60000,1000);
    }


    //计时器
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture,long countDownInterval){
            super(millisInFuture,countDownInterval);
        }

        @Override
        public void onTick(long l) {
            huoquyanzhengma.setClickable(false);
            huoquyanzhengma.setText(l/1000+"秒后重新获取");
        }

        @Override
        public void onFinish() {
            huoquyanzhengma.setClickable(true);
            huoquyanzhengma.setText("获取验证码");
        }
    }


    //正则匹配手机号码
    public boolean checkTel(String tel){
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        Matcher matcher = p.matcher(tel);
        return matcher.matches();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }


    //提交时检测短信
    public void check(View view){
        if(phonenumber.getText().toString().trim().equals("") || pwd1.getText().toString().trim().equals("") || pwd2.getText().toString().trim().equals("")){
            Toast.makeText(RegisterPage.this,"手机号或密码为空",Toast.LENGTH_SHORT).show();
        }else if(yanzhengma.getText().toString().equals("")){
            Toast.makeText(RegisterPage.this,"验证码为空",Toast.LENGTH_SHORT).show();
        }else if(!checkTel(phonenumber.getText().toString().trim())){
            Toast.makeText(RegisterPage.this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
        }else if(!pwd1.getText().toString().trim().equals(pwd2.getText().toString().trim())){
            Toast.makeText(RegisterPage.this,"密码不匹配",Toast.LENGTH_SHORT).show();
        }else{
            SMSSDK.submitVerificationCode("+86",phonenumber.getText().toString().trim(),yanzhengma.getText().toString().trim());//提交验证码
        }
    }

    //验证码按钮点击事件
    public void yanzhengClick(View view){
        if(!phonenumber.getText().toString().trim().equals("")){
            if(checkTel(phonenumber.getText().toString().trim())){
                SMSSDK.getVerificationCode("+86",phonenumber.getText().toString());//获取验证码
                mTimeCount.start();
                Toast.makeText(RegisterPage.this,"短信已经发送，有效时间为5分钟",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(RegisterPage.this,"请输入正确手机号",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(RegisterPage.this,"请输入手机号",Toast.LENGTH_SHORT).show();
        }
    }

    //初始化事件接收器
    private  void init(){
        eh=new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                if(result==SMSSDK.RESULT_COMPLETE){
                    //回调完成
                    if(event==SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                        //提交验证码成功,可以在此进行其他业务处理
                        register(phonenumber.getText().toString(),pwd2.getText().toString());
                    }
                }else{
                    ((Throwable)data).printStackTrace();
                    Message message=Message.obtain();
                    message.what=ERROR;
                    message.obj="验证错误";
                    handler.sendMessage(message);
                }
            }
        };
        SMSSDK.registerEventHandler(eh);//注册短信回调;
    }

    //当验证码验证成功后调用的事件
    public void register(final String phonenumber,String password){
        final String safepassword=MD5Utils.md5Password(password);
        final String param=MD5Utils.md5Password("我起了，一枪秒了，有什么好说的");
        new Thread(){
            private HttpURLConnection connection;
            @Override
            public void run() {
                try {
                    String data="phonenumber="+URLEncoder.encode(phonenumber,"utf-8")+"&password="+URLEncoder.encode(safepassword,"utf-8")+"&param="+URLEncoder.encode(param,"utf-8");
                    connection=HttpConnectionUtils.getRegisterdConnection(data);
                    int code=connection.getResponseCode();
                    if(code==200){
                        InputStream inputStream=connection.getInputStream();
                        String str=StreamChangeStrUtils.toChange(inputStream);
                        if(str.equals("该手机号已经注册过了")){
                            Message message=Message.obtain();
                            message.what=ERROR;
                            message.obj=str;
                            handler.sendMessage(message);
                        }else if(str.equals("注册成功")){
                            Message message=Message.obtain();
                            message.what=REGISTERSUCCESS;
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
