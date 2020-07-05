package com.example.hibana.englishproject;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.englishproject.R;
import com.example.hibana.englishproject.ConnectUtils.DateUtil;

public class SignDate extends LinearLayout {
    private TextView tvYear;
    private InnerGridView gvWeek;
    private InnerGridView gvDate;
    private AdapterDate adapterDate;


    public SignDate(Context context) {
        super(context);
        init();
    }

    public SignDate(Context context,AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SignDate(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        View view = View.inflate(getContext(),R.layout.layout_signdate,this);
        tvYear = view.findViewById(R.id.tvYear);
        gvWeek = view.findViewById(R.id.gvWeek);
        gvDate = view.findViewById(R.id.gvDate);
        tvYear.setText(DateUtil.getCurrentYearAndMonth());
        gvWeek.setAdapter(new AdapterWeek(getContext()));
        adapterDate = new AdapterDate(getContext());
        gvDate.setAdapter(adapterDate);
    }

    /**
     * 签到成功的回调
     * @param onSignedSuccess
     */
    public void setOnSignedSuccess(OnSignedSuccess onSignedSuccess){
        adapterDate.setOnSignedSuccess(onSignedSuccess);
    }
}
