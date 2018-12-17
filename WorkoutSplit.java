package com.example.android.splitfeatures;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.splitfeatures.workoutsplit.DatabaseHelper;
import com.example.android.splitfeatures.workoutsplit.ListData;


public class WorkoutSplit extends AppCompatActivity {

    private static final String TAG = "WorkoutSplit";

    DatabaseHelper mDatabaseHelper;
    private Button btnAdd, btnExerciseLog;
    //private EditText editText;
    private EditText workout, sets, reps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_split);
        //editText = findViewById(R.id.editText);
        workout = findViewById(R.id.workout);
        sets = findViewById(R.id.sets);
        reps = findViewById(R.id.reps);

        btnAdd = findViewById(R.id.btnAdd);
        btnExerciseLog = findViewById(R.id.btnView);
        mDatabaseHelper = new DatabaseHelper(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String newEntry = editText.getText().toString();
//                if (editText.length() != 0) {
//                    AddData(newEntry);
//                    editText.setText("");
//                } else {
//                    toastMessage("You must put something in the text field!");
//                }
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

                //int setsEntry = new Integer(sets).intValue();


            }
        });

        btnExerciseLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutSplit.this, ListData.class);
                startActivity(intent);
            }
        });

    }

//    public void AddData(String newEntry,) {
//        boolean insertData = mDatabaseHelper.addData(newEntry);
//
//        if (insertData) {
//            toastMessage("Data Successfully Inserted!");
//        } else {
//            toastMessage("Something went wrong");
//        }
//    }

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



/**
 * Source: Coding w/ mitch implementation https://github.com/mitchtabian/SaveReadWriteDeleteSQLite
 */