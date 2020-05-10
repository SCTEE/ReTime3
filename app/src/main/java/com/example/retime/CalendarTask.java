package com.example.retime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarTask extends AppCompatActivity {

    ImageButton Tasks, Next, Prev;
    TextView currentdate;
    GridView calendardate;
    int maxdays = 31;
    List<Date> date = new ArrayList<>();
    List<Events> events = new ArrayList<>();

    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_calendartask);

        Tasks = findViewById(R.id.tasks);
        Tasks.setOnClickListener(new TasksOnClickListener());
        currentdate = findViewById(R.id.textView7);
        currentdate.setText(dateFormat.format(Calendar.getInstance().getTime()));
    }

    private class TasksOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(CalendarTask.this, home.class);
            startActivity(intent);
        }
    }

}
