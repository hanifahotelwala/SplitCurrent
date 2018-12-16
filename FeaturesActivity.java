package com.example.android.splitfeatures;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class FeaturesActivity extends AppCompatActivity {
    private Button cameraButton;
    private Button photoLibButton;
    private Button workoutSplitButton;
    private Button timerButton;


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

    }


    public void goToCamera(){
        Intent intent = new Intent(this, Camera.class);
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
