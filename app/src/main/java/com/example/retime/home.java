package com.example.retime;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class home extends FragmentActivity implements TimeTableAdapter.OnFragmentListener {
    String[] TimeList = { "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00"};
//    String[] companynameList = {"9News", "ABC News", "The Age", "Sydney Morning Herald"}; //String variable of the company name list

    RecyclerView TimeTableRecycleView;
    TimeTableAdapter TimeTableAdapter;

//    Button AddTask;

    List<com.example.retime.TimeTable> TimeTableList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        AddTask = findViewById(R.id.AddTaskbtn);
//        AddTask.setOnClickListener(new AddTaskOnClickListner());
        TimeTableRecycleView = findViewById(R.id.schedule);
        // Create adapter passing in the sample user data
        TimeTableAdapter = new TimeTableAdapter(TimeTableList, home.this, this);
        // Attach the adapter to the recyclerview to populate items
        TimeTableRecycleView.setAdapter(TimeTableAdapter);
        // Set layout manager to position the items
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        TimeTableRecycleView.setLayoutManager(layoutManager);

        //Filling our recycler view list
        for (int i= 0; i< TimeList.length; i++ )
        { String time = TimeList[i];

            com.example.retime.TimeTable TimeTable = new com.example.retime.TimeTable (i, time, "test1", "test2");
            TimeTableList.add(TimeTable); //Add company to the company list
        }
    }

//    private class AddTaskOnClickListner implements View.OnClickListener{
//        @Override
//        public void onClick(View v) {
//            Fragment TaskDetails2frag = new TaskDetails(); //Declare a dynamic fragment variable
//            FragmentManager TaskDetailsfragmentManager = getSupportFragmentManager(); //declare a fragment manager
//            FragmentTransaction TaskDetailsfragmentTransaction = TaskDetailsfragmentManager.beginTransaction(); //declare a fragment transaction
//            TaskDetailsfragmentTransaction.replace(R.id.TaskDetailslayout,TaskDetails2frag, "TaskDetails1Fragment").addToBackStack(null); //replace the fragment that user is selected to the screen and add to back stack
//            TaskDetailsfragmentTransaction.commit(); //commit the changes of the fragment transaction
//        }
//    }

    public void onFragmentClick(int position) {

        Fragment Taskfrag = new Task(); //Declare a dynamic fragment variable
        Fragment TaskDetails1frag = new TaskDetails(); //Declare a dynamic fragment variable


//        switch (position) {
//            case 0:
//                frag = new NineNewsFragment(); //instantiate Nine News Fragment to the frag variable
//                break;
//            case 1:
//                frag = new ABCFragment(); //instantiate ABC News Fragment to the frag variable
//                break;
//            case 2:
//                frag = new AgeNews(); //instantiate Age News Fragment to the frag variable
//                break;
//            case 3:
//                frag = new SydneyMorningHeraldNews(); //instantiate Sydney Morning Herald News Fragment to the frag variable
//                break;
//            default:
//                throw new IllegalStateException("Unexpected value: " + position);
//        }

        FragmentManager TaskfragmentManager = getSupportFragmentManager(); //declare a fragment manager
        FragmentTransaction TaskfragmentTransaction = TaskfragmentManager.beginTransaction(); //declare a fragment transaction
//        TaskfragmentTransaction.replace(R.id.TaskLayout,Taskfrag, "TaskFragment").addToBackStack(null); //replace the fragment that user is selected to the screen and add to back stack
        TaskfragmentTransaction.replace(R.id.TaskLayout,Taskfrag, "TaskFragment");//.addToBackStack(null); //replace the fragment that user is selected to the screen and add to back stack
        TaskfragmentTransaction.commit(); //commit the changes of the fragment transaction

        FragmentManager TaskDetailsfragmentManager = getSupportFragmentManager(); //declare a fragment manager
        FragmentTransaction TaskDetailsfragmentTransaction = TaskDetailsfragmentManager.beginTransaction(); //declare a fragment transaction
//        TaskDetailsfragmentTransaction.replace(R.id.TaskDetailslayout1,TaskDetails1frag, "TaskDetails1Fragment").addToBackStack(null); //replace the fragment that user is selected to the screen and add to back stack
        TaskDetailsfragmentTransaction.replace(R.id.TaskDetailslayout1,TaskDetails1frag, "TaskDetails1Fragment");//.addToBackStack(null); //replace the fragment that user is selected to the screen and add to back stack
        TaskDetailsfragmentTransaction.commit(); //commit the changes of the fragment transaction

//        TaskDetailsfragmentTransaction.replace(R.id.TaskLayout,Taskfrag, "TaskFragment").addToBackStack(null); //replace the fragment that user is selected to the screen and add to back stack
//        TaskDetailsfragmentTransaction.commit(); //commit the changes of the fragment transaction

    }
}
