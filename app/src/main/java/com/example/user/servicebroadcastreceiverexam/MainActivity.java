package com.example.user.servicebroadcastreceiverexam;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {
    private TextView count_TextView;
    private Button threadStartButton;
    private Button threadStopButton;
    private CountThread countThread;
    private boolean interruptSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        count_TextView = (TextView) findViewById(R.id.count_TextView);

        threadStartButton = (Button) findViewById(R.id.threadStartButton);
        threadStartButton.setOnClickListener(this::threadStartButtonClick);

        threadStopButton = (Button) findViewById(R.id.threadStopButton);
        threadStopButton.setOnClickListener(this::threadStopButtonClick);
    }

    private void threadStartButtonClick(View view) {
        countThread = new CountThread();
        countThread.start();
    }

    private void threadStopButtonClick(View view) {

        countThread.interrupt();
        countThread = null;
//        Log.i("interruptSign", String.valueOf(countThread.isInterrupted()));

    }


    class CountThread extends Thread {

        private Handler handler;


        CountThread() {
            handler = new Handler();
        }

        @Override
        public void run() {
            super.run();
            int i = 0;
            while (!this.currentThread().isInterrupted()) {
                try {

                    i++;

                    handleMessage(i);

                    this.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    this.currentThread().interrupt();
                }
            }
        }

        @Override
        public boolean isInterrupted() {
            return super.isInterrupted();
        }


        private void handleMessage(final int data) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    count_TextView.setText(String.valueOf(data));
                }
            });
        }
    }
}
