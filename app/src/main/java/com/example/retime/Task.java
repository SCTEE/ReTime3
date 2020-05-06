package com.example.retime;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


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
    Button AddTask, Backbtn;

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
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.AddTaskbtn:
                AddTask.setVisibility(View.INVISIBLE);
                Fragment TaskDetails2frag = new TaskDetails(); //Declare a dynamic fragment variable
                FragmentManager TaskDetailsfragmentManager = getFragmentManager(); //declare a fragment manager
                FragmentTransaction TaskDetailsfragmentTransaction = TaskDetailsfragmentManager.beginTransaction(); //declare a fragment transaction
//                TaskDetailsfragmentTransaction.replace(R.id.TaskDetailslayout2,TaskDetails2frag, "TaskDetails1Fragment").addToBackStack(null); //replace the fragment that user is selected to the screen and add to back stack
                TaskDetailsfragmentTransaction.replace(R.id.TaskDetailslayout2,TaskDetails2frag, "TaskDetails1Fragment");//.addToBackStack(null); //replace the fragment that user is selected to the screen and add to back stack
                TaskDetailsfragmentTransaction.commit(); //commit the changes of the fragment transaction
                break;
            case R.id.backbtn:
                Intent intent = new Intent(getActivity(), home.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
