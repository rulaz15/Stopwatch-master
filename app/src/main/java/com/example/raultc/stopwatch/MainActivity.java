package com.example.raultc.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public Button startButton;
    public Button stopButton;
    public boolean isRunning;
    public long timeStopped = 0;
    public String valueButton;
    public String elapsedTime;
    public boolean reset;
    public Chronometer chronometer;
    public ListView times;
    public ArrayAdapter<String> adapter;
    ArrayList<String> array = new ArrayList<String>();




    public void startTimer (View view){
        startButton = (Button) findViewById(R.id.startButton);
        valueButton = startButton.getText().toString();
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        times = (ListView)findViewById(R.id.timesSaved);

        if (valueButton.equals("Start")) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            isRunning = true;
            reset = true;
            startButton.setText("Reset");

        }

        else if(valueButton.equals("Reset") && reset == true){
            elapsedTime = chronometer.getText().toString();
            array.add(elapsedTime);
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, array);
            times.setAdapter(adapter);
            chronometer.stop();
            startButton.setText("Start");
            stopButton.setText("Stop");
            isRunning = false;
            chronometer.setText("00:00:00");
        }
    }



    public void stopTimer (View view) {

        stopButton = (Button) findViewById(R.id.stopButton);
        valueButton = stopButton.getText().toString();
        chronometer = (Chronometer) findViewById(R.id.chronometer);

        if (isRunning == true) {
            chronometer.stop();
            timeStopped = SystemClock.elapsedRealtime() - chronometer.getBase();
            stopButton.setText("Resume");
            isRunning = false;

        }

        else if (isRunning == false) {
            chronometer.setBase(SystemClock.elapsedRealtime() - timeStopped);
            chronometer.start();
            reset = true;
            isRunning = true;

            stopButton.setText("Stop");

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setText("00:00:00");
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

            @Override
            public void onChronometerTick(Chronometer chronometer) {
                CharSequence text = chronometer.getText();

                if (text.length()  == 5) {
                    chronometer.setText("00:"+text);
                }

                else if (text.length() == 7) {
                    chronometer.setText("0"+text);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
