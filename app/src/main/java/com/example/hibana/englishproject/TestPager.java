package com.example.hibana.englishproject;

import App.vo.Word;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

public class TestPager extends PagerAdapter {
    private WordView wv;
    private AttributeSet attrs;
    private List<Word> wordList;

    public TestPager(List<Word> wordList){
        this.wordList=wordList;
    }

    @Override
    public void startUpdate(@NonNull ViewGroup container) {
        super.startUpdate(container);
    }

    @Override
    public int getCount() {
        //设置显示几个单词页面
        return 10;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public WordView instantiateItem(@NonNull ViewGroup container, int position) {
        wv = new WordView(container.getContext(),attrs);
        //-----放单词的信息---//
        wv.setWordText(wordList.get(position).getWord());//单词
        wv.setClassText(wordList.get(position).getType());//单词词性
        wv.setMeanText(wordList.get(position).getMean());//单词意思
        wv.setSentenceText(wordList.get(position).getSentence());//单词例句
        wv.setInterpretText(wordList.get(position).getInterpret());//单词例句意思
        wv.setPhraseText(wordList.get(position).getPhrase());//单词短语搭配
        //------//
        container.addView(wv);
        // 返回填充的View对象
        return wv;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
