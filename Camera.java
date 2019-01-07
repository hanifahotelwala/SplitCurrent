package com.example.android.splitfeatures;

import android.content.Context;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.android.splitfeatures.Utils.BottomNavigationViewHelper;
import com.example.android.splitfeatures.notes.Notes;
import com.example.android.splitfeatures.workoutsplit.WorkoutSplit;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class Camera extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String TAG = "Camera";


    /**
     * for bottom nav
     */
    private static final int ACTIVITY_NUM=3;
    private Context mContext= Camera.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

       Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       startActivityForResult(cameraIntent,REQUEST_IMAGE_CAPTURE);

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
                        Intent intent3 = new Intent(Camera.this, FeaturesActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_workoutsplit:
                        Intent intent1 = new Intent(Camera.this, WorkoutSplit.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_timer:
                        Intent intent2 = new Intent(Camera.this, Timer.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_camera:

                        break;

                    case R.id.ic_notes:
                        Intent intent4 = new Intent(Camera.this, Notes.class);
                        startActivity(intent4);
                        break;
                }


                return false;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==REQUEST_IMAGE_CAPTURE){
            Log.d(TAG, "done taking a photo");


        }
    }
}


