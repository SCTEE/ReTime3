package com.example.retime;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Task extends Fragment implements View.OnClickListener{
    // the fragment initialization parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    View view;
    Button AddTask, Backbtn, Savebtn, Deletebtn;
    TextView task1, break1, goal1, task2, break2, goal2;
    EditText tasket1, endtimeet1, goalet1, tasket2, endtimeet2, goalet2;

    String today, _year, _month, _dayofmonth, todaydate, ID;
    String[] todayarr;

    int position;
    SQLiteOpenHelper openHelper, openHelper2;
    SQLiteDatabase db, db2;

    public Task() {
        // Required empty public constructor
    }

    public static Task newInstance(String param1, String param2) {
        Task fragment = new Task();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        openHelper = new TaskDatabase(getActivity());
        openHelper2 = new CalendarDatabase(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_task, container, false);
        @SuppressLint("WrongConstant") SharedPreferences getprefs = getActivity().getSharedPreferences("MyTask", Context.MODE_PRIVATE);
        position = getprefs.getInt("TaskTime",0);
        db = openHelper.getWritableDatabase();
        db2 = openHelper2.getWritableDatabase();

        today = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        todayarr = today.split("/");
        _dayofmonth = new DecimalFormat("00").format(Integer.parseInt(todayarr[0]));
        _month = todayarr[1];
        _year = todayarr[2];
        todaydate = _dayofmonth.concat(_month).concat(_year);
        ID = todaydate.concat("/").concat(Integer.toString(position));

        Cursor data = db.rawQuery("Select * From " + TaskDatabase.TABLE_NAME + " Where " + TaskDatabase.COL_1 + " = '" + ID + "' AND " + TaskDatabase.COL_8 + " = '" + todaydate + "' AND " + TaskDatabase.COL_9 + " = " + position, null);

        AddTask = (Button) view.findViewById(R.id.AddTaskbtn);
        AddTask.setOnClickListener(this);
        Backbtn = (Button) view.findViewById(R.id.backbtn);
        Backbtn.setOnClickListener(this);
        Savebtn = (Button) view.findViewById(R.id.savebtn);
        Savebtn.setOnClickListener(this);
        Deletebtn = (Button) view.findViewById(R.id.deletebtn);
        Deletebtn.setOnClickListener(this);
        task1 = (TextView) view.findViewById(R.id.textView);
        break1 = (TextView) view.findViewById(R.id.textView2);
        goal1 = (TextView) view.findViewById(R.id.textView3);
        task2 = (TextView) view.findViewById(R.id.textView4);
        break2 = (TextView) view.findViewById(R.id.textView5);
        goal2 = (TextView) view.findViewById(R.id.textView6);
        tasket1 = (EditText) view.findViewById(R.id.editText);
        endtimeet1 = (EditText) view.findViewById(R.id.editText2);
        goalet1 = (EditText) view.findViewById(R.id.editText3);
        tasket2 = (EditText) view.findViewById(R.id.editText4);
        endtimeet2 = (EditText) view.findViewById(R.id.editText5);
        goalet2 = (EditText) view.findViewById(R.id.editText6);

        if(position == 23){
            endtimeet1.setEnabled(false);
            endtimeet2.setEnabled(false);
        }

        if (data.getCount() > 0){
            data.moveToFirst();
            tasket1.setText(data.getString(1));
            endtimeet1.setText(data.getString(2));
            goalet1.setText(data.getString(3));
            if (data.getString(4).length() != 0 || data.getString(5).length() != 0 || data.getString(6).length() != 0){
                AddTask.setVisibility(View.INVISIBLE);
                tasket2.setText(data.getString(4));
                endtimeet2.setText(data.getString(5));
                goalet2.setText(data.getString(6));
            }
            else {
                AddTask.setVisibility(View.VISIBLE);
            }
        }

        String firsttask = tasket1.getText().toString();
        String firstendtime = endtimeet1.getText().toString();
        String firstgoal = goalet1.getText().toString();
        String secondtask = tasket2.getText().toString();
        String secondendtime = endtimeet2.getText().toString();
        String secondgoal = goalet2.getText().toString();

        Cursor data2 = db2.rawQuery("Select * From " + CalendarDatabase.TABLE_NAME + " Where " + CalendarDatabase.COL_1 + " = " + todaydate + " AND " + CalendarDatabase.COL_2 + " = '" + secondtask + "' AND " + CalendarDatabase.COL_3 + " = '" + (position+1) +"'", null);
        if (data2.getCount() > 0){
            tasket2.setEnabled(false);
        }
        Cursor data3 = db2.rawQuery("Select * From " + CalendarDatabase.TABLE_NAME + " Where " + CalendarDatabase.COL_1 + " = " + todaydate + " AND " + CalendarDatabase.COL_4 + " = '" + secondtask + "' AND " + CalendarDatabase.COL_5 + " = '" + (position+1) +"'", null);
        if (data3.getCount() > 0){
            tasket2.setEnabled(false);
        }
        Cursor data4 = db2.rawQuery("Select * From " + CalendarDatabase.TABLE_NAME + " Where " + CalendarDatabase.COL_1 + " = " + todaydate + " AND " + CalendarDatabase.COL_2 + " = '" + firsttask + "' AND " + CalendarDatabase.COL_3 + " = '" + (position+1) +"'" + " AND " + CalendarDatabase.COL_4 + " = '" + secondtask + "' AND " + CalendarDatabase.COL_5 + " = '" + (position+1) +"'", null);
        if (data4.getCount() > 0){
            tasket1.setEnabled(false);
            tasket2.setEnabled(false);
        }

        if (TextUtils.isEmpty(firsttask) && TextUtils.isEmpty(firstendtime) && TextUtils.isEmpty(firstgoal) && TextUtils.isEmpty(secondtask) && TextUtils.isEmpty(secondendtime) && TextUtils.isEmpty(secondgoal)){
            Deletebtn.setVisibility(View.INVISIBLE);
        }
        else {
            Deletebtn.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), home.class);
        String firsttask = tasket1.getText().toString();
        String firstendtime = endtimeet1.getText().toString();
        String firstgoal = goalet1.getText().toString();
        String secondtask = tasket2.getText().toString();
        String secondendtime = endtimeet2.getText().toString();
        String secondgoal = goalet2.getText().toString();
        int firsttime = 0, secondtime = 0;
        switch (v.getId()) {
            case R.id.AddTaskbtn:

                if(TextUtils.isEmpty(firsttask)){
                    Toast.makeText(getActivity(), "Please fill in the first task", Toast.LENGTH_LONG).show();
                }
                else {
                    AddTask.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.backbtn:

                startActivity(intent);
                break;
            case R.id.savebtn:

                if (TextUtils.isEmpty(firsttask) && TextUtils.isEmpty(firstendtime) && TextUtils.isEmpty(firstgoal) && TextUtils.isEmpty(secondtask) && TextUtils.isEmpty(secondendtime) && TextUtils.isEmpty(secondgoal)){
                    Toast.makeText(getActivity(), "Nothing is save", Toast.LENGTH_LONG).show();
                }
                else{

                    if(TextUtils.isEmpty(firsttask) && !TextUtils.isEmpty(secondtask)){
                        Toast.makeText(getActivity(), "Please fill in the first task", Toast.LENGTH_LONG).show();
                    }
                    else {
                        if (((TextUtils.isEmpty(firsttask)) && !(TextUtils.isEmpty(firstendtime) && TextUtils.isEmpty(firstgoal))) || ((TextUtils.isEmpty(secondtask)) && !(TextUtils.isEmpty(secondendtime) && TextUtils.isEmpty(secondgoal)))) {
                            Toast.makeText(getActivity(), "Please enter task name", Toast.LENGTH_LONG).show();
                        } else {

                            if (!TextUtils.isEmpty(firstendtime)){
                                firsttime = Integer.parseInt(firstendtime);
                            }

                            if (!TextUtils.isEmpty(secondendtime)){
                                secondtime = Integer.parseInt(secondendtime);
                            }

                            if ((!TextUtils.isEmpty(firsttask) && ((firsttime > 24 || firsttime <= (position + 1)) && firsttime != 0 )) || (!TextUtils.isEmpty(secondtask) && ((secondtime > 24 || secondtime <= (position + 1)) && secondtime != 0))) {
                                Toast.makeText(getActivity(), "Please enter the time between " + (position + 2) + " ~ 24", Toast.LENGTH_LONG).show();
                            } else {
                                appenddata(position, firsttask, firstendtime, firstgoal, secondtask, secondendtime, secondgoal);
                                if (TextUtils.isEmpty(firsttask) && TextUtils.isEmpty(firstendtime) && TextUtils.isEmpty(firstgoal) && TextUtils.isEmpty(secondtask) && TextUtils.isEmpty(secondendtime) && TextUtils.isEmpty(secondgoal)) {
                                    Deletebtn.setVisibility(View.INVISIBLE);
                                    long id = db.delete(TaskDatabase.TABLE_NAME, TaskDatabase.COL_1 + " = " + position, null);
                                    AddTask.setVisibility(View.VISIBLE);
                                } else {
                                    Deletebtn.setVisibility(View.VISIBLE);
                                }
                                startActivity(intent);
                            }
                        }
                    }
                }
                break;
            case R.id.deletebtn:
                Cursor data = db2.rawQuery("Select * From " + CalendarDatabase.TABLE_NAME + " Where " + CalendarDatabase.COL_1 + " = " + todaydate + " AND (" + CalendarDatabase.COL_3 + " = '" + (position+1) + "' OR " + CalendarDatabase.COL_5 + " = '" + (position+1) +"')", null);
                if (data.getCount() > 0){
                    Toast.makeText(getActivity(), "Please delete from calendar", Toast.LENGTH_LONG).show();
                }
                else {
                    tasket1.setText("");
                    endtimeet1.setText("");
                    goalet1.setText("");
                    tasket2.setText("");
                    endtimeet2.setText("");
                    goalet2.setText("");
                    AddTask.setVisibility(View.VISIBLE);
                    deletedata(ID);
                    Deletebtn.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                break;
        }
    }

    public void deletedata (String ID){
        long id = db.delete(TaskDatabase.TABLE_NAME, TaskDatabase.COL_1 + " = '" + ID + "'", null);
        Toast.makeText(getActivity(), "delete successfully", Toast.LENGTH_LONG).show();
    }

    public void appenddata (int position, String task1, String break1, String goal1, String task2, String break2, String goal2) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(TaskDatabase.COL_1, ID);
        contentValues.put(TaskDatabase.COL_2, task1);
        contentValues.put(TaskDatabase.COL_3, break1);
        contentValues.put(TaskDatabase.COL_4, goal1);
        contentValues.put(TaskDatabase.COL_5, task2);
        contentValues.put(TaskDatabase.COL_6, break2);
        contentValues.put(TaskDatabase.COL_7, goal2);
        contentValues.put(TaskDatabase.COL_8, todaydate);
        contentValues.put(TaskDatabase.COL_9, position);
        long id = db.replace(TaskDatabase.TABLE_NAME, null, contentValues);
        Toast.makeText(getActivity(), "save successfully", Toast.LENGTH_LONG).show();
    }
}
