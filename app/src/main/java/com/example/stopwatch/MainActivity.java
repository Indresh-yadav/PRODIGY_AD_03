package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int milliseconds =0;

    //IS the stopwatch running?

    private boolean running;
    private boolean wasRunning;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState!=null){
            milliseconds=savedInstanceState.getInt("milliseconds");
            running=savedInstanceState.getBoolean("running");
            wasRunning=savedInstanceState.getBoolean("wasRunning");
        }

        runTimer();

    }

    private void runTimer(){

        //Get the Textview
        final TextView timeView=findViewById(R.id.time_view);

        //Create Handler
        Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                int minutes = (milliseconds % 3600000) / 60000;
                int secs = (milliseconds % 60000) / 1000;
                long millis = milliseconds % 1000;


                String time= String.format(Locale.getDefault(),
                        "%d:0%d:0%d",
                        minutes,secs,millis);

                timeView.setText(time);

                if(running){
                    milliseconds=milliseconds+15;
                }

                handler.postDelayed(this, 1);

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning=running;
        running=false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wasRunning){
            running=true;
        }
    }

    public void onClickStart(View view) {
        running=true;
    }

    public void onClickStop(View view) {
        running=false;
    }

    public void onClickStopReset(View view) {
        running=false;
        milliseconds=0;
    }
}
