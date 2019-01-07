package com.example.android.splitfeatures;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.android.splitfeatures.Utils.BottomNavigationViewHelper;
import com.example.android.splitfeatures.notes.NoteEditor;
import com.example.android.splitfeatures.notes.Notes;
import com.example.android.splitfeatures.workoutsplit.WorkoutSplit;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class FeaturesActivity extends AppCompatActivity  {
    private Button cameraButton;
    private Button photoLibButton;
    private Button workoutSplitButton;
    private Button timerButton, notesButton;

    private static final String TAG= "FeaturesActivity";
    private Context mContext= FeaturesActivity.this;
    private static final int ACTIVITY_NUM=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features);


        cameraButton = findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCamera();
            }
        });

        photoLibButton = findViewById(R.id.photoLibButton);
        photoLibButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPhotoLib();
            }
        });

        workoutSplitButton = findViewById(R.id.workoutSplitButton);
        workoutSplitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToWorkoutSplit();
            }
        });


        timerButton = findViewById(R.id.timerButton);
        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToTimer();
            }
        });

        notesButton = findViewById(R.id.notesButton);

        notesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNotes();
            }
        });

        setupBottomNavigationView();


    }




    /**
     * BottomNavigationView setup codingw/mitch
     */
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx =findViewById(R.id.bottomNavViewBar);
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

                        break;

                    case R.id.ic_workoutsplit:
                        Intent intent1 = new Intent(FeaturesActivity.this, WorkoutSplit.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_timer:
                        Intent intent2 = new Intent(FeaturesActivity.this, Timer.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_camera:
                        Intent intent3 = new Intent(FeaturesActivity.this, Camera.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_notes:
                        Intent intent4 = new Intent(FeaturesActivity.this, Notes.class);
                        startActivity(intent4);
                        break;
                }


                return false;
            }
        });

    }


    /**
     *
     */





    /**
     *
     */



    public void goToCamera(){
        Intent intent = new Intent(this, Camera.class);
        startActivity(intent);

    }

    public void goToNotes(){
        Intent intent = new Intent(this, Notes.class);
        startActivity(intent);

    }
    public void goToPhotoLib(){
        Intent intent = new Intent(this, PhotoLibrary.class);
        startActivity(intent);
    }

    public void goToWorkoutSplit(){
        Intent intent = new Intent(this, WorkoutSplit.class);
        startActivity(intent);

    }

    public void goToTimer()
    {
        Intent intent = new Intent(this, Timer.class);
        startActivity(intent);
    }


}
