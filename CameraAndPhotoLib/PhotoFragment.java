package com.example.android.splitfeatures.CameraAndPhotoLib;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.splitfeatures.R;
import com.example.android.splitfeatures.Utils.FirebaseMethods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */

public class PhotoFragment extends Fragment {
    private static final String TAG = "PhotoFragment";
    //constant
    private static final int PHOTO_FRAGMENT_NUM = 1;
    private static final int GALLERY_FRAGMENT_NUM = 2;
    private static final int  CAMERA_REQUEST_CODE = 5;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;

    //vars
    private String mAppend = "file:/";
    private int imageCount = 0;
    private String imgUrl;
    private Bitmap bitmap;
    private Intent intent;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        Log.d(TAG, "onCreateView: started.");

        Button btnLaunchCamera = view.findViewById(R.id.btnLaunchCamera);
        btnLaunchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: launching camera.");

                if(((ShareActivity)getActivity()).getCurrentTabNumber() == PHOTO_FRAGMENT_NUM){

                    if(((ShareActivity)getActivity()).checkPermissions(Permissions.CAMERA_PERMISSION[0])){
                        Log.d(TAG, "onClick: starting camera");
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);

                    }else{
                        Intent intent = new Intent(getActivity(), ShareActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
            }
        });

        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST_CODE){
            Log.d(TAG, "onActivityResult: done taking a photo.");
            Log.d(TAG, "onActivityResult: attempting to navigate to final share screen.");
            //navigate to the final share screen to publish photo
//            /**
//             * upload image to firebase
//             */
//            if(intent.hasExtra(getString(R.string.selected_image))){
//                imgUrl = intent.getStringExtra(getString(R.string.selected_image));
//                mFirebaseMethods.uploadNewPhoto(getString(R.string.new_photo), imageCount, imgUrl,null);
//            }
//            else if(intent.hasExtra(getString(R.string.selected_bitmap))){
//                bitmap = (Bitmap) intent.getParcelableExtra(getString(R.string.selected_bitmap));
//                mFirebaseMethods.uploadNewPhoto(getString(R.string.new_photo),imageCount, null,bitmap);
//            }

            Bitmap bitmap;
            bitmap = (Bitmap)data.getExtras().get("data");
            try{
                Log.d(TAG, "onActivityResult: received new bitmap from camera: " + bitmap);
                Intent intent = new Intent(getActivity(), GalleryActivity.class);
                intent.putExtra(getString(R.string.selected_bitmap), bitmap);
                startActivity(intent);
                /**
                 * prevents navigating back to the previous
                 */
                getActivity().finish();
            }catch (NullPointerException e){
                Log.d(TAG, "onActivityResult: NullPointerException: " + e.getMessage());
            }

        }
    }

       /*
    ------------------------------------ Firebase ---------------------------------------------
     */

    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//
//
//                if (user != null) {
//                    // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                } else {
//                    // User is signed out
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                }
//                // ...
//            }
//        };


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                //retrieve user information from the database
//                setProfileWidgets(mFirebaseMethods.getUserSettings(dataSnapshot));

                //retrieve images for the user in question

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
       // mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
          //  mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}