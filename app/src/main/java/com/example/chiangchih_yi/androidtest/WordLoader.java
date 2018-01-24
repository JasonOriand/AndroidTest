package com.example.chiangchih_yi.androidtest;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;

/**
 * Created by chiangchih-yi on 2018/1/24.
 */

public class WordLoader {

    private JApplication jApplication;

    private static class WordLoaderHolder {
        private static final WordLoader INSTANCE = new WordLoader();
    }

    private WordLoader(){}

    public static final WordLoader getInstance() {
        return WordLoaderHolder.INSTANCE;
    }

    public void init(JApplication jApplication) {
        this.jApplication = jApplication;
    }

    public void getDailyWord(MainActivity mainActivity) {
        new WordTask().execute(mainActivity);
    }

    private class WordTask extends AsyncTask<MainActivity, Void, String> {

        MainActivity mainActivity;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(MainActivity... params) {
            mainActivity = (MainActivity) params[0];

            String word = null;

            try {
                URL url = new URL("http://www.appledaily.com.tw/index/dailyquote/");

                Document doc =  Jsoup.parse(url, 3000);
                Elements title = doc.select("article[class=dphs]");

                word = title.get(0).text();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return word;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(result != null) {
                if(jApplication != null) {
                    jApplication.refreshWord(result);
                }
            }

            if (mainActivity != null) {
                mainActivity.refreshWordView();
            }
        }
    }
}
