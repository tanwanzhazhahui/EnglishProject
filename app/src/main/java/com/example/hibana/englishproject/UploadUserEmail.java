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
import com.example.hibana.englishproject.ConnectUtils.UserUtils;

public class UploadUserEmail extends Activity {

    private Button confirm;
    private EditText email;
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
                    UploadUserEmail.this.finish();
                    startActivity(new Intent(UploadUserEmail.this,PersonalCenter.class));
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
        setContentView(R.layout.uploaduseremail);
        initView();
    }

    public void initView(){
        this.back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UploadUserEmail.this,PersonalCenter.class));
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
            }
        });
        this.confirm=findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().trim().equals("")){
                    Toast.makeText(UploadUserEmail.this,"邮箱不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    new Thread(){
                        @Override
                        public void run() {
                            SharedPreferences preferences=getSharedPreferences("user",MODE_PRIVATE);
                            String phoenumber=preferences.getString("phonenumber",null);
                            UserUtils.changeUserEmailByPhonenumber(phoenumber,email.getText().toString());
                            handler.sendEmptyMessage(CHANGESUCCESS);
                        }
                    }.start();
                }
            }
        });
        this.email=findViewById(R.id.email);
        Intent intent=getIntent();
        email.setText(intent.getStringExtra("email"));
        this.cancel=findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email.setText("");
            }
        });
    }
}
