package com.example.chiangchih_yi.androidtest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements WordLoaderInterface, WeatherLoaderInterface {

    private StorageInterface storageInterface;

    //view
    private TextView wordTextView;
    private Button wordButton;
    private Button weatherButton;
    private ListView weatherListView;
    private ArrayAdapter<String> weatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.storageInterface = (StorageInterface)getApplication();

        initView();
    }

    private void initView() {
        wordTextView=(TextView)findViewById(R.id.wordTextView);

        wordButton=(Button)findViewById(R.id.wordButton);
        wordButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                v.setEnabled(false);

                WordLoader.getInstance().getDailyWord(MainActivity.this);
            }
        });

        weatherListView=(ListView) findViewById(R.id.weatherListView);
        weatherListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("刪除此筆資料?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                weatherAdapter.remove(weatherAdapter.getItem(position));
                                weatherAdapter.notifyDataSetChanged();

                                Set<String> tempWeather = new HashSet<String>();
                                for(int i=0 ; i<weatherAdapter.getCount() ; i++){
                                    tempWeather.add(weatherAdapter.getItem(i));
                                }
                                storageInterface.refreshWeather(tempWeather, null);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();

                return false;
            }
        });

        weatherButton=(Button)findViewById(R.id.weatherButton);
        weatherButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                v.setEnabled(false);

                WeatherLoader.getInstance().getWeekWeather(MainActivity.this);
            }
        });

        this.refreshWordView();
        this.refreshWeatherView();
    }

    // interface method
    public void refreshWordView() {
        String word = this.storageInterface.getWord();

        if (word != null && !word.equalsIgnoreCase(wordTextView.getText().toString())) {
            wordTextView.setText(word);

            Log.i("Word refresh", word);
        }

        if (!wordButton.isEnabled()) {
            wordButton.setEnabled(true);
        }
    }

    // interface method
    public void refreshWeatherView() {
        Set<String> weathers = this.storageInterface.getWeather();

        if (weathers != null && weathers.size() > 0) {
            List<String> weathersList = new ArrayList<String>(weathers);
            Collections.sort(weathersList);

            weatherAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, weathersList);
            weatherListView.setAdapter(weatherAdapter);
            weatherAdapter.notifyDataSetChanged();
        }

        if (!weatherButton.isEnabled()) {
            weatherButton.setEnabled(true);
        }
    }
}
