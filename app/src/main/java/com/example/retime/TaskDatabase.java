package com.example.retime;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDatabase extends SQLiteOpenHelper {
    //database column name
    public static final String DATABASE_NAME = "taskdetails.db";
    public static final String TABLE_NAME = "taskdetails";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "FirstTask";
    public static final String COL_3 = "FirstEndTime";
    public static final String COL_4 = "FirstGoal";
    public static final String COL_5 = "SecondTask";
    public static final String COL_6 = "SecondEndTime";
    public static final String COL_7 = "SecondGoal";
    public static final String COL_8 = "Date";
    public static final String COL_9 = "Position";

    public TaskDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create database table
        db.execSQL(" CREATE table " + TABLE_NAME+ "(ID TEXT PRIMARY KEY, FirstTask TEXT, FirstEndTime TEXT, FirstGoal TEXT, SecondTask TEXT, SecondEndTime TEXT, SecondGoal TEXT, Date TEXT, Position INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" drop table if EXISTS "+TABLE_NAME); //drop older table if it is exist
        onCreate(db);
    }
}

