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

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Timer extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 5000;

    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private CountDownTimer mCountDownTimer;
    private CountDownTimer restCountDown;
    private boolean mTimerRunning;
    //private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private long mTimeLeftInMillis;
    private long mRestTimeInMillis;

    private TextView mCircuitValue;
    private TextView mCircuits;
    private EditText mCircuitInput;
    private int numCircuitInput=-1;
    private String toStringCircuitInput="";

    private EditText mCircuitLength;
    private long numCircuitLength;
   // private long numCircuitLengthMillis;
    private EditText mRestPeriod;
    private long numRestLength;





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
                    // startTimer();
//                        int x=numCircuitInput;
//                       // if(x%2==0) {
                    for (int y = numCircuitInput * 2; y > 0; y--) {
                        if (y % 2 == 0) {
                            restTimer();

                            toastMessage("even!");
                            toastMessage("current:" + y);

                        }
                        else  {
                            startTimer();
                            mCountDownTimer.start();
                            toastMessage("odd!");
                            toastMessage("current:" + y);
                        }

                        }
                    //}


                       // }

//                        else {
//                            for(int y=x*2; y>0; y--)
//                            {
//                                if
//                            }
//                        }



//                    startTimer();
//                        for(int x=numCircuitInput*2; x>0; x--) {
//                            if(x%2==0)
//                            {
//                                start
//                            }
//                           startTimer();
//                            for (int y = numCircuitInput - 1; y > 0; y-= 2) {
//                                restTimer();
//                                continue;
//                            }
                            numCircuitInput--;
                            mCircuitValue.setText(String.valueOf(numCircuitInput));
                        }
                    }
                /**
                where the value of No. Circuit is decreasing
                 */
               // numCircuitInput--;
               // mCircuitValue.setText(String.valueOf(numCircuitInput));

        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
            updateCountDownText();

    }

//    private void startTimer() {
//        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//               mTimeLeftInMillis = millisUntilFinished;
//               updateCountDownText();
//               mCircuitValue.setVisibility(VISIBLE);
//               mCircuits.setVisibility(VISIBLE);
//            }
//
//            @Override
//            public void onFinish() {
//                if(numCircuitInput ==0) {
//                    mTimerRunning = false;
//                    mCountDownTimer.cancel();
//                    restCountDown.cancel();
//                }
//                else {
//                    mTimerRunning = true;
//                        restCountDown = new CountDownTimer(mRestTimeInMillis, 1000) {
//                            @Override
//                            public void onTick(long millisUntilFinished) {
//                                mRestTimeInMillis = millisUntilFinished;
//                                updateCountDownTextRest();
//                                mCircuitValue.setVisibility(VISIBLE);
//                                mCircuits.setVisibility(VISIBLE);
//                            }
//                            @Override
//                            public void onFinish() {
//                                mCountDownTimer.start();
//                                mButtonStartPause.setText("Start");
//                                mButtonStartPause.setVisibility(View.INVISIBLE);
//                                mButtonReset.setVisibility(VISIBLE);
//                                mCircuitValue.setVisibility(VISIBLE);
//                                mCircuits.setVisibility(VISIBLE);
//
//                            }
//                        }.start();
//                        numCircuitInput--;
//                        //mCountDownTimer.start();
//                    //restCountDown.start();
//                    mCircuitValue.setText(String.valueOf(numCircuitInput));
//                    mButtonStartPause.setText("Start");
//                    mButtonStartPause.setVisibility(View.INVISIBLE);
//                    mButtonReset.setVisibility(VISIBLE);
//                    mCircuitValue.setVisibility(VISIBLE);
//                    mCircuits.setVisibility(VISIBLE);
//                }
//
//            }
//        }.start();
//
//
//
//      //  mTimerRunning = true;
//        mButtonStartPause.setText("pause");
//        mButtonReset.setVisibility(View.INVISIBLE);
//        mCircuitInput.setVisibility(GONE);
//        mCircuitLength.setVisibility(GONE);
//    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
              // if(mTimerRunning==false) {
                    //restCountDown.start();
               // }
                //else {
                   // mTimerRunning = true;
                  restCountDown.start();
                   // numCircuitInput--;
                   // mCircuitValue.setText(String.valueOf(numCircuitInput));
                    mButtonStartPause.setText("Start");
                    mButtonStartPause.setVisibility(View.INVISIBLE);
                    mButtonReset.setVisibility(View.VISIBLE);
                }
          // }
        }.start();
        //mTimerRunning = true;
        mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);
        mCircuitInput.setVisibility(View.GONE);
    }

    private void restTimer(){
        restCountDown = new CountDownTimer(mRestTimeInMillis, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                mRestTimeInMillis = millisUntilFinished;
                                updateCountDownTextRest();
                                mCircuitValue.setVisibility(VISIBLE);
                                mCircuits.setVisibility(VISIBLE);
                               // mTimerRunning=false;
                            }
                            @Override
                            public void onFinish() {
                               // mTimerRunning=true;

//                                if(numCircuitInput==0)
//                                    mTimerRunning=true;
//                                else {
//                                    mTimerRunning=false;
//                                    startTimer();
                              //  mCountDownTimer.start();
                                   //restCountDown.start();
                                    mCountDownTimer.start();
                                    mButtonStartPause.setText("Start");
                                    mButtonStartPause.setVisibility(View.INVISIBLE);
                                    mButtonReset.setVisibility(VISIBLE);
                                   // mCircuitValue.setVisibility(VISIBLE);
                                   // mCircuits.setVisibility(VISIBLE);

                                }
                            //}
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
        mButtonReset.setVisibility(VISIBLE);
        mCircuitInput.setVisibility(GONE);
        mCircuitLength.setVisibility(GONE);
        mCircuitValue.setVisibility(VISIBLE);
        mCircuits.setVisibility(VISIBLE);

    }

    private void resetTimer() {
        mRestTimeInMillis = numRestLength;
        mTimeLeftInMillis = numCircuitLength;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(VISIBLE);
        mCircuitInput.setVisibility(VISIBLE);
        mCircuitLength.setVisibility(VISIBLE);
        mCircuitValue.setVisibility(GONE);
        mCircuits.setVisibility(GONE);

    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void updateCountDownTextRest(){
        int minutesRest = (int) (mRestTimeInMillis / 1000) / 60;
        int secondsRest = (int) (mRestTimeInMillis / 1000) % 60;
        String timeLeftFormattedRest = String.format(Locale.getDefault(), "%02d:%02d", minutesRest, secondsRest);
        mTextViewCountDown.setText(timeLeftFormattedRest);

    }

private void toastMessage(String message){
    Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
}


}

