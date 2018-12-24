package com.example.android.splitfeatures.workoutsplit;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.splitfeatures.R;
import com.example.android.splitfeatures.WorkoutSplit;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * for workoutsplit!!
 */
public class ListData extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";
    DatabaseHelper mDatabaseHelper;
    private ListView mListView;
    private Button BtndeleteAll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);
        mListView = findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);
        populateListView();
        BtndeleteAll = findViewById(R.id.trash);

        BtndeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if(mDatabaseHelper.getData()!=null)
                mDatabaseHelper.deleteAll();
                goHome();
            }
        });
    }


    private void populateListView() {
                Log.d(TAG, "populateListView: Displaying data in the ListView.");

                //get the data and append to a list
                Cursor data = mDatabaseHelper.getData();
                ArrayList<String> listExercise = new ArrayList<>();
                ArrayList<String> listSet = new ArrayList<>();
                ArrayList<String> listRep = new ArrayList<>();


                while (data.moveToNext()) {
                    //get the value from the database in column 1
                    //then add it to the ArrayList
                    listExercise.add(data.getString(1));
                    listSet.add(data.getString(2));
                    listRep.add(data.getString(3));

                }

                String dt = "2018-12-24";  // Start date
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(sdf.parse(dt));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
                String output = sdf1.format(c.getTime());

                SimpleDateFormat sdf11 = new SimpleDateFormat("EEEE");
                Date d = new Date();
                String dayOfTheWeek = sdf11.format(d);

                final List<ArrayList<String>> listOfLists = new ArrayList<>();
                int counter = listExercise.size();
                for (int x = 0; x < counter; x++) {
                    ArrayList<String> derp = new ArrayList<>();
                    derp.add("Workout: " + listExercise.toArray()[x].toString());
                    derp.add("\n\t\tSet: "+ listSet.toArray()[x].toString());
                    derp.add("\n\t\tRep: " +listRep.toArray()[x].toString());
                    derp.add("\nDay: "+dayOfTheWeek +" ("+output+")" );

                    listOfLists.add(derp);
                    }
                    //create the list adapter and set the adapter
                    ListAdapter adapter5 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listOfLists);
                    mListView.setAdapter(adapter5);


                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            String name = adapterView.getItemAtPosition(i).toString();
                            //String sets = adapterView.getItemAtPosition(i+1).toString();
                            Log.d(TAG, "onItemClick: You Clicked on " + name);

                            Cursor data = mDatabaseHelper.getItemID(listOfLists); //get the id associated with that name
                            int itemID = -1;
                            while (data.moveToNext()) {
                                itemID = data.getInt(0);

                            }
                            if (itemID > -1) {
                                Log.d(TAG, "onItemClick: The ID is: " + itemID);
                                Intent editScreenIntent = new Intent(ListData.this, EditData.class);
                                editScreenIntent.putExtra("id", itemID);
                                editScreenIntent.putExtra("name", name);
                                startActivity(editScreenIntent);

                            } else {
                                toastMessage("No ID associated with that name");

                            }
                        }
                    });
    }



    public void goHome(){
        Intent intent = new Intent(this, WorkoutSplit.class);
        startActivity(intent);
    }


    /**
         * customizable toast
         * @param message
         */
        private void toastMessage (String message){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

}

/**
 * Source: Coding w/ mitch implementation https://github.com/mitchtabian/SaveReadWriteDeleteSQLite
 */