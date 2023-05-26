package com.example.imob301assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddStudent extends AppCompatActivity {
    private DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        myDB = new DBHelper(this);

        EditText studentName = findViewById(R.id.studentName);
        EditText studentSurname = findViewById(R.id.studentSurname);
        EditText studentDOB = findViewById(R.id.studentDOB);
        EditText studentPass = findViewById(R.id.studentPass);

        Button btn = findViewById(R.id.addStudentBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues contentValues = new ContentValues();

                // contentValues.put("")
            }
        });
    }
}