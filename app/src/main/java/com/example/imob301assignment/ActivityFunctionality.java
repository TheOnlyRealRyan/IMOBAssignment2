package com.example.imob301assignment;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imob301assignment.placeholder.PlaceholderContent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
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

    class TaskAdapter extends BaseAdapter {
        List<String> taskIDs;
        List<String> taskNames;
        List<String> moduleIDs;
        List<String> taskDueDates;
        List<Integer> taskCompleted;

        public TaskAdapter(
            List<String> ids, List<String> names, List<String> moduleIds,
            List<String> dueDates, List<Integer> completedTasks
        ) {
            taskIDs = ids;
            taskNames = names;
            moduleIDs = moduleIds;
            taskDueDates = dueDates;
            taskCompleted = completedTasks;
        }
        @Override
        public int getCount() {
            return taskIDs.toArray().length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(_context).inflate(R.layout.task_item, parent, false);
            }

            TextView taskID = convertView.findViewById(R.id.itemTaskId);
            taskID.setText(taskIDs.get(position));

            TextView taskName = convertView.findViewById(R.id.itemTaskName);
            taskName.setText(taskNames.get(position));

            TextView taskDueDate = convertView.findViewById(R.id.itemTaskDueDate);
            taskDueDate.setText("Due Date: " + taskDueDates.get(position));

            TextView taskModuleID = convertView.findViewById(R.id.itemTaskModuleId);
            taskModuleID.setText("Module: " + moduleIDs.get(position));

            CheckBox completed = convertView.findViewById(R.id.itemTaskCompleted);
            boolean val = false;

            if (taskCompleted.get(position) == 1) {
                val = true;
            }

            completed.setChecked(val);

            completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    String studentID = MainActivity.preferences.getString("studentID", "1");
                    boolean result = myDB.updateCompletion(taskIDs.get(position), studentID, b);

                    if (result) {
                        Toast.makeText(
                            _context, "Task Completion Status Changed!", Toast.LENGTH_SHORT
                        ).show();
                    } else {
                        Toast.makeText(
                            _context, "Unknown Error Occurred!", Toast.LENGTH_SHORT
                        ).show();

                    }
                }
            });

            return  convertView;
        }
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
        String studentID = MainActivity.preferences.getString("studentID", "1");
        Cursor cursor = myDB.getTasks(studentID);
        List<String> taskIDs = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> moduleIDs = new ArrayList<>();
        List<String> dueDates = new ArrayList<>();
        List<Integer> completedTasks = new ArrayList<>();

        int COL_1 = cursor.getColumnIndex ("taskID");
        int COL_2 = cursor.getColumnIndex ("taskName");
        int COL_3 = cursor.getColumnIndex ("moduleName");
        int COL_4 = cursor.getColumnIndex ("dueDate");
        int COL_5 = cursor.getColumnIndex("completed");

        while (cursor.moveToNext()) {
            String itemID = cursor.getString(COL_1);
            String itemName = cursor.getString(COL_2);
            String itemModuleID = cursor.getString(COL_3);
            String itemDueDate = cursor.getString(COL_4);
            int itemCompleted = cursor.getInt(COL_5);

            taskIDs.add(itemID);
            names.add(itemName);
            moduleIDs.add(itemModuleID);
            dueDates.add(itemDueDate);
            completedTasks.add(itemCompleted);
        }
        // String x = "";
        ListView taskListView = _baseView.findViewById(R.id.taskListView);
        taskListView.setAdapter(
            new TaskAdapter(taskIDs, names, moduleIDs, dueDates, completedTasks)
        );


    }

    public void Logout() {
        MainActivity.preferences.edit().remove("role").apply();
        PlaceholderContent.clear();

        Intent intent = new Intent(_context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        _context.startActivity(intent);
    }
}
