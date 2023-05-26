package com.example.imob301assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddInstructor extends AppCompatActivity {
    private DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instructor);

        myDB = new DBHelper(this);

        EditText instructorName = findViewById(R.id.instructorUsername);
        EditText instructorPass = findViewById(R.id.instructorPass);

        Button btn = findViewById(R.id.addInstructorBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String instructorNameVal = instructorName.getText().toString();
                String instructorPassVal = instructorPass.getText().toString();

                ContentValues contentValues = new ContentValues();
                contentValues.put("username", instructorNameVal);
                contentValues.put("password", instructorPassVal);

                boolean result = myDB.insertData("Students", contentValues);

                if (result) {
                    instructorName.setText("");
                    instructorPass.setText("");

                    Toast.makeText(
                AddInstructor.this, "Add Instructor Successful!", Toast.LENGTH_SHORT
                    ).show();

                } else {
                    Toast.makeText(
                AddInstructor.this, "An unknown error has occured!", Toast.LENGTH_SHORT
                    ).show();
                    // take user back
                }
            }
        });
    }
}