package com.example.imob301assignment;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.imob301assignment.placeholder.PlaceholderContent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class ActivityFunctionality {
    Context _context;
    DBHelper myDB;
    View _baseView;

    final Calendar myCalendar = Calendar.getInstance();

    ActivityFunctionality(Context context, View baseView) {
        _context = context;
        _baseView = baseView;
        myDB = new DBHelper(_context);
    }

    public void AddInstructor() {
        EditText instructorName = _baseView.findViewById(R.id.instructorUsername);
        EditText instructorPass = _baseView.findViewById(R.id.instructorPass);

        Button btn = _baseView.findViewById(R.id.addInstructorBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String instructorNameVal = instructorName.getText().toString();
                String instructorPassVal = instructorPass.getText().toString();

                ContentValues contentValues = new ContentValues();
                contentValues.put("username", instructorNameVal);
                contentValues.put("password", instructorPassVal);

                boolean result = myDB.insertData("Instructors", contentValues);

                if (result) {
                    instructorName.setText("");
                    instructorPass.setText("");

                    Toast.makeText(
                        _context, "Add Instructor Successful!", Toast.LENGTH_SHORT
                    ).show();

                } else {
                    Toast.makeText(
                        _context, "An unknown error has occured!", Toast.LENGTH_SHORT
                    ).show();
                    // take user back
                }
            }
        });
    }

    public void AddStudent() {
        EditText studentName = _baseView.findViewById(R.id.studentName);
        EditText studentSurname = _baseView.findViewById(R.id.studentSurname);
        EditText studentDOB = _baseView.findViewById(R.id.studentDOB);
        EditText studentPass = _baseView.findViewById(R.id.studentPass);


        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);

                String myFormat="yyyy-MM-dd";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                studentDOB.setText(dateFormat.format(myCalendar.getTime()));
            }
        };

        studentDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(
                        _context, date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        Button btn = _baseView.findViewById(R.id.addStudentBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String studentNameVal = studentName.getText().toString();
                String studentSurnameVal = studentSurname.getText().toString();
                String studentUsername = studentNameVal.toLowerCase() + "_" + studentSurnameVal.toLowerCase();

                ContentValues contentValues = new ContentValues();
                contentValues.put("name", studentNameVal);
                contentValues.put("surname", studentNameVal);
                contentValues.put("password", studentPass.getText().toString());
                contentValues.put("dob", studentDOB.getText().toString());
                contentValues.put("username", studentUsername);


                boolean result = myDB.insertData("Students", contentValues);

                if (result) {
                    studentName.setText("");
                    studentSurname.setText("");
                    studentPass.setText("");
                    studentDOB.setText("");

                    Toast.makeText(
                        _context, "Add Student Successful!", Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Toast.makeText(
                        _context, "An unknown error has occured!", Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    public void CreateModule() {
        EditText moduleName = _baseView.findViewById(R.id.moduleName);
        EditText moduleDuration = _baseView.findViewById(R.id.moduleDuration);
        Button btn = _baseView.findViewById(R.id.createModuleBtn);

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
                        _context, "Create Module Successful!", Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Toast.makeText(
                        _context, "An unknown error has occured!", Toast.LENGTH_SHORT
                    ).show();
                    // take user back
                }
            }
        });
    }

    public void AddTask() {

        EditText taskName = _baseView.findViewById(R.id.taskName);
        EditText taskDueDate = _baseView.findViewById(R.id.taskDueDate);
        EditText taskModule = _baseView.findViewById(R.id.taskModuleID);
        Button btn = _baseView.findViewById(R.id.btnAddTask);
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);

                String myFormat="yyyy-MM-dd";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                taskDueDate.setText(dateFormat.format(myCalendar.getTime()));
            }
        };

        taskDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(
                        _context, date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crashes here?
                String taskNameVal = taskName.getText().toString();
                String taskDueDateVal = taskDueDate.getText().toString();
                Integer taskModuleVal = Integer.parseInt(taskModule.getText().toString());


                ContentValues contentValues = new ContentValues();
                contentValues.put("name", taskNameVal);
                contentValues.put("dueDate", taskDueDateVal);
                contentValues.put("moduleID", taskModuleVal);

                boolean result = myDB.insertData("Tasks", contentValues);

                if (result) {
                    taskName.setText("");
                    taskDueDate.setText("");
                    taskModule.setText("");

                    Toast.makeText(
                        _context, "Created Task Successfully!", Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Toast.makeText(
                        _context, "An unknown error has occurred!", Toast.LENGTH_SHORT
                    ).show();
                    // take user back
                }
            }
        });
    }
    public void ViewTasks(){
        Cursor cursor = myDB.getAllItems("Tasks");
        List<String> taskIDs = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> moduleIDs = new ArrayList<>();
        List<String> dueDates = new ArrayList<>();

        int COL_1 = cursor.getColumnIndex ("taskID");
        int COL_2 = cursor.getColumnIndex ("name");
        int COL_3 = cursor.getColumnIndex ("moduleID");
        int COL_4 = cursor.getColumnIndex ("dueDate");
        while (cursor.moveToNext()) {
            String itemID = cursor.getString(COL_1);
            String itemName = cursor.getString(COL_2);
            String itemModuleID = cursor.getString(COL_3);
            String itemDueDate = cursor.getString(COL_4);
            taskIDs.add(itemID);
            names.add(itemName);
            moduleIDs.add(itemModuleID);
            dueDates.add(itemDueDate);
        }
        // String x = "";
        ListView taskListView = _baseView.findViewById(R.id.taskListView);
        taskListView.setAdapter(new ArrayAdapter<String>(_context, android.R.layout.simple_list_item_1, names));


    }

    public void Logout() {
        MainActivity.preferences.edit().remove("role").apply();
        PlaceholderContent.clear();

        Intent intent = new Intent(_context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        _context.startActivity(intent);
    }
}
