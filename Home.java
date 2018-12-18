package com.example.android.splitfeatures;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    private Button mainPageNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mainPageNav = findViewById(R.id.mainpage);
        mainPageNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMainPage();
            }
        });


    }


    public void goToMainPage(){
        Intent intent = new Intent(this, FeaturesActivity.class);
        startActivity(intent);
    }
}
