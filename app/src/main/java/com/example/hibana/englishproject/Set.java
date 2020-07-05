package com.example.hibana.englishproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.example.englishproject.R;

public class Set extends AppCompatActivity {
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_set);
        initView();
    }

    public void initView(){
        this.back=findViewById(R.id.set_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Set.this.finish();
            }
        });
    }
}
