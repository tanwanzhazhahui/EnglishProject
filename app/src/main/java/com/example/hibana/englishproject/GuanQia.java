package com.example.hibana.englishproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.*;
import com.example.englishproject.R;


import java.util.List;

public class GuanQia extends Activity {
    private LinearLayout gq_layout;
    private ImageView cg_close;
    private GuanQiaview guanqia;
    private AttributeSet attrs;
    private Spinner spinner;

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guanqia);
        initView();

    }

    public void initView(){
        this.gq_layout=findViewById(R.id.gqlayout);
        this.cg_close=findViewById(R.id.cg_close);
        cg_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuanQia.this.finish();
            }
        });
        //动态添加GuanQiaView-----------//
        AutoLinefeedLayout alf=new AutoLinefeedLayout(this);//自动换行的布局
        guanqia=new GuanQiaview(this,attrs);
        guanqia.setBackground(getResources().getDrawable(R.drawable.border));
        guanqia.setText("第一关");
        guanqia.setRating(0);//默认没闯关为0；如果数据库有保存进度即有保存星星数，则在这里可以设置
        alf.addView(guanqia);
        gq_layout.addView(alf);
        guanqia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GuanQia.this,ChuangGuan.class);
                startActivity(intent);
            }
        });
        this.spinner=findViewById(R.id.cihui);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(adapterView.getSelectedItem().toString());//获取下拉框选中的值
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
