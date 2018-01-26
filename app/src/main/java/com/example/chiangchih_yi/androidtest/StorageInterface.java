package com.example.chiangchih_yi.androidtest;

import java.util.Set;

/**
 * Created by chiangchih-yi on 2018/1/24.
 */

public interface StorageInterface {

    public void refreshWord(String word, WordLoaderInterface wordLoaderInterface);
    public String getWord();

    public void refreshWeather(Set<String> weathers, WeatherLoaderInterface weatherLoaderInterface);
    public Set<String> getWeather();

}
