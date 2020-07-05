package com.example.hibana.englishproject;

import App.vo.Word;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.englishproject.R;
import com.example.hibana.englishproject.ConnectUtils.UserUtils;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.ArrayList;
import java.util.List;

public class StartLearn extends Activity {
    private ViewPager xianshi;
    private ImageView upper, under, sl_close;
    private int mCurrentViewID = 0;
    private String guanqia;
    private TestPager testPager;
    private ViewGroup container;
    private AttributeSet attrs;
    private WordView wv;
    private ObjectMapper mapper=new ObjectMapper();
    //handler消息类型;
    private final int GETWORDSSUCCESS=66;
    private final int ERROR=0;

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GETWORDSSUCCESS:
                    //获得单词数组
                    try {
                        List<Word> wordList=getWordList(msg.obj.toString());
                        testPager=new TestPager(wordList);
                        xianshi.setAdapter(testPager);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case ERROR:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startlean);
        initView();
        initData();
    }

    public void initView(){
        Intent intent=getIntent();
        guanqia=intent.getStringExtra("guan");
        xianshi=(ViewPager)findViewById(R.id.xianshiword);
        sl_close=findViewById(R.id.sl_close);
        sl_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartLearn.this.finish();
            }
        });
    }

    public void initData(){
        SharedPreferences preferences=getSharedPreferences("user",MODE_PRIVATE);
        final String phonenumber=preferences.getString("phonenumber",null);
        new Thread(){
            @Override
            public void run() {
                String result= UserUtils.getTenBookWords(phonenumber);
                Message message=Message.obtain();
                message.what=GETWORDSSUCCESS;
                message.obj=result;
                handler.sendMessage(message);
            }
        }.start();
    }

    public List<Word> getWordList(String result) throws Exception{
        JavaType javaType=getCollectionType(ArrayList.class,Word.class);
        return  mapper.readValue(result,javaType);
    }

    public JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

}
