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

public class SignIn extends AppCompatActivity {

    DatabaseHelper dbh;
    SharedPreferences sp;
    Button btnCo;
    Button btnE;
    String username;
    String password;
    EditText usernameET;
    EditText passwordET;
    ArrayList<String> listData = new ArrayList<>();
    boolean foundUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        btnCo = findViewById(R.id.btnCo);
        btnE = findViewById(R.id.btnE);
        dbh = new DatabaseHelper(getApplicationContext());
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        ListView mListView;
        populateListView();

        btnCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("SignIn","here");
                if(findUser()) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("username", usernameET.getText().toString());
                    editor.commit();
                    Log.e("SignIn","success");
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
        Log.e("SignIn","here");
        while(data.moveToNext())
        {
            if(data.getString(0).toString().equals(usernameET.getText().toString()) && data.getString(1).toString().equals(passwordET.getText().toString()) )
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