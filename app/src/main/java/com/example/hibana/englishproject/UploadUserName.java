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
import com.example.hibana.englishproject.ConnectUtils.UserUtils;

public class UploadUserName extends Activity {

    private Button confirm;
    private EditText username;
    private ImageView back,cancel;

    //handler类型
    private final int CHANGESUCCESS=66;
    private final int ERROR=0;

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CHANGESUCCESS:
                    UploadUserName.this.finish();
                    startActivity(new Intent(UploadUserName.this,PersonalCenter.class));
                    overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                    break;
                case ERROR:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadusername);
        initView();
    }

    public void initView(){
        this.back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UploadUserName.this,PersonalCenter.class));
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
            }
        });
        this.confirm=findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().trim().equals("")){
                    Toast.makeText(UploadUserName.this,"昵称不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    new Thread(){
                        @Override
                        public void run() {
                            SharedPreferences preferences=getSharedPreferences("user",MODE_PRIVATE);
                            String phoenumber=preferences.getString("phonenumber",null);
                            UserUtils.changeUserNameByPhonenumber(phoenumber,username.getText().toString());
                            handler.sendEmptyMessage(CHANGESUCCESS);
                        }
                    }.start();
                }
            }
        });
        this.username=findViewById(R.id.username);
        Intent intent=getIntent();
        username.setText(intent.getStringExtra("username"));
        this.cancel=findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username.setText("");
            }
        });
    }
}
