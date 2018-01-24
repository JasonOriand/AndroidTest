package com.example.chiangchih_yi.androidtest;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by chiangchih-yi on 2018/1/25.
 */

public class WeatherLoader {

    private StorageInterface storageInterface;

    private static class WeatherLoaderHolder {
        private static final WeatherLoader INSTANCE = new WeatherLoader();
    }

    private WeatherLoader(){}

    public static final WeatherLoader getInstance() {
        return WeatherLoaderHolder.INSTANCE;
    }

    public void setStorageInterface(StorageInterface storageInterface) {
        this.storageInterface = storageInterface;
    }

    public void getWeekWeather(WeatherLoaderInterface WeatherLoaderInterface) {
        new WeatherTask().execute(WeatherLoaderInterface);
    }

    private class WeatherTask extends AsyncTask<WeatherLoaderInterface, Void, Set<String>> {

        WeatherLoaderInterface WeatherLoaderInterface;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Set<String> doInBackground(WeatherLoaderInterface... params) {
            WeatherLoaderInterface = (WeatherLoaderInterface) params[0];

            Set<String> weathers = null;

            try {
                URL url = new URL("http://www.cwb.gov.tw/rss/forecast/36_08.xml");

                Document doc =  Jsoup.parse(url, 3000);
                Elements description = doc.select("title:contains(臺中市 一週天氣預報)").parents().select("description");
                String descriptionString = description.get(0).text();

                String[] splitedDescriptionString = descriptionString.split("<BR>");

                weathers = new HashSet<String>();

                for (int i=0; i<splitedDescriptionString.length; i+=2) {
                    weathers.add(splitedDescriptionString[i].trim() + "\n" + splitedDescriptionString[i+1].trim());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return weathers;
        }

        @Override
        protected void onPostExecute(Set<String> result) {
            super.onPostExecute(result);

            if(result != null) {
                if(storageInterface != null) {
                    storageInterface.refreshWeather(result);
                }

                if (WeatherLoaderInterface != null) {
                    WeatherLoaderInterface.refreshWeatherView();
                }
            }
        }
    }
}
