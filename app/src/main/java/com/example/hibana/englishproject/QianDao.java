package com.example.hibana.englishproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.example.englishproject.R;

public class QianDao extends Activity {
    private ImageView back;
    private SignDate signDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_qiandao);
        initView();
    }

    public void initView(){
        this.back=findViewById(R.id.sd_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QianDao.this.finish();
            }
        });
        this.signDate=findViewById(R.id.signDate);
        signDate.setOnSignedSuccess(new OnSignedSuccess() {
            @Override
            public void OnSignedSuccess() {
                Log.e("wqf","Success");
            }
        });

    }
}
