package com.example.android.splitfeatures;

//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//
//public class Timer extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_timer);
//    }
//}

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Timer extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 5000;

    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private CountDownTimer mCountDownTimer;
    private CountDownTimer timer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private TextView mCircuitValue;
    private EditText mCircuitInput;
    private int numCircuitInput=-1;
    private String toStringCircuitInput="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);

        mCircuitValue = findViewById(R.id.circuitsValue);
        mCircuitInput = findViewById(R.id.circuitsInput);
        toStringCircuitInput = mCircuitInput.getText().toString();


        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toStringCircuitInput.length() == 0) {
                    try {
                        numCircuitInput = Integer.parseInt(mCircuitInput.getText().toString());

                    } catch (NumberFormatException e) {
                        toastMessage("mCircuitInput not working");
                    }

                } else {
                    numCircuitInput = 0;
                }

                    if (mTimerRunning) {
                        pauseTimer();

                    } else {
                        startTimer();
                    }

                numCircuitInput--;
                mCircuitValue.setText(String.valueOf(numCircuitInput));
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });


            updateCountDownText();



    }

    private void startTimer() {

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {

                if(numCircuitInput ==0) {
                    mTimerRunning = false;
                }
                else {
                    mTimerRunning = true;
                    mCountDownTimer.start();
                    numCircuitInput--;
                    mCircuitValue.setText(String.valueOf(numCircuitInput));
                    mButtonStartPause.setText("Start");
                    mButtonStartPause.setVisibility(View.INVISIBLE);
                    mButtonReset.setVisibility(View.VISIBLE);
                }
            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);
        mCircuitInput.setVisibility(View.GONE);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.VISIBLE);
        mCircuitInput.setVisibility(View.GONE);
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
        mCircuitInput.setVisibility(View.VISIBLE);

    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }


private void toastMessage(String message){
    Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
}


}

