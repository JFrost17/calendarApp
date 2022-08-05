package com.example.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity {

    DatabaseHelper dbh;
    EditText usernameET;
    EditText passwordET;
    Button btnC;
    Button btnE;
    ArrayList<String> listData = new ArrayList<>();
    boolean foundUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        dbh = new DatabaseHelper(getApplicationContext());
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        btnC= findViewById(R.id.btnC);
        btnE = findViewById(R.id.btnE);
        ListView mListView;
        populateListView();
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!findUser()) {

                    dbh.addDataUser(usernameET.getText().toString(), passwordET.getText().toString());
                    populateListView();
                    //dbh.deleteAll();
                }
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
        Cursor data = dbh.getDataUser();
        listData.clear();
        while(data.moveToNext())
        {
            listData.add(data.getString(0));
            Log.e("populate",data.getString(0));
        }
    }
    private boolean findUser()
    {
        Cursor data = dbh.getDataUser();
        while(data.moveToNext())
        {
            if(data.getString(0).toString().equals(usernameET.getText().toString()))
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