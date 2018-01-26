package com.example.chiangchih_yi.androidtest;

import android.app.Application;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by chiangchih-yi on 2018/1/24.
 */

public class JApplication extends Application implements StorageInterface {

    //store
    private static final String STORE_KEY_PREFS = "STORE_KEY_PREFS";
    private static final String STORE_KEY_DAILY_WORD = "STORE_KEY_DAILY_WORD";
    private static final String STORE_KEY_WORD_DEFAULT = "每日一字";
    private static final String STORE_KEY_WEEK_WEATHER = "STORE_KEY_WEEK_WEATHER";
    private SharedPreferences storePrefs;

    @Override
    public void onCreate() {
        super.onCreate();

        initStore();

        initLoader();
    }

    private void initStore() {
        storePrefs = getSharedPreferences(STORE_KEY_PREFS, MODE_PRIVATE);
    }

    private void initLoader() {
        WordLoader.getInstance().setStorageInterface(this);
        WeatherLoader.getInstance().setStorageInterface(this);
    }

    // interface method
    public void refreshWord(String word, WordLoaderInterface wordLoaderInterface) {
        if (word != null) {
            SharedPreferences.Editor e = storePrefs.edit();
            e.putString(STORE_KEY_DAILY_WORD, word);
            e.commit();

            if (wordLoaderInterface != null) {
                wordLoaderInterface.refreshWordView();
            }
        }
    }

    // interface method
    public String getWord() {
        return storePrefs.getString(STORE_KEY_DAILY_WORD, STORE_KEY_WORD_DEFAULT);
    }

    // interface method
    public void refreshWeather(Set<String> weathers, WeatherLoaderInterface weatherLoaderInterface) {
        if (weathers != null) {
            SharedPreferences.Editor e = storePrefs.edit();
            e.putStringSet(STORE_KEY_WEEK_WEATHER, weathers);
            e.commit();

            if (weatherLoaderInterface != null) {
                weatherLoaderInterface.refreshWeatherView();
            }
        }
    }

    // interface method
    public Set<String> getWeather() {
        return storePrefs.getStringSet(STORE_KEY_WEEK_WEATHER, null);
    }

}