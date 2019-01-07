package com.example.android.splitfeatures.Utils;

import android.util.Log;

import com.example.android.splitfeatures.Camera;
import com.example.android.splitfeatures.FeaturesActivity;
import com.example.android.splitfeatures.Timer;
import com.example.android.splitfeatures.notes.Notes;
import com.example.android.splitfeatures.workoutsplit.WorkoutSplit;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import com.example.android.splitfeatures.R;

/**
 * coding w/ mitch
 */

public class BottomNavigationViewHelper {

    private static final String TAG = "BottomNavigationViewHel";


    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(TAG, "setupBottomNavigationView: Setting up BottomNavigationView");
        bottomNavigationViewEx.enableAnimation(false);
       // bottomNavigationViewEx.enableItemShiftingMode(false);
      //  bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.ic_home:
                        Intent intent1 = new Intent(context,FeaturesActivity.class);//ACTIVITY_NUM = 0
                        context.startActivity(intent1);
                        break;

                    case R.id.ic_workoutsplit:
                        Intent intent2  = new Intent(context, WorkoutSplit.class);//ACTIVITY_NUM = 1
                        context.startActivity(intent2);
                        break;

                    case R.id.ic_timer:
                        Intent intent3 = new Intent(context, Timer.class);//ACTIVITY_NUM = 2
                        context.startActivity(intent3);
                        break;

                    case R.id.ic_camera:
                        Intent intent4 = new Intent(context, Camera.class);//ACTIVITY_NUM = 3
                        context.startActivity(intent4);
                        break;

                    case R.id.ic_notes:
                        Intent intent5 = new Intent(context, Notes.class);//ACTIVITY_NUM = 4
                        context.startActivity(intent5);
                        break;
                }


                return false;
            }
        });
    }
}