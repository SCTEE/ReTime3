package com.example.retime;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CalendarDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "calendardetails.db";
    public static final String TABLE_NAME = "calendardetails";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "FirstTask";
    public static final String COL_3 = "FirstTime";
    public static final String COL_4 = "SecondTask";
    public static final String COL_5 = "SecondTime";


    public CalendarDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(" CREATE table " + TABLE_NAME+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT, FirstTask TEXT, FirstBreak TEXT, FirstGoal TEXT, SecondTask TEXT, SecondBreak TEXT, SecondGoal TEXT)");
        db.execSQL(" CREATE table " + TABLE_NAME+ "(ID INTEGER PRIMARY KEY, FirstTask TEXT, FirstTime TEXT, SecondTask TEXT, SecondTime TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" drop table if EXISTS "+TABLE_NAME); //drop older table if it is exist
        onCreate(db);
    }
}
