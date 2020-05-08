package com.example.retime;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Task#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Task extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;
    Button AddTask, Backbtn, Savebtn;
    TextView task1, break1, goal1, task2, break2, goal2;
    EditText tasket1, breaket1, goalet1, tasket2, breaket2, goalet2;

    int position;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    public Task() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Task.
     */
    // TODO: Rename and change types and number of parameters
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_task, container, false);

        AddTask = (Button) view.findViewById(R.id.AddTaskbtn);
        AddTask.setOnClickListener(this);
        Backbtn = (Button) view.findViewById(R.id.backbtn);
        Backbtn.setOnClickListener(this);
        Savebtn = (Button) view.findViewById(R.id.savebtn);
        Savebtn.setOnClickListener(this);
        task1 = (TextView) view.findViewById(R.id.textView);
        break1 = (TextView) view.findViewById(R.id.textView2);
        goal1 = (TextView) view.findViewById(R.id.textView3);
        task2 = (TextView) view.findViewById(R.id.textView4);
        break2 = (TextView) view.findViewById(R.id.textView5);
        goal2 = (TextView) view.findViewById(R.id.textView6);
        tasket1 = (EditText) view.findViewById(R.id.editText);
        breaket1 = (EditText) view.findViewById(R.id.editText2);
        goalet1 = (EditText) view.findViewById(R.id.editText3);
        tasket2 = (EditText) view.findViewById(R.id.editText4);
        breaket2 = (EditText) view.findViewById(R.id.editText5);
        goalet2 = (EditText) view.findViewById(R.id.editText6);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.AddTaskbtn:
                AddTask.setVisibility(View.INVISIBLE);
                task1.setVisibility(View.VISIBLE);
                break1.setVisibility(View.VISIBLE);
                goal1.setVisibility(View.VISIBLE);
                task2.setVisibility(View.VISIBLE);
                break2.setVisibility(View.VISIBLE);
                goal2.setVisibility(View.VISIBLE);
                tasket1.setVisibility(View.VISIBLE);
                breaket1.setVisibility(View.VISIBLE);
                goalet1.setVisibility(View.VISIBLE);
                tasket2.setVisibility(View.VISIBLE);
                breaket2.setVisibility(View.VISIBLE);
                goalet2.setVisibility(View.VISIBLE);
                break;
            case R.id.backbtn:
                Intent intent = new Intent(getActivity(), home.class);
                startActivity(intent);
                break;
            case R.id.savebtn:
                @SuppressLint("WrongConstant") SharedPreferences getprefs = getActivity().getSharedPreferences("MyTask", Context.MODE_PRIVATE);
                position = getprefs.getInt("TaskTime",0);
                db = openHelper.getWritableDatabase();
                String firsttask = tasket1.getText().toString();
                String firstbreak = breaket1.getText().toString();
                String firstgoal = goalet1.getText().toString();
                String secondtask = tasket2.getText().toString();
                String secondbreak = breaket2.getText().toString();
                String secondgoal = goalet2.getText().toString();
                appenddata(position, firsttask, firstbreak, firstgoal, secondtask, secondbreak, secondgoal);
                break;
            default:

                break;
        }
    }

    public void appenddata (int position, String task1, String break1, String goal1, String task2, String break2, String goal2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskDatabase.COL_1, position);
        contentValues.put(TaskDatabase.COL_2, task1);
        contentValues.put(TaskDatabase.COL_3, break1);
        contentValues.put(TaskDatabase.COL_4, goal1);
        contentValues.put(TaskDatabase.COL_5, task2);
        contentValues.put(TaskDatabase.COL_6, break2);
        contentValues.put(TaskDatabase.COL_7, goal2);
        long id = db.replace(TaskDatabase.TABLE_NAME, null, contentValues);
        Toast.makeText(getActivity(), "save successfully", Toast.LENGTH_LONG).show();
    }
}
