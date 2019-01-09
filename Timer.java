package com.example.android.splitfeatures;


import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.splitfeatures.Utils.BottomNavigationViewHelper;
import com.example.android.splitfeatures.workoutsplit.WorkoutSplit;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.Locale;
import java.util.concurrent.TimeUnit;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Timer extends AppCompatActivity {

    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private CountDownTimer mCountDownTimer;
    private CountDownTimer restCountDown;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis;
    private long mRestTimeInMillis;

    private TextView mCircuitValue;
    private TextView mCircuits;
    private EditText mCircuitInput;
    private int numCircuitInput=-1;
    private String toStringCircuitInput="";

    private EditText mCircuitLength;
    private long numCircuitLength;
    private EditText mRestPeriod;
    private long numRestLength;

    private static final String TAG = "Timer";
    private static final int ACTIVITY_NUM=2;
    private Context mContext= Timer.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final MediaPlayer templebell= MediaPlayer.create(Timer.this,R.raw.templebell);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);

        mCircuitValue = findViewById(R.id.circuitsValue);
        mCircuitInput = findViewById(R.id.circuitsInput);
        toStringCircuitInput = mCircuitInput.getText().toString();

        mCircuitLength = findViewById(R.id.circuitLength);
        mCircuits = findViewById(R.id.circuits);
        mRestPeriod = findViewById(R.id.restLength);


        mCircuitValue.setVisibility(GONE);
        mCircuits.setVisibility(GONE);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toStringCircuitInput.length() == 0) {
                    try {
                        numCircuitInput = Integer.parseInt(mCircuitInput.getText().toString());
                        numCircuitLength = Long.parseLong(mCircuitLength.getText().toString());
                        mTimeLeftInMillis = numCircuitLength * 1000;
                        numRestLength = Long.parseLong(mRestPeriod.getText().toString());
                        mRestTimeInMillis = numRestLength * 1000;

                    } catch (NumberFormatException e) {
                        toastMessage("mCircuitInput not working");
                    }

                } else {
                    numCircuitInput = 0;
                }

                if (mTimerRunning) {
                    pauseTimer();

                } else {
                    if(numCircuitInput !=0) {

                        templebell.start();
                        startTimer();
                        numCircuitInput--;
                        mCircuitValue.setText(String.valueOf(numCircuitInput));
                    }

                    }
            }

        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
            updateCountDownText();

        setupBottomNavigationView();

    }

    /**
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent intent1 = new Intent(Timer.this, FeaturesActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_workoutsplit:
                        Intent intent2 = new Intent(Timer.this, WorkoutSplit.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_timer:

                        break;

                    case R.id.ic_camera:
                        Intent intent3 = new Intent(Timer.this, Camera.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_photo:
                        Intent intent4 = new Intent(Timer.this, PhotoLibrary.class);
                        startActivity(intent4);
                        break;
                }


                return false;
            }
        });
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
                final MediaPlayer buzzer= MediaPlayer.create(Timer.this,R.raw.buzzer);
                final MediaPlayer templebell= MediaPlayer.create(Timer.this,R.raw.templebell);
                final MediaPlayer cheer= MediaPlayer.create(Timer.this,R.raw.cheer);


                if (numCircuitInput!=0) {
                    try {
                       buzzer.start();
                        TimeUnit.SECONDS.sleep(numRestLength);
                      //  toastMessage("Resting!!");

                    }
                    catch(InterruptedException e)
                    {
                        toastMessage("Resting not working");
                    }

                    numCircuitInput--;
                    mCircuitValue.setText(String.valueOf(numCircuitInput));
                   templebell.start();
                    start();
                }
                else {
                    cheer.start();
                    finish();
                }
                mTimerRunning=false;
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
                }
        }.start();
        mTimerRunning = true;
        mButtonStartPause.setText("Stop");
        mButtonReset.setVisibility(View.INVISIBLE);
        mCircuitInput.setVisibility(View.GONE);
        mCircuitValue.setVisibility(VISIBLE);
        mCircuits.setVisibility(VISIBLE);
        mCircuitLength.setVisibility(GONE);
        mRestPeriod.setVisibility(GONE);
    }


    private void pauseTimer() {
       mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Start over");
        mButtonReset.setVisibility(VISIBLE);
        mCircuitInput.setVisibility(GONE);
        mCircuitLength.setVisibility(GONE);
        mCircuitValue.setVisibility(VISIBLE);
        mCircuits.setVisibility(VISIBLE);
    }

    private void resetTimer() {
        mTimeLeftInMillis = numCircuitLength;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(VISIBLE);
        mCircuitInput.setVisibility(VISIBLE);
        mCircuitLength.setVisibility(VISIBLE);
        mRestPeriod.setVisibility(VISIBLE);
        mCircuitValue.setVisibility(GONE);
        mCircuits.setVisibility(GONE);

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
