package com.example.android.splitfeatures.workoutsplit;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.android.splitfeatures.Camera;
import com.example.android.splitfeatures.FeaturesActivity;
import com.example.android.splitfeatures.R;
import com.example.android.splitfeatures.Timer;
import com.example.android.splitfeatures.Utils.BottomNavigationViewHelper;
import com.example.android.splitfeatures.notes.Notes;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


import java.util.ArrayList;


public class ListData extends AppCompatActivity {


    private ExpandableListView mExpandableList;
    DatabaseHelper mDatabaseHelper;
    private Button clear;

    private Context mContext= ListData.this;
    private static final int ACTIVITY_NUM=1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        mExpandableList = findViewById(R.id.expandableListView);
        mDatabaseHelper = new DatabaseHelper(this);
        clear = findViewById(R.id.clearAll);
        clear.setFocusable(false);

        ArrayList<Parent> arrayParents = new ArrayList<Parent>();
        ArrayList<String> arrayChildren;// = new ArrayList<String>();

        Cursor data = mDatabaseHelper.getData();

        while (data.moveToNext()) {
            //get the value from the database in column 1
            //then add it to the ArrayList
            Parent parent = new Parent();
            parent.setTitle(data.getString(1));

            arrayChildren = new ArrayList<String>();
            arrayChildren.add("Sets "+data.getString(2));
            arrayChildren.add("Reps "+data.getString(3));
            parent.setArrayChildren(arrayChildren);

            //in this array add the Parent object. We will use the arrayParents at the setAdapter
            arrayParents.add(parent);

        }

        //sets the adapter that provides data to the list.
        mExpandableList.setAdapter(new CustomAdapter(ListData.this,arrayParents));
        mExpandableList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

                Toast.makeText(getApplicationContext(),
                        "Showing workout description..",
                        Toast.LENGTH_SHORT).show();
            }
        });

        mExpandableList.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        " Compressing workout details..",
                        Toast.LENGTH_SHORT).show();

            }
        });

        mExpandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                String name = parent.getItemAtPosition(groupPosition).toString();

                Cursor data = mDatabaseHelper.getItemID(name); //get the id associated with that name

                int itemID = -1;

                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                }
                if (itemID > -1) {
                    Intent editScreenIntent = new Intent(ListData.this, EditData.class);
                    editScreenIntent.putExtra("id", itemID);
                    editScreenIntent.putExtra("name", name);

                    startActivity(editScreenIntent);

                } else {
                    Toast.makeText(getApplicationContext(),
                            " No Id associated with that name.",
                            Toast.LENGTH_SHORT).show();
                }
                return true;
            }

        });

        /**
         * deletes all entries
         */
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDatabaseHelper.getData()!=null){
                    mDatabaseHelper.deleteAll();
                    goHome();}
                else
                    Toast.makeText(getApplicationContext(),
                            " Exercise log is already empty...",
                            Toast.LENGTH_SHORT).show();
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
                        Intent intent = new Intent(ListData.this, FeaturesActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.ic_workoutsplit:
                        Intent intent1 = new Intent(ListData.this, WorkoutSplit.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_timer:
                        Intent intent2 = new Intent(ListData.this, Timer.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_camera:
                        Intent intent3 = new Intent(ListData.this, Camera.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_notes:
                        Intent intent4 = new Intent(ListData.this, Notes.class);
                        startActivity(intent4);
                        break;
                }


                return false;
            }
        });

    }


    public void goHome(){
        Intent intent = new Intent(this, WorkoutSplit.class);
        startActivity(intent);
    }


}

