package com.example.android.splitfeatures.notes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.android.splitfeatures.Camera;
import com.example.android.splitfeatures.FeaturesActivity;
import com.example.android.splitfeatures.R;
import com.example.android.splitfeatures.Timer;
import com.example.android.splitfeatures.Utils.BottomNavigationViewHelper;
import com.example.android.splitfeatures.workoutsplit.WorkoutSplit;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.HashSet;

public class Notes extends AppCompatActivity {

    ListView listView;
    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    ImageButton add;

    private static final String TAG = "Notes";


    /**
     * for bottom nav
     */
    private static final int ACTIVITY_NUM=4;
    private Context mContext= Notes.this;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);



        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.add_note) {
            Intent intent = new Intent(getApplicationContext(),NoteEditor.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_library);
        listView =  findViewById(R.id.listView);

        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(".PhotoLibrary", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes",null);

        if(set == null){
            notes.add("Tester");
        } else {
            notes = new ArrayList(set);
        }


        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),NoteEditor.class);
                intent.putExtra("noteId",position);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(Notes.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notes.remove(position);
                                arrayAdapter.notifyDataSetChanged();

                                HashSet<String> set = new HashSet<>(Notes.notes);
                                sharedPreferences.edit().putStringSet("notes",set).apply();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();

                return true;
            }
        });

        /**
         * uncomment out this method below to work notes, last time i checked it was still crashing
         */

        //setupBottomNavigationView();

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
                        Intent intent4 = new Intent(Notes.this, FeaturesActivity.class);
                        startActivity(intent4);
                        break;

                    case R.id.ic_workoutsplit:
                        Intent intent1 = new Intent(Notes.this, WorkoutSplit.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_timer:
                        Intent intent2 = new Intent(Notes.this, Timer.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_camera:
                        Intent intent3 = new Intent(Notes.this, Camera.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_notes:

                        break;
                }


                return false;
            }
        });
    }
    }

