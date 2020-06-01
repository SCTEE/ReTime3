package com.example.retime;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class UserRegister extends AppCompatActivity {
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Button _reg_btn;
    EditText _user_reg, _fname_reg, _lname_reg, _pass_reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        openHelper = new DatabaseHelper(this);
        _reg_btn = (Button)findViewById(R.id.reg_btn);
        _user_reg = (EditText)findViewById(R.id.user_reg);
        _fname_reg = (EditText)findViewById(R.id.fname_reg);
        _lname_reg = (EditText)findViewById(R.id.lname_reg);
        _pass_reg = (EditText)findViewById(R.id.pass_reg);

        _reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = openHelper.getWritableDatabase();
                String fname = _fname_reg.getText().toString();
                String lname = _lname_reg.getText().toString();
                String username = _user_reg.getText().toString();
                String password = _pass_reg.getText().toString();
                insertdata(fname, lname, username, password);
                Toast.makeText(getApplicationContext(), "register successfully", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void insertdata (String fname, String lname, String username, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_2, fname);
        contentValues.put(DatabaseHelper.COL_3, lname);
        contentValues.put(DatabaseHelper.COL_4, password);
        contentValues.put(DatabaseHelper.COL_5, username);
        long id= db.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
    }
}
