package com.example.chiangchih_yi.androidtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements LoaderInterface {

    private StorageInterface storageInterface;

    //view
    private TextView wordTextView;
    private Button wordButton;
    private Button weatherButton;
    private Button editButton;

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

        weatherButton=(Button)findViewById(R.id.weatherButton);
        editButton=(Button)findViewById(R.id.editButton);

        this.refreshView();
    }

    // interface method
    public void refreshView() {
        String word = this.storageInterface.getWord();

        if (word != null && !word.equalsIgnoreCase(wordTextView.getText().toString())) {
            wordTextView.setText(word);

            Log.i("Word refresh", word);
        }

        if (!wordButton.isEnabled()) {
            wordButton.setEnabled(true);
        }
    }
}
