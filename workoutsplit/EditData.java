package com.example.android.splitfeatures.workoutsplit;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.splitfeatures.Camera;
import com.example.android.splitfeatures.FeaturesActivity;
import com.example.android.splitfeatures.PhotoLibrary;
import com.example.android.splitfeatures.R;
import com.example.android.splitfeatures.Timer;
import com.example.android.splitfeatures.Utils.BottomNavigationViewHelper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * for workoutsplit
 */

public class EditData extends AppCompatActivity {
    private static final String TAG = "EditData";

    private Button btnSave,btnDelete;
    private EditText editable_item;

    DatabaseHelper mDatabaseHelper;

    private String selectedName;

    private int selectedID;


    private Context mContext= EditData.this;
    private static final int ACTIVITY_NUM=1;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        editable_item = findViewById(R.id.editable_item);
        /**
         * firebase
         */
        setupFirebaseAuth();


        //get the intent extra from the ListDataActivity
        Intent receivedIntent = getIntent();

        //now get the itemID we passed as an extra
        selectedID = receivedIntent.getIntExtra("id",-1); //NOTE: -1 is just the default value

        //now get the name we passed as an extra
        selectedName = receivedIntent.getStringExtra("name");

        //set the text to show the current selected name
        editable_item.setText(selectedName);

        mDatabaseHelper = new DatabaseHelper(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = editable_item.getText().toString();
                if(!item.equals("")){
                    mDatabaseHelper.updateName(item,selectedID,selectedName);

                    goHome();
                    toastMessage("Saved!");
                }else{
                    toastMessage("You must enter a name");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabaseHelper.deleteName(selectedID, selectedName);
                    editable_item.setText("");
                    goHome();
                    toastMessage("Removed from database");

                /**
                 * TODO: attempt to delete only a single child!!!!!
                 */
                myRef=mFirebaseDatabase.getInstance().getReference().getRoot().child("sets");
                toastMessage("myRef"+myRef);
                myRef.setValue(null);

            }
        });

        setupBottomNavigationView();
    }

    private void setupBottomNavigationView(){

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
                        Intent intent = new Intent(EditData.this, FeaturesActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.ic_workoutsplit:
                        Intent intent1 = new Intent(EditData.this, WorkoutSplit.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_timer:
                        Intent intent2 = new Intent(EditData.this, Timer.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_camera:
                        Intent intent3 = new Intent(EditData.this, Camera.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_photo:
                        Intent intent4 = new Intent(EditData.this, PhotoLibrary.class);
                        startActivity(intent4);
                        break;
                }
                return false;
            }
        });

    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    public void goHome(){
        Intent intent = new Intent(this, ListData.class);
        startActivity(intent);
    }

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


//    @Override
//    public void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }
}
