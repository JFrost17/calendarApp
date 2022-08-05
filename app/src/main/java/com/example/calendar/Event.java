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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Event extends AppCompatActivity {

    DatabaseHelper dbh;
    ArrayList<String> listData = new ArrayList<>();
    boolean foundUser = false;
    SharedPreferences sp;
    Button btnE;

    TextView nameText;
    TextView dateText;
    TextView timeText;
    TextView noteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventObject eo = new EventObject();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        btnE = findViewById(R.id.btnE);
        dbh = new DatabaseHelper(getApplicationContext());
        ListView mListView;
        populateListView();
        sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        nameText = findViewById(R.id.eventText);
        dateText = findViewById(R.id.dateText);
        timeText = findViewById(R.id.timeText);
        noteText = findViewById(R.id.noteText);

        eo=findEventObject();

        nameText.setText(eo.getEventName());
        dateText.setText("Date: "+eo.getDate());
        timeText.setText("Time: "+eo.getTime());
        noteText.setText("Note: "+eo.getNote());
       // nameText.setText();
        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivities();
            }
        });

    }

    private void populateListView()
    {
        Cursor data = dbh.getDataEvent();
        listData.clear();
        while(data.moveToNext())
        {
            listData.add(data.getString(0));
            Log.e("populate",data.getString(0));
        }
    }
    private boolean findEvent()
    {
        Cursor data = dbh.getDataEvent();
        while(data.moveToNext())
        {
            if(data.getString(0).toString().equals(sp.getString("username",""))&& data.getString(2).toString().equals(sp.getString("date","")))
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
    private EventObject findEventObject()
    {
        EventObject eo = new EventObject();
        Cursor data = dbh.getDataEvent();
        while(data.moveToNext())
        {
            if(data.getString(0).toString().equals(sp.getString("username",""))&& data.getString(2).toString().equals(sp.getString("date","")))
            {
                eo.setUsername(sp.getString("username",""));
                eo.setEventName(data.getString(1).toString());
                eo.setDate(data.getString(2).toString());
                eo.setTime(data.getString(3).toString());
                eo.setNote(data.getString(4).toString());
            }
        }
            return eo;
    }
    private void switchActivities()
    {
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }
    
}