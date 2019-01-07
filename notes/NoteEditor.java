package com.example.android.splitfeatures.notes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.example.android.splitfeatures.PhotoLibrary;
import com.example.android.splitfeatures.R;

import java.util.HashSet;

public class NoteEditor extends AppCompatActivity {

    static int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        EditText editText = findViewById(R.id.editText);

        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId",-1);
        Log.i("Note ID",String.valueOf(noteId));

        if(noteId != -1) {
            editText.setText(Notes.notes.get(noteId));
        } else {
            Notes.notes.add("");
            noteId = Notes.notes.size()-1;
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Notes.notes.set(noteId,String.valueOf(s));
                Notes.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.jardosh.notekeeping", Context.MODE_PRIVATE);

                HashSet<String> set = new HashSet<>(Notes.notes);
                sharedPreferences.edit().putStringSet("notes",set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}

