package com.example.android.splitfeatures.workoutsplit;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.splitfeatures.Camera;
import com.example.android.splitfeatures.FeaturesActivity;
import com.example.android.splitfeatures.R;
import com.example.android.splitfeatures.Timer;
import com.example.android.splitfeatures.Utils.BottomNavigationViewHelper;
import com.example.android.splitfeatures.notes.Notes;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


public class WorkoutSplit extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    private Button btnAdd, btnExerciseLog;
    private EditText workout, sets, reps;
    private static final String TAG = "WorkoutSplit";

    /**
     * for bottom nav
     */
    private static final int ACTIVITY_NUM=1;
    private Context mContext= WorkoutSplit.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_split);
        workout = findViewById(R.id.workout);
        sets = findViewById(R.id.sets);
        reps = findViewById(R.id.reps);

        btnAdd = findViewById(R.id.btnAdd);
        btnExerciseLog = findViewById(R.id.btnView);

        mDatabaseHelper = new DatabaseHelper(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String workoutEntry = workout.getText().toString();
                String setEntry = sets.getText().toString();
                int setNum = new Integer(setEntry).intValue();
                String repEntry = reps.getText().toString();
                int repNum = new Integer(repEntry).intValue();

                if (workout.length() != 0 && sets.length() !=0 && reps.length() !=0) {
                    AddData(workoutEntry, setNum, repNum);
                    workout.setText("");
                    sets.setText("");
                    reps.setText("");
                } else {
                    toastMessage("You must put something in all fields!");
                }

            }

        });

        btnExerciseLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutSplit.this, ListData.class);
                startActivity(intent);
            }
        });

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
                        Intent intent1 = new Intent(WorkoutSplit.this, FeaturesActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_workoutsplit:

                        break;

                    case R.id.ic_timer:
                        Intent intent2 = new Intent(WorkoutSplit.this, Timer.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_camera:
                        Intent intent3 = new Intent(WorkoutSplit.this, Camera.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_notes:
                        Intent intent4 = new Intent(WorkoutSplit.this, Notes.class);
                        startActivity(intent4);
                        break;
                }


                return false;
            }
        });
    }




    public void AddData(String newWorkout, int sets, int reps) {
        boolean insertData = mDatabaseHelper.addData(newWorkout, sets, reps);

        if (insertData) {
            toastMessage("Exercise Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }


}
