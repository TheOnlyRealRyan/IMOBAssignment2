package com.example.imob301assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateModule extends AppCompatActivity {
    private DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_module);

        myDB = new DBHelper(this);

        EditText moduleName = findViewById(R.id.moduleName);
        EditText moduleDuration = findViewById(R.id.moduleDuration);

        Button btn = findViewById(R.id.createModuleBtn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String moduleNameVal = moduleName.getText().toString();
                Integer moduleDurationVal = Integer.parseInt(moduleDuration.getText().toString());

                ContentValues contentValues = new ContentValues();
                contentValues.put("name", moduleNameVal);
                contentValues.put("duration", moduleDurationVal);

                boolean result = myDB.insertData("Modules", contentValues);

                if (result) {
                    moduleName.setText("");
                    moduleDuration.setText("");

                    Toast.makeText(
                CreateModule.this, "Create Module Successful!", Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Toast.makeText(
                            CreateModule.this, "An unknown error has occured!", Toast.LENGTH_SHORT
                    ).show();
                    // take user back
                }
            }
        });

    }
}