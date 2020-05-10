package com.example.retime;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class home extends FragmentActivity implements TimeTableAdapter.OnFragmentListener {
    String[] TimeList = { "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00"};


    RecyclerView TimeTableRecycleView;
    TimeTableAdapter TimeTableAdapter;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    ImageButton Calendars;

    List<com.example.retime.TimeTable> TimeTableList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Calendars = findViewById(R.id.calendar);
        Calendars.setOnClickListener(new CalendarsOnClickListener());
        TimeTableRecycleView = findViewById(R.id.schedule);
        TimeTableRecycleView.setVisibility(View.VISIBLE);
        // Create adapter passing in the sample user data
        TimeTableAdapter = new TimeTableAdapter(TimeTableList, home.this, this);
        // Attach the adapter to the recyclerview to populate items
        TimeTableRecycleView.setAdapter(TimeTableAdapter);
        // Set layout manager to position the items
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        TimeTableRecycleView.setLayoutManager(layoutManager);
        openHelper = new TaskDatabase(this);
        db = openHelper.getWritableDatabase();

        //Filling our recycler view list
        for (int i= 0; i< TimeList.length; i++ )
        { String time = TimeList[i];
            Cursor data = db.rawQuery("Select * From " + TaskDatabase.TABLE_NAME + " Where " + TaskDatabase.COL_1 + " = " + i, null);
            if (data.getCount() > 0){
                data.moveToFirst();
                com.example.retime.TimeTable TimeTable = new com.example.retime.TimeTable (i, time, data.getString(1), data.getString(4));
                TimeTableList.add(TimeTable); //Add company to the company list
            }
            else {
                com.example.retime.TimeTable TimeTable = new com.example.retime.TimeTable (i, time, "", "");
                TimeTableList.add(TimeTable); //Add company to the company list
            }

        }
    }

    private class CalendarsOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(home.this, CalendarTask.class);
            startActivity(intent);
        }
    }

    public void onFragmentClick(int position) {

        Fragment Taskfrag = new Task(); //Declare a dynamic fragment variable
        SharedPreferences prefs = getSharedPreferences("MyTask", MODE_PRIVATE);
        //declare share preference editor
        SharedPreferences.Editor editor = prefs.edit();

        //clear share preference
        editor.clear();
        editor.putInt("TaskTime", position);
        //commit share preference edit
        editor.apply();
        editor.commit();

        FragmentManager TaskfragmentManager = getSupportFragmentManager(); //declare a fragment manager
        FragmentTransaction TaskfragmentTransaction = TaskfragmentManager.beginTransaction(); //declare a fragment transaction
//        TaskfragmentTransaction.replace(R.id.TaskLayout,Taskfrag, "TaskFragment").addToBackStack(null); //replace the fragment that user is selected to the screen and add to back stack
        TaskfragmentTransaction.replace(R.id.TaskLayout,Taskfrag, "TaskFragment");//.addToBackStack(null); //replace the fragment that user is selected to the screen and add to back stack
        TaskfragmentTransaction.commit(); //commit the changes of the fragment transaction
        TimeTableRecycleView.setVisibility(View.INVISIBLE);

    }
}
