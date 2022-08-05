package com.example.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class addEvent extends AppCompatActivity {

    DatabaseHelper dbh;
    ArrayList<String> listData = new ArrayList<>();
    boolean foundUser = false;
    SharedPreferences sp;

    Button btnC;
    Button btnE;
    EditText nameET;
    EditText dateET;
    EditText timeET;
    EditText noteET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        dbh = new DatabaseHelper(getApplicationContext());
        ListView mListView;
        populateListView();
        sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        btnC = findViewById(R.id.btnC);
        btnE = findViewById(R.id.btnE);
        nameET = findViewById(R.id.nameET);
        timeET = findViewById(R.id.timeET);
        noteET = findViewById(R.id.noteET);


        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Log.e("adding","here");
                dbh.addDataEvent(sp.getString("username",""),nameET.getText().toString(),sp.getString("date",""),timeET.getText().toString(),noteET.getText().toString());
                populateListView();
                Log.e("adding","added");
            }
        });

        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivities();
            }
        });


    }
    private void populateListView()
    {
        Log.e("adding","here");
        Cursor data = dbh.getDataEvent();
        listData.clear();
        while(data.moveToNext())
        {
            listData.add(data.getString(0));
            Log.e("populate",data.getString(2));
        }
    }
    private boolean findEvent()
    {
        Cursor data = dbh.getDataUser();
        while(data.moveToNext())
        {
            if(data.getString(0).toString().equals(sp.getString("name","")))
            {
                foundUser = true;
            }
        }
        if(foundUser == true)
        {
            foundUser=false;
            return true;
        }
        else
        {
            return false;
        }
    }
    private void switchActivities()
    {
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }
}