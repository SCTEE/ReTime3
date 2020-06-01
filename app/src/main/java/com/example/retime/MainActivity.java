package com.example.retime;

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
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    //variable declaration
    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;
     Button _register_btn, _login_btn, _facebook_login_btn;
     EditText _main_password, _main_username;
     Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize database helper
        openHelper = new DatabaseHelper(this);
        db = openHelper.getReadableDatabase();

        //get id from view
        _facebook_login_btn = (Button)findViewById(R.id.facebooklogin);
        _register_btn = (Button)findViewById(R.id.register_btn);
        _main_username = (EditText)findViewById(R.id.main_username);
        _main_password = (EditText)findViewById(R.id.main_password);
        _login_btn = (Button)findViewById(R.id.login_btn);

        //set listener to button
        _register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to register page
                Intent intent = new Intent(MainActivity.this, UserRegister.class);
                startActivity(intent);
            }
        });

        //set listener to button
        _login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verify if user account password is correct
                String username = _main_username.getText().toString();
                String password = _main_password.getText().toString();
                cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COL_5 + " =? AND " + DatabaseHelper.COL_4 + " =? ", new String[] {username, password});
                if (cursor!=null){
                    if(cursor.getCount() > 0){
                        Toast.makeText(getApplicationContext(), "login successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, home.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        //set listener to button
        _facebook_login_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //go to facebook login page
                Intent intent = new Intent(MainActivity.this, FacebookLoginActivity.class);
                startActivity(intent);
            }
        });

    }



}
