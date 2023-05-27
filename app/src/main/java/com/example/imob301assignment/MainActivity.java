package com.example.imob301assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static SharedPreferences preferences;

    private DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = this.getSharedPreferences("SharedPref", MODE_PRIVATE);
        myDB = new DBHelper(this);


        Spinner roles = findViewById(R.id.appRoles);
        EditText userName = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);

        Button signIn = findViewById(R.id.signIn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String role = roles.getSelectedItem().toString();
                String userNameVal = userName.getText().toString();
                String passwordVal = password.getText().toString();

                if (role.equals("Admin")) {
                    if (userNameVal.toLowerCase().equals("admin") &&
                        passwordVal.toLowerCase().equals("admin")) {

                        SharedPreferences.Editor myEdit = preferences.edit();
                        myEdit.putString("role", "admin");
                        myEdit.apply();

                        navigateToDashboard();
                    }
                }
                else {
                    String tableName;
                    if (role.equals("Instructor")) {
                        tableName = "Instructors";
                    } else {
                        tableName = "Students";
                    }

                    Cursor cursor = myDB.getUser(tableName, userNameVal, passwordVal);

                    if (cursor.getCount() == 1) {
                        SharedPreferences.Editor myEdit = preferences.edit();
                        myEdit.putString("role", role.toLowerCase());
                        myEdit.apply();

                        navigateToDashboard();
                    } else {
                        Toast.makeText(
                            getBaseContext(), "Incorrect Login Details!", Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            }
        });
    }

    void navigateToDashboard() {
        Intent intent = new Intent(MainActivity.this, ActionDetailHostActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}