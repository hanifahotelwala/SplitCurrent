package com.example.android.splitfeatures;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;

import com.example.android.splitfeatures.CameraAndPhotoLib.GalleryActivity;
import com.example.android.splitfeatures.Utils.BottomNavigationViewHelper;
import com.example.android.splitfeatures.workoutsplit.WorkoutSplit;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class Camera extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String TAG = "Camera";
    private static final int ACTIVITY_NUM=3;
    private Context mContext= Camera.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

       Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       startActivityForResult(cameraIntent,REQUEST_IMAGE_CAPTURE);
        setupBottomNavigationView();
        /**
         * cant have finish here because then gallery activity wont appear
         */
       // finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==REQUEST_IMAGE_CAPTURE){
            Log.d(TAG, "done taking a photo");

        }
        Bitmap bitmap;
        bitmap = (Bitmap)data.getExtras().get("data");
        try{
            Log.d(TAG, "onActivityResult: received new bitmap from camera: " + bitmap);
            Intent intent = new Intent(Camera.this, GalleryActivity.class);
            intent.putExtra(getString(R.string.selected_bitmap), bitmap);
            startActivity(intent);
            /**
             * prevents navigating back to the previous
             */
            finish();
        }catch (NullPointerException e){
            Log.d(TAG, "onActivityResult: NullPointerException: " + e.getMessage());
        }
    }

    /**
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        Intent intent3 = new Intent(mContext, FeaturesActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_workoutsplit:
                        Intent intent1 = new Intent(mContext, WorkoutSplit.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_timer:
                        Intent intent2 = new Intent(mContext, Timer.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_camera:

                        break;

                    case R.id.ic_photo:
                        Intent intent4 = new Intent(mContext, PhotoLibrary.class);
                        startActivity(intent4);
                        break;
                }


                return false;
            }
        });

    }


}

