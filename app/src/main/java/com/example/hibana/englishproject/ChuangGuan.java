package com.example.hibana.englishproject;

import App.vo.ErrorWord;
import App.vo.Word;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.englishproject.R;
import com.example.hibana.englishproject.ConnectUtils.UserUtils;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

public class ChuangGuan extends Activity {

    private TextView examword;
    private LinearLayout exam;
    private Button bn,next;
    private ImageView cg_close;
    private List list1;
    private Integer index,sq=0;
    private float num=5;
    private boolean isoncl=true;
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
                    Map<String,String> wordMap=(Map<String, String>)msg.obj;
                    String result1=wordMap.get("result1");
                    String result2=wordMap.get("result2");
                    try {
                        List<Word> rwords=getWordList(result1);
                        List<ErrorWord> ewords=getErrorWordList(result2);
                        CGWord(rwords,ewords);
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
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chuangguan);
        initView();
        initWords();
    }

    public void initView(){
        this.examword=(TextView)findViewById(R.id.examword);
        this.exam=(LinearLayout)findViewById(R.id.exam);
        this.cg_close=(ImageView)findViewById(R.id.cg_close);
        cg_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChuangGuan.this.finish();
            }
        });
        this.next=(Button)findViewById(R.id.next);
    }



    public void CGWord(final List<Word>  rwords, final List<ErrorWord> ewords){
        next.setEnabled(false);
        final Word word=rwords.get(sq);
        examword.setText(word.getWord());//这里放置数据库提取出来的单词
        int suijishu1=(int)(Math.random()*30);
        int suijishu2=(int)(Math.random()*30);
        int suijishu3=(int)(Math.random()*30);
        ErrorWord errorword1=ewords.get(suijishu1);
        ErrorWord errorword2=ewords.get(suijishu2);
        ErrorWord errorword3=ewords.get(suijishu3);
        final String r1=word.getType()+"  "+word.getMean();
        final String r2=errorword1.getType()+"  "+errorword1.getMean();
        final String r3=errorword2.getType()+"  "+errorword2.getMean();
        final String r4=errorword3.getType()+"  "+errorword3.getMean();
        String res[]=new String[]{r1,r2,r3,r4};//这里放置数据库提取出来的单词意思，其余三个意思可以通过数据库随机提取其余单词的意思但不得与当前单词的意思重复
        list1= Arrays.asList(res);
        Collections.shuffle(list1);//将数组打乱
        for (int i = 0 ; i < exam.getChildCount(); i++) {//遍历button
            bn = (Button) exam.getChildAt(i);
            bn.setText(list1.get(i).toString());
            bn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isoncl){
                        switch (view.getId()) {
                            case R.id.cg_bn1:
                                index = 0;
                                break;
                            case R.id.cg_bn2:
                                index = 1;
                                break;
                            case R.id.cg_bn3:
                                index = 2;
                                break;
                            case R.id.cg_bn4:
                                index = 3;
                                break;
                        }
                        bn = (Button) exam.getChildAt(index);//获取当前点击的button对象
                        if (bn.getText().toString().equals(r1)) {//如果答对了
                            //bn.append(Html.fromHtml("<b><font color='red'>√</font></b>"));
                            bn.setBackground(getResources().getDrawable(R.drawable.bg_green));
                            bn.setTextColor(getResources().getColor(R.color.white));
                            Toast toast = Toast.makeText(ChuangGuan.this, "恭喜您！答对了", Toast.LENGTH_SHORT);
                            toast.show();
                            sq++;
                        } else {
                            sq++;
                            num--;
                            //bn.append(Html.fromHtml("<b><font color='red'>×</font></b>"));
                            bn.setBackground(getResources().getDrawable(R.drawable.bg_red));
                            bn.setTextColor(getResources().getColor(R.color.white));
                            Toast toast = Toast.makeText(getApplicationContext(), "抱歉！您答错了\n正确答案为："+word.getMean(), Toast.LENGTH_SHORT);
                            toast.show();
                            //这里可以将错误的单词(即：examword.getText().toString())存进错题本//
                        }
                        isoncl=false;
                        next.setEnabled(true);
                        if (sq < 10) {
                            next.setText("下一个");
                            next.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    bn.setBackground(getResources().getDrawable(R.drawable.border));
                                    bn.setTextColor(getResources().getColor(R.color.black));
                                    isoncl=true;
                                    CGWord(rwords,ewords);
                                }
                            });
                        } else {
                            Toast toast1 = Toast.makeText(ChuangGuan.this, "闯关结束"+num, Toast.LENGTH_SHORT);//num指的是对了几个
                            toast1.show();
                            //这里则可以将当前关卡的的星星数存进数据库，星星数则可以通过num来判断，假如num==5则三星，=4则两星，=3则一星，其余为0星//
                            ChuangGuan.this.finish();
                            Intent intent=new Intent(ChuangGuan.this,GuanQia.class);
                            String str=new String(String.valueOf(num));
                            startActivity(intent);
                        }
                    }
                    else{

                    }
                }
            });

        }

    }

    public void initWords(){
        SharedPreferences preferences=getSharedPreferences("user",MODE_PRIVATE);
        final String phonenumber=preferences.getString("phonenumber",null);
        new Thread(){
            @Override
            public void run() {
                String result1= UserUtils.getTenBookWords(phonenumber);
                String result2=UserUtils.getThirtyErrorWords(phonenumber);
                Map<String,String> wordMap=new HashMap<String, String>();
                wordMap.put("result1",result1);
                wordMap.put("result2",result2);
                Message message=Message.obtain();
                message.what=GETWORDSSUCCESS;
                message.obj=wordMap;
                handler.sendMessage(message);
            }
        }.start();
    }



    public List<Word> getWordList(String result) throws Exception{
        JavaType javaType=getCollectionType(ArrayList.class,Word.class);
        return mapper.readValue(result,javaType);
    }

    public List<ErrorWord> getErrorWordList(String result) throws Exception{
        JavaType javaType=getCollectionType(ArrayList.class, ErrorWord.class);
        return mapper.readValue(result,javaType);
    }

    public JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

}
