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
    Button AddTask, Backbtn, Savebtn, Deletebtn;
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
        @SuppressLint("WrongConstant") SharedPreferences getprefs = getActivity().getSharedPreferences("MyTask", Context.MODE_PRIVATE);
        position = getprefs.getInt("TaskTime",0);
        db = openHelper.getWritableDatabase();
        Cursor data = db.rawQuery("Select * From " + TaskDatabase.TABLE_NAME + " Where " + TaskDatabase.COL_1 + " = " + position, null);
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
        breaket1 = (EditText) view.findViewById(R.id.editText2);
        goalet1 = (EditText) view.findViewById(R.id.editText3);
        tasket2 = (EditText) view.findViewById(R.id.editText4);
        breaket2 = (EditText) view.findViewById(R.id.editText5);
        goalet2 = (EditText) view.findViewById(R.id.editText6);
        if (data.getCount() > 0){
            data.moveToFirst();
            tasket1.setText(data.getString(1));
            breaket1.setText(data.getString(2));
            goalet1.setText(data.getString(3));
            Log.d("blabla", Integer.toString(data.getString(4).length()));
            if (data.getString(4).length() != 0 || data.getString(5).length() != 0 || data.getString(6).length() != 0){
                AddTask.setVisibility(View.INVISIBLE);
                tasket2.setText(data.getString(4));
                breaket2.setText(data.getString(5));
                goalet2.setText(data.getString(6));
            }
            else {
                AddTask.setVisibility(View.VISIBLE);
            }
        }

        String firsttask = tasket1.getText().toString();
        String firstbreak = breaket1.getText().toString();
        String firstgoal = goalet1.getText().toString();
        String secondtask = tasket2.getText().toString();
        String secondbreak = breaket2.getText().toString();
        String secondgoal = goalet2.getText().toString();
        if (TextUtils.isEmpty(firsttask) && TextUtils.isEmpty(firstbreak) && TextUtils.isEmpty(firstgoal) && TextUtils.isEmpty(secondtask) && TextUtils.isEmpty(secondbreak) && TextUtils.isEmpty(secondgoal)){
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
        String firstbreak = breaket1.getText().toString();
        String firstgoal = goalet1.getText().toString();
        String secondtask = tasket2.getText().toString();
        String secondbreak = breaket2.getText().toString();
        String secondgoal = goalet2.getText().toString();
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

                if(TextUtils.isEmpty(firsttask) && !TextUtils.isEmpty(secondtask)){
                    Toast.makeText(getActivity(), "Please fill in the first task", Toast.LENGTH_LONG).show();
                }
                else {
                    if (((TextUtils.isEmpty(firsttask)) && !(TextUtils.isEmpty(firstbreak) && TextUtils.isEmpty(firstgoal))) || ((TextUtils.isEmpty(secondtask)) && !(TextUtils.isEmpty(secondbreak) && TextUtils.isEmpty(secondgoal)))) {
                        Toast.makeText(getActivity(), "Please enter task name", Toast.LENGTH_LONG).show();
                    } else {

                        appenddata(position, firsttask, firstbreak, firstgoal, secondtask, secondbreak, secondgoal);
                        if (TextUtils.isEmpty(firsttask) && TextUtils.isEmpty(firstbreak) && TextUtils.isEmpty(firstgoal) && TextUtils.isEmpty(secondtask) && TextUtils.isEmpty(secondbreak) && TextUtils.isEmpty(secondgoal)) {
                            Deletebtn.setVisibility(View.INVISIBLE);
                            long id = db.delete(TaskDatabase.TABLE_NAME, TaskDatabase.COL_1 + " = " + position, null);
                            AddTask.setVisibility(View.VISIBLE);
                        } else {
                            Deletebtn.setVisibility(View.VISIBLE);
                        }
                        startActivity(intent);
                    }
                }
                break;
            case R.id.deletebtn:
                tasket1.setText("");
                breaket1.setText("");
                goalet1.setText("");
                tasket2.setText("");
                breaket2.setText("");
                goalet2.setText("");
                AddTask.setVisibility(View.VISIBLE);
                deletedata(position);
                Deletebtn.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }

    public void deletedata (int position){
        long id = db.delete(TaskDatabase.TABLE_NAME, TaskDatabase.COL_1 + " = " + position, null);
        Toast.makeText(getActivity(), "delete successfully", Toast.LENGTH_LONG).show();
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
