package com.example.hibana.englishproject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.englishproject.R;

public class MainActivity extends Activity {
    LinearLayout home,course,personal;
    ImageView ivc,ivh,ivp;
    TextView tvc,tvh,tvp,maintv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initClick();
    }

    public void initView(){

        this.home=findViewById(R.id.home);
        this.course=findViewById(R.id.course);
        this.personal=findViewById(R.id.personal);

        this.ivc=findViewById(R.id.ivcourse);
        this.ivh=findViewById(R.id.ivhome);
        this.ivp=findViewById(R.id.ivpersonal);

        this.tvc=findViewById(R.id.tvcourse);
        this.tvh=findViewById(R.id.tvhome);
        this.tvp=findViewById(R.id.tvpersonal);
        this.maintv=findViewById(R.id.mainname);

        FragmentManager fragmentManager= getFragmentManager();
        //1、在系统中原生的Fragment是通过getFragmentManager获得的。
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //2.开启一个事务，通过调用beginTransaction方法开启。
        PersonalFragment fragment=new PersonalFragment();
        //3.用自己创建好的fragment1类创建一个对象
        transaction.replace(R.id.main_Layout,fragment);
        //4.向容器内加入Fragment，一般使用add或者replace方法实现，需//要传入容器的id和Fragment的实例。
        transaction.commit();

    }

    public void initClick(){
       home.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ivc.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.mipmap.course));
               tvc.setTextColor(getResources().getColor(R.color.black));
               ivh.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.mipmap.home1));
               tvh.setTextColor(getResources().getColor(R.color.blue));
               ivp.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.mipmap.person));
               tvp.setTextColor(getResources().getColor(R.color.black));
               maintv.setText("");
               FragmentManager fragmentManager= getFragmentManager();
               //1、在系统中原生的Fragment是通过getFragmentManager获得的。
               FragmentTransaction transaction=fragmentManager.beginTransaction();
               //2.开启一个事务，通过调用beginTransaction方法开启。
               HomeFragment fragment=new HomeFragment();
               //3.用自己创建好的fragment1类创建一个对象
               transaction.replace(R.id.main_Layout,fragment);
               //4.向容器内加入Fragment，一般使用add或者replace方法实现，需//要传入容器的id和Fragment的实例。
               transaction.commit();

           }
       });

       course.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ivh.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.mipmap.home));
               tvh.setTextColor(getResources().getColor(R.color.black));
               ivc.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.mipmap.course1));
               tvc.setTextColor(getResources().getColor(R.color.blue));
               ivp.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.mipmap.person));
               tvp.setTextColor(getResources().getColor(R.color.black));
               maintv.setText("课程搜索");
               FragmentManager fragmentManager= getFragmentManager();
               //1、在系统中原生的Fragment是通过getFragmentManager获得的。
               FragmentTransaction transaction=fragmentManager.beginTransaction();
               //2.开启一个事务，通过调用beginTransaction方法开启。
               CourseFragment fragment=new CourseFragment();
               //3.用自己创建好的fragment1类创建一个对象
               transaction.replace(R.id.main_Layout,fragment);
               //4.向容器内加入Fragment，一般使用add或者replace方法实现，需//要传入容器的id和Fragment的实例。
               transaction.commit();
           }
       });

       personal.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ivp.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.mipmap.person1));
               tvp.setTextColor(getResources().getColor(R.color.blue));
               ivc.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.mipmap.course));
               tvc.setTextColor(getResources().getColor(R.color.black));
               ivh.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.mipmap.home));
               tvh.setTextColor(getResources().getColor(R.color.black));
               maintv.setText("");
               FragmentManager fragmentManager= getFragmentManager();
               //1、在系统中原生的Fragment是通过getFragmentManager获得的。
               FragmentTransaction transaction=fragmentManager.beginTransaction();
               //2.开启一个事务，通过调用beginTransaction方法开启。
               PersonalFragment fragment=new PersonalFragment();
               //3.用自己创建好的fragment1类创建一个对象
               transaction.replace(R.id.main_Layout,fragment);
               //4.向容器内加入Fragment，一般使用add或者replace方法实现，需//要传入容器的id和Fragment的实例。
               transaction.commit();
           }
       });
    }


}
