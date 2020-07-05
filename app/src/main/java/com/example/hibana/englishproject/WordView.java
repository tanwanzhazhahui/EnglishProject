package com.example.hibana.englishproject;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.englishproject.R;

public class WordView extends LinearLayout{
    private TextView word,w_class,w_mean,w_sentence,w_interpret,w_phrase;
    private ImageView w_left,w_right;
    //使用布局添加
    public WordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.word_style, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WordView);
        initTyped(typedArray);
    }
    private void initTyped(TypedArray typedArray) {
        word=(TextView)findViewById(R.id.word);
        w_class=(TextView)findViewById(R.id.w_class);
        w_mean=(TextView)findViewById(R.id.w_mean);
        w_sentence=(TextView)findViewById(R.id.w_sentence);
        w_interpret=(TextView)findViewById(R.id.w_interpret);
        w_phrase=(TextView)findViewById(R.id.w_phrase);
        String word_text=typedArray.getString(R.styleable.WordView_word_text);
        String class_text=typedArray.getString(R.styleable.WordView_class_text);
        String mean_text=typedArray.getString(R.styleable.WordView_mean_text);
        String sentence_text=typedArray.getString(R.styleable.WordView_sentence_text);
        String interpret_text=typedArray.getString(R.styleable.WordView_interpret_text);
        String phrase_text=typedArray.getString(R.styleable.WordView_phrase_text);
        word.setText(word_text);
        w_class.setText(class_text);
        w_mean.setText(mean_text);
        w_sentence.setText(sentence_text);
        w_interpret.setText(interpret_text);
        w_phrase.setText(phrase_text);
    }
    @SuppressWarnings("unused")
    public void setWordText(String text){
        word.setText(text);
    }
    @SuppressWarnings("unused")
    public void setClassText(String text){
        w_class.setText(text);
    }
    @SuppressWarnings("unused")
    public void setMeanText(String text){
        w_mean.setText(text);
    }
    @SuppressWarnings("unused")
    public void setSentenceText(String text){
        w_sentence.setText(text);
    }
    @SuppressWarnings("unused")
    public void setInterpretText(String text){
        w_interpret.setText(text);
    }
    @SuppressWarnings("unused")
    public void setPhraseText(String text){
        w_phrase.setText(text);
    }

}
