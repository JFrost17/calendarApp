package com.example.calendar;

public class EventObject {

    String date;
    String note;
    String username;
    String eventName;
    String time;

    EventObject()
    {
        this.date="";
        this.eventName = "";
        this.username="";
        this.note="";
        this.time="";
    }

    EventObject(String date, String note, String username, String eventName, String time)
    {
        this.date=date;
        this.eventName = eventName;
        this.username=username;
        this.note=note;
        this.time=time;
    }
    void setUsername(String username)
    {
        this.username = username;
    }
    void setNote(String note)
    {
        this.note=note;
    }
    void setDate(String date)
    {
        this.date = date;
    }
    void setEventName(String eventName)
    {
        this.eventName = eventName;
    }
    void setTime(String time)
    {
        this.time = time;
    }

    public String getUsername()
    {
        return this.username;
    }
    public String getNote()
    {
        return this.note;
    }
    public String getDate()
    {
        return this.date;
    }
    public String getEventName()
    {
        return this.eventName;
    }
    public String getTime()
    {
        return this.time;
    }




}
