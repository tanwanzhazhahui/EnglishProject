package com.example.hibana.englishproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
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
import com.example.hibana.englishproject.ConnectUtils.UserUtils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

public class UploadUserTradePassword extends Activity {

    private ImageView back;
    private Button confirm;
    private EditText oldtradepwd,newtradepwd1,newtradepwd2;

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
                    Toast.makeText(UploadUserTradePassword.this,"修改成功",Toast.LENGTH_SHORT).show();
                    UploadUserTradePassword.this.finish();
                    startActivity(new Intent(UploadUserTradePassword.this,PersonalCenter.class));
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                    break;
                case CHANGEFAIL:
                    oldtradepwd.setText("");
                    newtradepwd1.setText("");
                    newtradepwd2.setText("");
                    Toast.makeText(UploadUserTradePassword.this,(String)msg.obj,Toast.LENGTH_SHORT).show();
                    break;
                case ERROR:
                    oldtradepwd.setText("");
                    newtradepwd1.setText("");
                    newtradepwd2.setText("");
                    Toast.makeText(UploadUserTradePassword.this,(String)msg.obj,Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadusertradepassword);
        initView();
    }

    public void initView(){
        this.back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UploadUserTradePassword.this,PersonalCenter.class));
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
            }
        });
        this.confirm=findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(oldtradepwd.getText().toString().trim().equals("") || newtradepwd1.getText().toString().trim().equals("") || newtradepwd2.getText().toString().trim().equals("")){
                    Toast.makeText(UploadUserTradePassword.this,"密码格式错误",Toast.LENGTH_SHORT).show();
                }else if(!newtradepwd1.getText().toString().trim().equals(newtradepwd2.getText().toString().trim())){
                    Toast.makeText(UploadUserTradePassword.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences preferences=getSharedPreferences("user",MODE_PRIVATE);
                    final String phonenumber=preferences.getString("phonenumber",null);
                    final String safetradepassword=MD5Utils.md5Password(oldtradepwd.getText().toString()+"wdnmd,人没看到一滴血");
                    final String param=MD5Utils.md5Password("我起了，一枪秒了，有什么好说的");
                    new Thread(){
                        private HttpURLConnection connection;
                        @Override
                        public void run() {
                            try {
                                String data="phonenumber="+URLEncoder.encode(phonenumber,"utf-8")+"&tradepassword="+URLEncoder.encode(safetradepassword,"utf-8")+"&param="+URLEncoder.encode(param,"utf-8");
                                connection=HttpConnectionUtils.getCheckTradePasswordConnection(data);
                                int code=connection.getResponseCode();
                                if(code==200){
                                    InputStream inputStream=connection.getInputStream();
                                    String str=StreamChangeStrUtils.toChange(inputStream);
                                    if(str.equals("验证成功")){
                                        changetradepassword(phonenumber,MD5Utils.md5Password(newtradepwd2.getText().toString()+"wdnmd,人没看到一滴血"));
                                    }else{
                                        Message message=Message.obtain();
                                        message.what=CHANGEFAIL;
                                        message.obj="旧交易密码错误";
                                        handler.sendMessage(message);
                                    }
                                }else {
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
        this.oldtradepwd=findViewById(R.id.oldtradepwd);
        this.newtradepwd1=findViewById(R.id.newtradepwd1);
        this.newtradepwd2=findViewById(R.id.newtradepwd2);
    }

    public void changetradepassword(final String phonenumber, final String tradepassword){
        new Thread(){
            @Override
            public void run() {
                UserUtils.changeUserTradePassword(phonenumber,tradepassword);
                handler.sendEmptyMessage(CHANGESUCCESS);
            }
        }.start();
    }
}
