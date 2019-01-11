package com.example.android.splitfeatures.CameraAndPhotoLib;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.android.splitfeatures.R;
import com.example.android.splitfeatures.Utils.FirebaseMethods;
import com.example.android.splitfeatures.Utils.ImageAdapter;
import com.example.android.splitfeatures.workoutsplit.CustomAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */

public class GalleryFragment extends Fragment {
    private static final String TAG = "GalleryFragment";


    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;
    private StorageReference storageReference;
    private static final int NUM_GRID_COLUMNS = 3;
    int gridWidth;
    int imageWidth;




   // private GridView gridView;
    private ImageView imageview;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_gallery, container, false);
        Log.d(TAG, "onCreateView: started.");
       // gridView = view.findViewById(R.id.gridView);
        imageview = view.findViewById(R.id.imageView);
        storageReference= FirebaseStorage.getInstance().getReference();

        setupFirebaseAuth();
        setupGridView();

        return view;
    }
    private void setupGridView(){
        Log.d(TAG, "setupGridView: Setting up image grid.");

        final ArrayList<Photo> photos = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(getString(R.string.dbname_user_photos))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for ( DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
                    photos.add(singleSnapshot.getValue(Photo.class));
                    Log.d(TAG, "setupGridView: adding photos in array");
                }
               // setup our image grid
                gridWidth = getResources().getDisplayMetrics().widthPixels;
                imageWidth = gridWidth/NUM_GRID_COLUMNS;
              //  gridView.setColumnWidth(imageWidth);
                Photo photo;
                photo = new Photo();
                ArrayList<String> imgUrls = new ArrayList<String>();
                for(int i = 0; i < photos.size(); i++){
                    imgUrls.add(photos.get(i).getImage_path());

                    Picasso.get()
                            .load(photos.get(i).getImage_path())
                           // .placeholder(R.mipmap.ic_launcher)
                            .fit()
                            .centerCrop()
                            .into(imageview);

//                    Log.d(TAG, "setupGridView: obtaining image urls");
//                    mlistView.setAdapter(imgUrls);

                }



             //   GridImageAdapter adapter = new GridImageAdapter(getActivity(),R.layout.layout_grid_imageview,
                 //       "", imgUrls);
               // gridView.setAdapter(adapter);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });
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

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //retrieve user information from the database


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
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
//}