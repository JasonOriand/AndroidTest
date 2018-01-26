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

    private StorageInterface storageInterface;

    private static class WordLoaderHolder {
        private static final WordLoader INSTANCE = new WordLoader();
    }

    private WordLoader(){}

    public static final WordLoader getInstance() {
        return WordLoaderHolder.INSTANCE;
    }

    public void setStorageInterface(StorageInterface storageInterface) {
        this.storageInterface = storageInterface;
    }

    public void getDailyWord(WordLoaderInterface wordLoaderInterface) {
        new WordTask().execute(wordLoaderInterface);
    }

    private class WordTask extends AsyncTask<WordLoaderInterface, Void, String> {

        WordLoaderInterface wordLoaderInterface;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(WordLoaderInterface... params) {
            wordLoaderInterface = params[0];

            String word = null;

            try {
                URL url = new URL("http://www.appledaily.com.tw/index/dailyquote/");

                Document doc =  Jsoup.parse(url, 3000);
                Elements title = doc.select("article[class=dphs]");

                word = title.get(0).text();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return word;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(result != null) {
                if(storageInterface != null) {
                    storageInterface.refreshWord(result, wordLoaderInterface);
                }
            }
        }
    }
}
