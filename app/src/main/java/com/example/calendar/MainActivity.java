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

public class MainActivity extends AppCompatActivity {
    int Jan = 31;
    int Feb = 28;
    int March = 31;
    int April = 30;
    int May = 31;
    int June = 30;
    int July = 31;
    int Aug = 31;
    int Sep = 30;
    int Oct = 31;
    int Nov = 30;
    int Dec = 31;
    int month = 0;
    int nextFirst =0;
    int first = 5;
    int last = 0;
    int prevLast = 0;
    String username ="";

    private Button btns[]=new Button[42];
    private Button btnNext;
    private Button btnPre;
    private Button btnSignUp;
    private Button btnSignIn;
    private Button btnClear;
    private TextView monthText;
    int[] daysInMonth = new int[]{31,28,31,30,31,30,31,31,30,31,30,31};
    int[] firsts = new int[12];
    int index = 0;
    
    DatabaseHelper dbh;
    ArrayList<String> listData = new ArrayList<>();
    boolean foundUser = false;
    SharedPreferences sp;
    public TextView userText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNext = findViewById(R.id.btnNext);
        btnPre = findViewById(R.id.btnPre);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnClear = findViewById(R.id.btnClear);
        monthText = findViewById(R.id.monthText);
        userText = findViewById(R.id.userText);
        dbh = new DatabaseHelper(getApplicationContext());
        ListView mListView;
        populateListView();
        sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        username = sp.getString("username","");
        userText.setText("User: "+username);
        for(int i=0;i<btns.length;i++) {
            int x = i + 1;
            String b = "btnAns" + x;
            int resID = getResources().getIdentifier(b, "id", getPackageName());
            btns[i] = (Button) findViewById(resID);
            btns[i].setTag(new Integer(i));
        }

        for(int i=0;i<btns.length-1;i++)
        {
            if(i>=first && i<daysInMonth[month]+first)
            {
                btns[i].setText(Integer.toString(i-(first-1)));
                if(i-(first-1)==daysInMonth[month])
                {
                   nextFirst = (i+1)%7;
                   last = i;
                }
            }
        }
        firsts[index]=first;
        index++;
       // btns[last].setText("here");

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (month < 11) {
                    month += 1;
                    first = nextFirst;
                    for (int i = 0; i < btns.length - 1; i++) {
                        if (i < first) {
                            btns[i].setText("");
                        }
                        if (i >= first && i < daysInMonth[month] + first) {
                            btns[i].setText(Integer.toString(i - (first - 1)));
                            if (i - (first - 1) == daysInMonth[month]) {
                                nextFirst = (i + 1) % 7;
                            }
                        }
                        if (i >= daysInMonth[month] + first) {
                            btns[i].setText("");
                        }
                    }
                    firsts[index] = first;
                    index++;
                    //btns[nextFirst].setText("here");
                    monthText.setText(setMonth());
                }
            }
        });

        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(month>0){
                month -=1;
                first = firsts[month];
                for(int i=0;i<btns.length-1;i++)
                {
                    if(i<first)
                    {
                        btns[i].setText("");
                    }
                    if(i>=firsts[month] && i<daysInMonth[month]+first)
                    {
                        btns[i].setText(Integer.toString(i-(first-1)));
                        if(i-(first-1)==daysInMonth[month])
                        {
                            nextFirst = (i+1)%7;
                        }
                    }
                    if(i>=daysInMonth[month]+first)
                    {
                        btns[i].setText("");
                    }
                }
                index--;
                //btns[nextFirst].setText("here");
                    monthText.setText(setMonth());
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivitiesSignUp();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivitiesSignIn();
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbh.deleteAllEvent();
            }
        });
    }

    private void switchActivitiesSignUp()
    {
        Intent switchActivityIntent = new Intent(this, SignUp.class);
        startActivity(switchActivityIntent);
    }
    private void switchActivitiesSignIn()
    {
        Intent switchActivityIntent = new Intent(this, SignIn.class);
        startActivity(switchActivityIntent);
    }
    private void switchActivitiesEvent()
    {
        Intent switchActivityIntent = new Intent(this, Event.class);
        startActivity(switchActivityIntent);
    }
    private void switchActivitiesAddEvent()
    {
        Intent switchActivityIntent = new Intent(this, addEvent.class);
        startActivity(switchActivityIntent);
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
        Log.e("findevent","alive");
        while(data.moveToNext())
        {
            Log.e("findevent",data.getString(2).toString() );
            Log.e("findevent",sp.getString("date",""));
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
        Cursor data = dbh.getDataUser();
        while(data.moveToNext())
        {
            if(data.getString(0).toString().equals(sp.getString("username",""))&& data.getString(2).toString().equals(sp.getString("date","")))
            {
                foundUser = true;

            }
        }
        if(foundUser == true)
        {
            eo.setUsername(sp.getString("username",""));
            eo.setEventName(data.getString(1).toString());
            eo.setDate(data.getString(2).toString());
            eo.setTime(data.getString(3).toString());
            eo.setNote(data.getString(4).toString());
            foundUser=false;
            return eo;
        }
        else
        {
            return eo;
        }
    }

    public void buttonClick(View v) {
        Log.e("buttons","clicked");
        Button b = null;
        for(int i=0;i<btns.length;i++)
        {
            if(btns[i].getTag()==v.getTag())
            {
                b=btns[i];
            }
        }
        if(!b.getText().toString().equals("")) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("date", (month + 1) + "/" + b.getText().toString());
            editor.commit();
            Log.e("buttons", "here");
            if (this.findEvent() == true) {
                switchActivitiesEvent();
            } else {
                switchActivitiesAddEvent();
            }
        }
    }

    private String setMonth()
    {
        if(month == 0)
        {
            return "January";
        }
        else if (month == 1)
        {
            return "February";
        }
        else if (month == 2)
        {
            return "March";
        }
        else if (month == 3)
        {
            return "April";
        }
        else if (month == 4)
        {
            return "May";
        }
        else if (month == 5)
        {
            return "June";
        }
        else if (month == 6)
        {
            return "July";
        }
        else if (month == 7)
        {
            return "August";
        }
        else if (month == 8)
        {
            return "September";
        }
        else if (month == 9)
        {
            return "October";
        }
        else if (month == 10)
        {
            return "November";
        }
        else if (month == 11)
        {
            return "December";
        }
        else
        {
            return"";
        }
    }
}