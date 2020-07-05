package com.example.hibana.englishproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import com.example.englishproject.R;

public class Loading_view extends ProgressDialog {

    public Loading_view(Context context){
        super(context);
    }

    public Loading_view(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
    }

    private void init(Context context){
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.loading);
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.width=WindowManager.LayoutParams.WRAP_CONTENT;
        params.height=WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
