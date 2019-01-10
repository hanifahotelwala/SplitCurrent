package com.example.android.splitfeatures.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.android.splitfeatures.CameraAndPhotoLib.Photo;
import com.example.android.splitfeatures.CameraAndPhotoLib.ShareActivity;
import com.example.android.splitfeatures.Utils.ImageManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.example.android.splitfeatures.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class FirebaseMethods {

    private static final String TAG = "FirebaseMethods";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String userID;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private StorageReference mStorageReference;


    private Context mContext;
    private double mPhotoUploadProgress = 0;


    public FirebaseMethods(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mContext = context;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();

        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
    }

    public void uploadNewPhoto(String photoType,final String caption, final int count, final String imgUrl,
                               Bitmap bm){
        Log.d(TAG, "uploadNewPhoto: attempting to uplaod new photo.");

        FilePaths filePaths = new FilePaths();
        //case1) new photo
        if(photoType.equals(mContext.getString(R.string.new_photo))){
            Log.d(TAG, "uploadNewPhoto: uploading NEW photo.");

            String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            /**
             * defines the location where the file is stored
             */
            StorageReference storageReference = mStorageReference
                    .child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + user_id + "/photo" + (count + 1));

            //convert image url to bitmap
            if(bm == null){
                bm = ImageManager.getBitmap(imgUrl);
            }

            byte[] bytes =ImageManager.getBytesFromBitmap(bm, 100);

            UploadTask uploadTask = null;
            uploadTask = storageReference.putBytes(bytes);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //    Uri firebaseUrl = taskSnapshot.getDownloadUrl();
                    Uri firebaseUrl = taskSnapshot.getUploadSessionUri();


                    Toast.makeText(mContext, "photo upload success", Toast.LENGTH_SHORT).show();

                    //add the new photo to 'photos' node and 'user_photos' node
                    addPhotoToDatabase(caption,firebaseUrl.toString());

                    //navigate to the main feed so the user can see their photo
                    Intent intent = new Intent(mContext, ShareActivity.class);
                    mContext.startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: Photo upload failed.");
                    Toast.makeText(mContext, "Photo upload failed ", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                    if(progress - 15 > mPhotoUploadProgress){
                        Toast.makeText(mContext, "photo upload progress: " + String.format("%.0f", progress) + "%", Toast.LENGTH_SHORT).show();
                        mPhotoUploadProgress = progress;
                    }

                    Log.d(TAG, "onProgress: upload progress: " + progress + "% done");
                }
            });

        }
        //case new profile photo
//        else if(photoType.equals(mContext.getString(R.string.profile_photo))){
//            Log.d(TAG, "uploadNewPhoto: uploading new PROFILE photo");
//
//
//            String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
//            StorageReference storageReference = mStorageReference
//                    .child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + user_id + "/profile_photo");
//
//            //convert image url to bitmap
//            if(bm == null){
//                bm = ImageManager.getBitmap(imgUrl);
//            }
//            byte[] bytes = ImageManager.getBytesFromBitmap(bm, 100);
//
//            UploadTask uploadTask = null;
//            uploadTask = storageReference.putBytes(bytes);
//
//            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Uri firebaseUrl = taskSnapshot.getDownloadUrl();
//
//                    Toast.makeText(mContext, "photo upload success", Toast.LENGTH_SHORT).show();
//
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Log.d(TAG, "onFailure: Photo upload failed.");
//                    Toast.makeText(mContext, "Photo upload failed ", Toast.LENGTH_SHORT).show();
//                }
//            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//
//                    if(progress - 15 > mPhotoUploadProgress){
//                        Toast.makeText(mContext, "photo upload progress: " + String.format("%.0f", progress) + "%", Toast.LENGTH_SHORT).show();
//                        mPhotoUploadProgress = progress;
//                    }
//
//                    Log.d(TAG, "onProgress: upload progress: " + progress + "% done");
//                }
//            });
//        }

    }



//    private String getTimestamp(){
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US;
//       // sdf.setTimeZone(TimeZone.getTimeZone("Canada/Pacific"));
//        return sdf.format(new Date());
//    }

    private void addPhotoToDatabase(String caption,String url){
        Log.d(TAG, "addPhotoToDatabase: adding photo to database.");

       // String tags = StringManinpulation.getTags(caption);
        String newPhotoKey = myRef.child(mContext.getString(R.string.dbname_photos)).push().getKey();
        Photo photo = new Photo();
        photo.setCaption(caption);
        //photo.setDate_created(getTimestamp());
        photo.setImage_path(url);
       // photo.setTags(tags);
        photo.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
        photo.setPhoto_id(newPhotoKey);

        //insert into database
        myRef.child(mContext.getString(R.string.dbname_user_photos))
                .child(FirebaseAuth.getInstance().getCurrentUser()
                        .getUid()).child(newPhotoKey).setValue(photo);
        myRef.child(mContext.getString(R.string.dbname_photos)).child(newPhotoKey).setValue(photo);

    }

    public int getImageCount(DataSnapshot dataSnapshot){
        int count = 0;
        for(DataSnapshot ds: dataSnapshot
                .child(mContext.getString(R.string.dbname_user_photos))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .getChildren()){
            count++;
        }
        return count;
    }

    public boolean checkIfUsernameExists(String username, DataSnapshot datasnapshot){
        Log.d(TAG, "checkIfUsernameExists: checking if " + username + " already exists.");

        User user = new User();

        for (DataSnapshot ds: datasnapshot.child(userID).getChildren()){
            Log.d(TAG, "checkIfUsernameExists: datasnapshot: " + ds);

            user.setUsername(ds.getValue(User.class).getUsername());
            Log.d(TAG, "checkIfUsernameExists: username: " + user.getUsername());

            if(StringManinpulation.expandUsername(user.getUsername()).equals(username)){
                Log.d(TAG, "checkIfUsernameExists: FOUND A MATCH: " + user.getUsername());
                return true;
            }
        }
        return false;
    }
    /**
     * Register a new email and password to Firebase Authentication
     * @param email
     * @param password
     * @param username
     */
    public void registerNewEmail(final String email, String password, final String username){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();

                        }
                        else if(task.isSuccessful()){
                            sendVerificationEmail();
                            userID = mAuth.getCurrentUser().getUid();
                            Log.d(TAG, "onComplete: Authstate changed: " + userID);

                        }

                    }
                });
    }


    public void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(mContext, "verification email sent", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(mContext, "couldn't send verification email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


    public void addNewUser(String userID, String email, String workout, String reps, String sets){

        User user = new User(userID, email);

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .setValue(user);

        UserWorkoutSplit workoutsplit = new UserWorkoutSplit(userID, workout, reps,sets);
        myRef.child("workoutsplit")
                .child(userID)
                .setValue(workoutsplit);

    }

    public UserSettings getUserWorkoutSplitInfo(DataSnapshot dataSnapshot){
        Log.d(TAG, "getUserWorkoutSplit: retrieving workoutsplit from firebase.");


        UserWorkoutSplit workoutsplit  = new UserWorkoutSplit();
        User user = new User();

        for(DataSnapshot ds: dataSnapshot.getChildren()){

            // user_account_settings node
            if(ds.getKey().equals(mContext.getString(R.string.dbname_workoutsplit))){
                Log.d(TAG, "getWorkoutSplit: datasnapshot: " + ds);

                try{


                    workoutsplit.setWorkout(
                            ds.child(userID)
                                    .getValue(UserWorkoutSplit.class)
                                    .getWorkout()
                    );
                    workoutsplit.setSets(
                            ds.child(userID)
                                    .getValue(UserWorkoutSplit.class)
                                    .getSets()
                    );
                    workoutsplit.setReps(
                            ds.child(userID)
                                    .getValue(UserWorkoutSplit.class)
                                    .getReps()
                    );
                    workoutsplit.setUser_id(
                            ds.child(userID)
                                    .getValue(UserWorkoutSplit.class)
                                    .getUser_id()
                    );

                    Log.d(TAG, "getWorkoutSplit: retrieved userWorkoutSplit information: " + workoutsplit.toString());
                }catch (NullPointerException e){
                    Log.e(TAG, "getWorkoutSplit: NullPointerException: " + e.getMessage() );
                }


                // users node
                if(ds.getKey().equals(mContext.getString(R.string.dbname_users))) {
                    Log.d(TAG, "getUserAccountSettings: datasnapshot: " + ds);

                    user.setUsername(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getUsername()
                    );
                    user.setEmail(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getEmail()
                    );
                    user.setUser_id(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getUser_id()
                    );

                    Log.d(TAG, "getUserAccountSettings: retrieved users information: " + user.toString());
                }
            }
        }
        return new UserSettings(user, workoutsplit);

    }

}
