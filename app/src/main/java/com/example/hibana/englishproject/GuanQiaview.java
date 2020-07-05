package com.example.hibana.englishproject;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.englishproject.R;

public class GuanQiaview extends LinearLayout {
    private TextView gq_tv;
    private RatingBar gq_rb;
    private LinearLayout gq_ly;
    //使用布局添加
    public GuanQiaview(Context context,  AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.guanqiaview,this);
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.GuanQiaview);
        initType(typedArray);

    }

    private void initType(TypedArray typedArray){
        this.gq_tv=findViewById(R.id.gq_tv);
        this.gq_rb=findViewById(R.id.gq_rb);
        this.gq_ly=findViewById(R.id.gq_ly);
        String text=typedArray.getString(R.styleable.GuanQiaview_text);
        int bg_border=typedArray.getResourceId(R.styleable.GuanQiaview_background,0);
        int rating=typedArray.getInt(R.styleable.GuanQiaview_rating,0);
        gq_tv.setText(text);
        gq_ly.setBackgroundResource(bg_border);
        gq_rb.setRating(rating);
    }

    @SuppressWarnings("unused")
    public void setText(String text){
        gq_tv.setText(text);
    }
    @SuppressWarnings("unused")
    public String getText(){
        return gq_tv.getText().toString();
    }
    @SuppressWarnings("unused")
    public void setRating(Integer num){
        gq_rb.setRating(num);
    }
    @SuppressWarnings("unused")
    public void setBackground(int resId){
        gq_ly.setBackgroundResource(resId);
    }

}
