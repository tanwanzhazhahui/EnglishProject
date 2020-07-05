package com.example.hibana.englishproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.englishproject.R;
import com.example.hibana.englishproject.ConnectUtils.UserUtils;

import org.json.JSONObject;

public class Purse extends Activity {
    private ImageView back;
    private TextView money;

    //handler消息类型;
    private final int GETUSERWALLETSUCCESS=66;
    private final int ERROR=0;

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GETUSERWALLETSUCCESS:
                    showData(msg.obj.toString());
                    break;
                case ERROR:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_purse);
        initView();
        initData();
    }

    public void initView(){
        this.back=findViewById(R.id.p_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Purse.this.finish();
            }
        });
        this.money=findViewById(R.id.money);

    }

    public void initData(){
        SharedPreferences preferences=getSharedPreferences("user",MODE_PRIVATE);
        final String phonenumber=preferences.getString("phonenumber",null);
        new Thread(){
            @Override
            public void run() {
                String result=UserUtils.getUserWalletByPhonenumber(phonenumber);
                Message message=Message.obtain();
                message.what=GETUSERWALLETSUCCESS;
                message.obj=result;
                handler.sendMessage(message);
            }
        }.start();
    }

    private void showData(String result){
        JSONObject jsonObject;
        try {
            jsonObject=new JSONObject(result);
            JSONObject userwalletObj=jsonObject.getJSONObject("userwallet");
            String money=userwalletObj.getString("money");
            this.money.setText(money+"元");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
