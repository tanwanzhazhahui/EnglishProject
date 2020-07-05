package com.example.hibana.englishproject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.englishproject.R;
import com.example.hibana.englishproject.ConnectUtils.UserUtils;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class PersonalFragment extends Fragment {
    private ImageView avator;
    private Bitmap bitmap;
    private LinearLayout pcenter,qiandao,qianbao,shezhi,cuoti;
    private TextView username;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pfragment,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    public void initView(){
        this.avator=getActivity().findViewById(R.id.avator);
        avator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),PersonalCenter.class));
                getActivity().overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });
        this.username=getActivity().findViewById(R.id.username);
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),PersonalCenter.class));
                getActivity().overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });
        this.pcenter=getActivity().findViewById(R.id.pcenter1);
        pcenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),PersonalCenter.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });
        this.qiandao=getActivity().findViewById(R.id.qiandaolay);
        qiandao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),QianDao.class);
                startActivity(intent);
            }
        });
        this.qianbao=getActivity().findViewById(R.id.qianbao);
        qianbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Purse.class);
                startActivity(intent);
            }
        });
        this.shezhi=getActivity().findViewById(R.id.p_set);
        shezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Set.class);
                startActivity(intent);
            }
        });
        this.cuoti=getActivity().findViewById(R.id.cuoti);
        cuoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),CuoTiBen.class);
                startActivity(intent);
            }
        });
    }

    public void initData(){
        SharedPreferences preferences=getActivity().getSharedPreferences("user",Context.MODE_PRIVATE);
        final String phonenumber=preferences.getString("phonenumber",null);
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
            //设置TextView
            this.username.setText(username);
            initAvator(avator);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
