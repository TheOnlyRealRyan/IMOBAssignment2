package com.example.imob301assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    public static SharedPreferences preferences;

    // Name/Admin/Instructor
    // Password


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = this.getSharedPreferences("SharedPref", MODE_PRIVATE);

        // Main
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

                Context context = getBaseContext();

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
                    navigateToDashboard();
                }


            }
        });
    }

    void navigateToDashboard() {
        Intent intent = new Intent(MainActivity.this, ActionDetailHostActivity.class);
        startActivity(intent);
    }

}