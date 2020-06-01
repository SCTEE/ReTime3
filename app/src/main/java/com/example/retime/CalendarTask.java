package com.example.retime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
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
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarTask extends AppCompatActivity {

    CalendarView calendar;
    SQLiteDatabase db, db2;
    SQLiteOpenHelper openHelper, openHelper2;
    ImageButton task;
    Button savebtn, deletebtn;
    EditText firsttasket, firsttimeet, secondtasket, secondtimeet;
    String _year, _month, _dayofmonth;
    String today;
    String[] todayarr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //will hide the title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // hide the title bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_calendartask);
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "RetimeChannel")
                .setSmallIcon(R.drawable.calendar)
                .setContentTitle("Retime Calendar")
                .setContentText("You have a task!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notifimgr = NotificationManagerCompat.from(this);
        task = findViewById(R.id.tasks);
        task.setOnClickListener(new TasksOnClickListener());
        savebtn = findViewById(R.id.savebtn);
        savebtn.setOnClickListener(new SaveTaskOnClickListener());
        deletebtn = findViewById(R.id.deletebtn);
        deletebtn.setOnClickListener(new DeleteTaskOnClickListener());
        firsttasket = findViewById(R.id.firsttask);
        firsttimeet = findViewById(R.id.firsttime);
        secondtasket = findViewById(R.id.secondtask);
        secondtimeet = findViewById(R.id.secondtime);
        openHelper = new CalendarDatabase(this);
        db = openHelper.getWritableDatabase();
        openHelper2 = new TaskDatabase(this);
        db2 = openHelper2.getWritableDatabase();
        calendar = findViewById(R.id.calendarView);
        today = new SimpleDateFormat("dd/MM/yyyy").format(new Date(calendar.getDate()));
        todayarr = today.split("/");
        _dayofmonth = new DecimalFormat("00").format(Integer.parseInt(todayarr[0]));
        _month = todayarr[1];
        _year = todayarr[2];

        String todaydate = _dayofmonth.concat(_month).concat(_year);
        Cursor data = db.rawQuery("Select * From " + CalendarDatabase.TABLE_NAME + " Where " + CalendarDatabase.COL_1 + " = " + todaydate, null);
        if (data.getCount() > 0){
            data.moveToFirst();
            firsttasket.setText(data.getString(1));
            firsttimeet.setText(data.getString(2));
            secondtasket.setText(data.getString(3));
            secondtimeet.setText(data.getString(4));
            String currenttime = new SimpleDateFormat("kk:mm").format(Calendar.getInstance().getTime());
            if (currenttime.substring(0,2).equals(data.getString(2)) || currenttime.substring(0,2).equals(data.getString(4))){
                notifimgr.notify(1, builder.build());
            }
        }

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                _year = Integer.toString(year);
                _month = new DecimalFormat("00").format(month + 1);
                _dayofmonth = Integer.toString(dayOfMonth);
                firsttasket.setText("");
                firsttimeet.setText("");
                secondtasket.setText("");
                secondtimeet.setText("");
                String todaydate = _dayofmonth.concat(_month).concat(_year);
                Cursor data = db.rawQuery("Select * From " + CalendarDatabase.TABLE_NAME + " Where " + CalendarDatabase.COL_1 + " = " + todaydate, null);
                if (data.getCount() > 0){
                    data.moveToFirst();
                    firsttasket.setText(data.getString(1));
                    firsttimeet.setText(data.getString(2));
                    secondtasket.setText(data.getString(3));
                    secondtimeet.setText(data.getString(4));
                }
                String firsttask = firsttasket.getText().toString();
                String firsttime = firsttimeet.getText().toString();
                String secondtask = secondtasket.getText().toString();
                String secondtime = secondtimeet.getText().toString();
                if (TextUtils.isEmpty(firsttask) && TextUtils.isEmpty(firsttime) && TextUtils.isEmpty(secondtask) && TextUtils.isEmpty(secondtime)){
                    deletebtn.setVisibility(View.INVISIBLE);
                }
                else {
                    deletebtn.setVisibility(View.VISIBLE);
                }
            }
        });

        String firsttask = firsttasket.getText().toString();
        String firsttime = firsttimeet.getText().toString();
        String secondtask = secondtasket.getText().toString();
        String secondtime = secondtimeet.getText().toString();

        if (TextUtils.isEmpty(firsttask) && TextUtils.isEmpty(firsttime) && TextUtils.isEmpty(secondtask) && TextUtils.isEmpty(secondtime)){
            deletebtn.setVisibility(View.INVISIBLE);
        }
        else {
            deletebtn.setVisibility(View.VISIBLE);
        }

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name";
            String description = "Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("RetimeChannel", name, importance);
            channel.setDescription(description);
            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(channel);
        }
    }

    public void insertdata (String day, String firsttask, String firsttime, String secondtask, String secondtime) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(CalendarDatabase.COL_1, day);
        contentValues.put(CalendarDatabase.COL_2, firsttask);
        contentValues.put(CalendarDatabase.COL_3, firsttime);
        contentValues.put(CalendarDatabase.COL_4, secondtask);
        contentValues.put(CalendarDatabase.COL_5, secondtime);
        long id = db.replace(CalendarDatabase.TABLE_NAME, null, contentValues);

        if (!TextUtils.isEmpty(firsttime)) {
            firsttime = Integer.toString(Integer.parseInt(firsttime) - 1);
        }

        if (!TextUtils.isEmpty(secondtime)){
            secondtime = Integer.toString(Integer.parseInt(secondtime) - 1);
        }

        String ID1 = day.concat("/").concat(firsttime);
        String ID2 = day.concat("/").concat(secondtime);

        if (firsttime.equals(secondtime)){
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put(TaskDatabase.COL_1, ID1);
            contentValues2.put(TaskDatabase.COL_2, firsttask);
            contentValues2.put(TaskDatabase.COL_3, "");
            contentValues2.put(TaskDatabase.COL_4, "");
            contentValues2.put(TaskDatabase.COL_5, secondtask);
            contentValues2.put(TaskDatabase.COL_6, "");
            contentValues2.put(TaskDatabase.COL_7, "");
            contentValues2.put(TaskDatabase.COL_8, day);
            contentValues2.put(TaskDatabase.COL_9, firsttime);
            id = db2.replace(TaskDatabase.TABLE_NAME, null, contentValues2);
        }
        else {
            ContentValues contentValues3 = new ContentValues();
            ContentValues contentValues4 = new ContentValues();

            contentValues3.put(TaskDatabase.COL_1, ID1);
            contentValues3.put(TaskDatabase.COL_2, "");
            contentValues3.put(TaskDatabase.COL_3, "");
            contentValues3.put(TaskDatabase.COL_4, "");
            contentValues3.put(TaskDatabase.COL_5, firsttask);
            contentValues3.put(TaskDatabase.COL_6, "");
            contentValues3.put(TaskDatabase.COL_7, "");
            contentValues3.put(TaskDatabase.COL_8, day);
            contentValues3.put(TaskDatabase.COL_9, firsttime);
            if(!TextUtils.isEmpty(firsttask) && !TextUtils.isEmpty(firsttime)){
                id = db2.replace(TaskDatabase.TABLE_NAME, null, contentValues3);
            }

            contentValues4.put(TaskDatabase.COL_1, ID2);
            contentValues4.put(TaskDatabase.COL_2, "");
            contentValues4.put(TaskDatabase.COL_3, "");
            contentValues4.put(TaskDatabase.COL_4, "");
            contentValues4.put(TaskDatabase.COL_5, secondtask);
            contentValues4.put(TaskDatabase.COL_6, "");
            contentValues4.put(TaskDatabase.COL_7, "");
            contentValues4.put(TaskDatabase.COL_8, day);
            contentValues4.put(TaskDatabase.COL_9, secondtime);
            if(!TextUtils.isEmpty(secondtask) && !TextUtils.isEmpty(secondtime)){
                id = db2.replace(TaskDatabase.TABLE_NAME, null, contentValues4);
            }
        }
        Toast.makeText(this, "save successfully", Toast.LENGTH_LONG).show();
    }

    public void deletedata (String day, String firsttime, String secondtime) {
        long id = db.delete(CalendarDatabase.TABLE_NAME, CalendarDatabase.COL_1 + " = " + day, null);
        firsttasket.setText("");
        firsttimeet.setText("");
        secondtasket.setText("");
        secondtimeet.setText("");

        if (!TextUtils.isEmpty(firsttime)) {
            firsttime = Integer.toString(Integer.parseInt(firsttime) - 1);
        }

        if (!TextUtils.isEmpty(secondtime)){
            secondtime = Integer.toString(Integer.parseInt(secondtime) - 1);
        }

        String ID1 = day.concat("/").concat(firsttime);
        String ID2 = day.concat("/").concat(secondtime);

        if (firsttime.equals(secondtime)){
            id = db2.delete(TaskDatabase.TABLE_NAME, CalendarDatabase.COL_1 + " = '" + ID1 + "'", null);
        }
        else {
            id = db2.delete(TaskDatabase.TABLE_NAME, CalendarDatabase.COL_1 + " = '" + ID1 + "'", null);
            id = db2.delete(TaskDatabase.TABLE_NAME, CalendarDatabase.COL_1 + " = '" + ID2 + "'", null);
        }

        Toast.makeText(this, "delete successfully", Toast.LENGTH_LONG).show();
    }

    private class SaveTaskOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String firsttask = firsttasket.getText().toString();
            String firsttime, secondtime;
            if (!TextUtils.isEmpty(firsttimeet.getText().toString())){
                firsttime = Integer.toString(Integer.parseInt(firsttimeet.getText().toString()));
            }
            else {
                firsttime = "";
            }

            String secondtask = secondtasket.getText().toString();
            if (!TextUtils.isEmpty(secondtimeet.getText().toString())){
                secondtime = Integer.toString(Integer.parseInt(secondtimeet.getText().toString()));
            }
            else {
                secondtime = "";
            }

            String todaydate = _dayofmonth.concat(_month).concat(_year);
            if(TextUtils.isEmpty(firsttask) && !TextUtils.isEmpty(secondtask)){
                Toast.makeText(CalendarTask.this, "Please fill in the first task", Toast.LENGTH_LONG).show();
            }
            else {
                if ((TextUtils.isEmpty(firsttask) && !TextUtils.isEmpty(firsttime)) || TextUtils.isEmpty(secondtask) && !TextUtils.isEmpty(secondtime)) {
                    Toast.makeText(CalendarTask.this, "Please enter task name", Toast.LENGTH_LONG).show();
                }
                else {
                    if ((!TextUtils.isEmpty(firsttask) && (Integer.parseInt(firsttime) > 24 || Integer.parseInt(firsttime) < 1)) || (!TextUtils.isEmpty(secondtask) && (Integer.parseInt(secondtime) > 24 || Integer.parseInt(secondtime) < 1))){
                        Toast.makeText(CalendarTask.this, "Please enter the time between 1 ~ 24", Toast.LENGTH_LONG).show();
                    }
                    else {
                        insertdata(todaydate, firsttask, firsttime, secondtask, secondtime);
                        if (TextUtils.isEmpty(firsttask) && TextUtils.isEmpty(firsttime) && TextUtils.isEmpty(secondtask) && TextUtils.isEmpty(secondtime)) {
                            deletebtn.setVisibility(View.INVISIBLE);
                            long id = db.delete(CalendarDatabase.TABLE_NAME, CalendarDatabase.COL_1 + " = " + todaydate, null);
                        } else {
                            deletebtn.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        }
    }

    private class DeleteTaskOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String todaydate = _dayofmonth.concat(_month).concat(_year);
            String firsttime = firsttimeet.getText().toString();
            String secondtime = secondtimeet.getText().toString();
            deletedata(todaydate, firsttime, secondtime);
            deletebtn.setVisibility(View.INVISIBLE);
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
