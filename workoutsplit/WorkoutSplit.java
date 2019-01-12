package com.example.android.splitfeatures.workoutsplit;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.android.splitfeatures.Camera;
import com.example.android.splitfeatures.FeaturesActivity;
import com.example.android.splitfeatures.PhotoLibrary;
import com.example.android.splitfeatures.R;
import com.example.android.splitfeatures.Timer;
import com.example.android.splitfeatures.Utils.BottomNavigationViewHelper;
import com.example.android.splitfeatures.Utils.FirebaseMethods;
import com.example.android.splitfeatures.Utils.UserWorkoutSplit;
import com.example.android.splitfeatures.login.SignOut;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


public class WorkoutSplit extends AppCompatActivity {

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;


    //SQLITE
    DatabaseHelper mDatabaseHelper;
    private Button btnAdd, btnExerciseLog;
    private ImageButton signOut;
    private EditText workout, sets, reps;
    private static final String TAG = "WorkoutSplit";
    private String userID,workoutEntry, setEntry, repEntry;

    //bottom navigation
    private static final int ACTIVITY_NUM=1;
    private Context mContext= WorkoutSplit.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_split);
        //vars
        workout = findViewById(R.id.workout);
        sets = findViewById(R.id.sets);
        reps = findViewById(R.id.reps);
        btnAdd = findViewById(R.id.btnAdd);
        btnExerciseLog = findViewById(R.id.btnView);
        signOut = findViewById(R.id.signOutButton);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(WorkoutSplit.this, SignOut.class);
                startActivity(intent1);
            }
        });

        //SQLite
        mDatabaseHelper = new DatabaseHelper(this);
        //firebase
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        //makes sure if the correct user is signed in
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }

            }
        };

        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                workoutEntry = workout.getText().toString();
                setEntry = sets.getText().toString();
                int setNum = new Integer(setEntry).intValue();
                repEntry = reps.getText().toString();
                int repNum = new Integer(repEntry).intValue();

                workoutEmpty(workoutEntry);
                setEmpty(setEntry);
                repEmpty(repEntry);

                if (workout.length() != 0 && sets.length() != 0 && reps.length() != 0) {
                    //adds entry into the SQLite db
                    AddData(workoutEntry, setNum, repNum);
                   //adds entry into the firebase database
                    UserWorkoutSplit userInformation = new UserWorkoutSplit(workoutEntry, setEntry, repEntry);
                    myRef.child("workout").child(userID).push().setValue(userInformation);
                    toastMessage("New Information has been saved.");

                    workout.setText("");
                    sets.setText("");
                    reps.setText("");

                } else {
                  toastMessage("You must put something in all fields!");
                    return;
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

    public void workoutEmpty(String workoutEntry){
        if(TextUtils.isEmpty(workoutEntry)){
            workout.setError("Field can not be empty");
        }

    }

    /**
     * TODO: setEmpty and repEmpty are not working.
     *
     */
    public void setEmpty(String setEntry){
        if(TextUtils.isEmpty(setEntry)){
            sets.setError("Field can not be empty");
        }

    }   public void repEmpty(String repEntry){
        if(TextUtils.isEmpty(repEntry)){
            reps.setError("Field can not be empty");
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

                    case R.id.ic_photo:
                        Intent intent4 = new Intent(WorkoutSplit.this, PhotoLibrary.class);
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


    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    //--------------------FIREBASE--------------------

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

               // setWorkoutWidgets(mFirebaseMethods.getUserWorkoutSplitInfo(dataSnapshot));


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
