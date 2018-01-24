package com.example.chiangchih_yi.androidtest;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by chiangchih-yi on 2018/1/24.
 */

public class JApplication extends Application {

    //model
    private static final String STORE_KEY_DAILY_WORD = "STORE_KEY_DAILY_WORD";
    private static final String STORE_KEY_WORD_PREFS = "STORE_KEY_WORD_PREFS";
    private static final String STORE_KEY_WORD_DEFAULT = "每日一字";
    private SharedPreferences wordPrefs;

    @Override
    public void onCreate() {
        super.onCreate();

        initModel();

        initLoader();
    }

    private void initModel() {
        wordPrefs = getSharedPreferences(STORE_KEY_WORD_PREFS, MODE_PRIVATE);
    }

    private void initLoader() {
        WordLoader.getInstance().init(this);
    }

    public void refreshWord(String word) {
        if (word != null) {
            SharedPreferences.Editor e = wordPrefs.edit();
            e.putString(STORE_KEY_DAILY_WORD, word);
            e.commit();
        }
    }

    public String getWord() {
        return wordPrefs.getString(STORE_KEY_DAILY_WORD, STORE_KEY_WORD_DEFAULT);
    }
}