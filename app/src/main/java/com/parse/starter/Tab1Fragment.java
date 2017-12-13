package com.parse.starter;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Onique on 2017-11-29.
 */

public class Tab1Fragment extends Fragment {
    public static final String TAG = "Tab1Fragment";
    private EditText min, sec;
    private TextView time;
    private Button start, stop, reset;
    private long millisec;
    private long minutes;
    private CountDownTimer countDownTimer;
    private View view;
    private boolean checkRun = false;
    private String setButton = "START";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab1_fragment,container,false);
        setupUIViews();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setButton.equals("START")){

                    setButton = "STOP";



                    if(checkRun == false) {
                        String temp = min.getText().toString();
                        minutes = (Long.parseLong(temp) * 60000);
                        temp = sec.getText().toString();
                        millisec = (Long.parseLong(temp) * 1000) + minutes;
                        checkRun = true;


                        countDownTimer = new CountDownTimer(millisec, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                millisec = millisUntilFinished;
                                updateTimer();

                            }

                            @Override
                            public void onFinish() {
                                time.setText("FINISHED!");
                                MediaPlayer mplayer = MediaPlayer.create(getContext(), R.raw.metronome);
                                mplayer.start();
                                checkRun = false;


                            }
                        }.start();
                    }
                }
                else {//FIX THIS SO THAT IT CONTINUES FROM CURRENT TIME
                    checkRun = false;
                    countDownTimer.cancel();
                    setButton = "START";
                }
                start.setText(setButton);


            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(setButton.equals("CONTINUE")){

                        countDownTimer.start();
                        setButton = "STOP";

                    }
                else {
                    checkRun = false;
                    countDownTimer.cancel();
                    setButton = "CONTINUE";
                }
                stop.setText(setButton);


            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.start();
            }
        });
        return view;
    }
    private void setupUIViews() {
        time = (TextView)view.findViewById(R.id.timer);
        min  = (EditText)view.findViewById(R.id.minutes);
        sec = (EditText)view.findViewById(R.id.seconds);
        start = (Button)view.findViewById(R.id.startbtn);
        stop = (Button)view.findViewById(R.id.stopbtn);
        reset = (Button)view.findViewById(R.id.resetbtn);
    }


    private void updateTimer() {
        int minute = (int)millisec/60000;
        int second = (int)millisec%60000/1000;
        String temp;
        temp = "" + minute + ":";
        if(second < 10){
            temp += "0";
        }
        temp += second;
        time.setText(temp);

    }

    public void onStop(View view1){

    }
}
