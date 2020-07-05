package com.example.hibana.englishproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.example.englishproject.R;

public class CuoTiBen extends Activity {

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cuotiben);
        initView();
    }

    public void initView(){
        this.back=findViewById(R.id.ct_close);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CuoTiBen.this.finish();
            }
        });
    }

}
