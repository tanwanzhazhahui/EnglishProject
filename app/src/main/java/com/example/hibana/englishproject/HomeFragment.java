package com.example.hibana.englishproject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.englishproject.R;

public class HomeFragment extends Fragment {
    private Button start,learnword;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        //通过参数中的布局填充获取对应布局
        View view=inflater.inflate(R.layout.homefment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.start=getActivity().findViewById(R.id.startword);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),GuanQia.class);
                startActivity(intent);
            }
        });
        this.learnword=getActivity().findViewById(R.id.learnword);
        learnword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),StartLearn.class);
                startActivity(intent);
            }
        });
    }
}
