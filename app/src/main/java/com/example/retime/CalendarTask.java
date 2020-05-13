package com.example.retime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarTask extends AppCompatActivity {

    CalendarView calendar;
    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;
    ImageButton task;
    Button savebtn;
    EditText firsttasket, firsttimeet, secondtasket, secondtimeet;
    String _year, _month, _dayofmonth;
    String today;
    String[] todayarr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_calendartask);
        task = findViewById(R.id.tasks);
        task.setOnClickListener(new TasksOnClickListener());
        savebtn = findViewById(R.id.savebtn);
        savebtn.setOnClickListener(new SaveTaskOnClickListener());
        firsttasket = findViewById(R.id.firsttask);
        firsttimeet = findViewById(R.id.firsttime);
        secondtasket = findViewById(R.id.secondtask);
        secondtimeet = findViewById(R.id.secondtime);
        openHelper = new CalendarDatabase(this);
        db = openHelper.getWritableDatabase();
        calendar = findViewById(R.id.calendarView);
        today = new SimpleDateFormat("dd/MM/yyyy").format(new Date(calendar.getDate()));
        todayarr = today.split("/");
        _dayofmonth = new DecimalFormat("00").format(Integer.parseInt(todayarr[0]));
        _month = todayarr[1];
        _year = todayarr[2];
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                _year = Integer.toString(year);
                _month = new DecimalFormat("00").format(month + 1);
                _dayofmonth = Integer.toString(dayOfMonth);
                Log.d("blabla", _dayofmonth);
                Log.d("blabla1", _month);
                Log.d("blabla2", _year);
            }
        });
        Log.d("blabla", _dayofmonth);
        Log.d("blabla1", _month);
        Log.d("blabla2", _year);
    }

    public void insertdata (String day, String firsttask, String firsttime, String secondtask, String secondtime) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CalendarDatabase.COL_1, day);
        contentValues.put(CalendarDatabase.COL_2, firsttask);
        contentValues.put(CalendarDatabase.COL_3, firsttime);
        contentValues.put(CalendarDatabase.COL_4, secondtask);
        contentValues.put(CalendarDatabase.COL_5, secondtime);
        long id = db.insert(CalendarDatabase.TABLE_NAME, null, contentValues);

    }

    private class SaveTaskOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String firsttask = firsttasket.getText().toString();
            String firsttime = firsttimeet.getText().toString();
            String secondtask = secondtasket.getText().toString();
            String secondtime = secondtimeet.getText().toString();
            String todaydate = _dayofmonth.concat(_month).concat(_year);
            insertdata(todaydate, firsttask, firsttime, secondtask, secondtime);
        }
    }

    private class TasksOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(CalendarTask.this, home.class);
            startActivity(intent);
        }
    }


}
