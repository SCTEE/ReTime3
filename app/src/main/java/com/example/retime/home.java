package com.example.retime;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class home extends FragmentActivity implements TimeTableAdapter.OnFragmentListener {
    String[] TimeList = { "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00"};


    RecyclerView TimeTableRecycleView;
    TimeTableAdapter TimeTableAdapter;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    ImageButton Calendars, Tasks;

    String currenttime;


    List<com.example.retime.TimeTable> TimeTableList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "RetimeChannel")
                .setSmallIcon(R.drawable.smallcalendar)
                .setContentTitle("Retime Calendar")
                .setContentText("You have a task!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notifimgr = NotificationManagerCompat.from(this);
        Calendars = findViewById(R.id.calendar);
        Calendars.setOnClickListener(new CalendarsOnClickListener());
        Tasks = findViewById(R.id.tasks);
        Tasks.setOnClickListener(new TasksOnClickListener());
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
                currenttime = new SimpleDateFormat("kk:mm").format(Calendar.getInstance().getTime());
                if (currenttime.substring(0,2).equals(TimeList[i].substring(0,2))){
                    notifimgr.notify(0, builder.build());
                }

            }
            else {
                com.example.retime.TimeTable TimeTable = new com.example.retime.TimeTable (i, time, "", "");
                TimeTableList.add(TimeTable); //Add company to the company list
            }

        }

//        notifimgr.notify(0, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name";
            String description = "Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("RetimeChannel", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(channel);
        }
    }

    private class CalendarsOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(home.this, CalendarTask.class);
            startActivity(intent);
        }
    }

    private class TasksOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
//            notifimgr.notify(10, builder.build());
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
