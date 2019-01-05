package com.example.android.splitfeatures.workoutsplit;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;
import com.example.android.splitfeatures.R;


import com.example.android.splitfeatures.WorkoutSplit;

import java.util.ArrayList;


public class ListData extends AppCompatActivity {


    private ExpandableListView mExpandableList;
    DatabaseHelper mDatabaseHelper;
    private Button clear;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        mExpandableList = findViewById(R.id.expandableListView);
        mDatabaseHelper = new DatabaseHelper(this);
        clear = findViewById(R.id.clearAll);

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

    }

    public void goHome(){
        Intent intent = new Intent(this, WorkoutSplit.class);
        startActivity(intent);
    }


}

