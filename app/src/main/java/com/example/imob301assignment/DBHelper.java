package com.example.imob301assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "TaskManager.db";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String taskSQL = "CREATE TABLE Tasks (taskID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name VARCHAR(255), dueDate DATE, moduleID INTEGER, " +
                        "FOREIGN KEY (moduleID) REFERENCES Modules(moduleID) )";

        String modulesSQL = "CREATE TABLE Modules (moduleID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "name VARCHAR(255), duration INTEGER)";

        String studentsSQL = "CREATE TABLE Students (studentID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "name VARCHAR(100), surname VARCHAR(100), password VARCHAR(255), " +
                            "username VARCHAR(255), dob DATE )";

        String instructorSQL = "CREATE TABLE Instructors (instructorID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "username VARCHAR(255), password VARCHAR(255) )";

        String studentTaskSQL = "CREATE TABLE StudentTasks (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "studentID INTEGER, taskID INTEGER, completed BOOLEAN DEFAULT 0, " +
                                "FOREIGN KEY (taskID) REFERENCES Tasks(taskID), " +
                                "FOREIGN KEY (studentID) REFERENCES Students(studentID) )";

        db.execSQL(modulesSQL);
        db.execSQL(instructorSQL);
        db.execSQL(studentsSQL);
        db.execSQL(taskSQL);
        db.execSQL(studentTaskSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Tasks");
        db.execSQL("DROP TABLE IF EXISTS Students");
        db.execSQL("DROP TABLE IF EXISTS Modules");
        db.execSQL("DROP TABLE IF EXISTS Instructors");

        onCreate(db);
    }

    public boolean insertData(String tableName, ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.insert(tableName, null, values);
        return result != -1;
    }

    public Cursor getAllItems(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        return cursor;
    }

    public Cursor getTasks(String studentID) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT Tasks.taskID AS taskID, Tasks.name AS taskName, Modules.name AS moduleName, dueDate, " +
                        " completed FROM Tasks " +
                        "LEFT JOIN StudentTasks ON StudentTasks.taskID = Tasks.taskID AND StudentTasks.studentID = " + studentID +
                        " LEFT JOIN Modules ON Modules.moduleID = Tasks.moduleID " ;

        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public Cursor getUser(String tableName, String userName, String userPass) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + tableName + " WHERE username = '" + userName +
                        "' AND password = '" + userPass + "'";

        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public boolean updateCompletion(String id, String studentID, boolean newVal) {
        SQLiteDatabase db = this.getWritableDatabase();

        String val = "FALSE";
        if (newVal) {
            val = "TRUE";
        }

        String findItems = "SELECT * FROM StudentTasks WHERE taskID = " + id + " AND studentID = " +
                            studentID + "";

        Cursor cursor = db.rawQuery(findItems, null);

        try {
            if (cursor.getCount() == 0) {
                String query = "INSERT INTO StudentTasks (studentID, taskID, completed) " +
                                "VALUES (" +  studentID + ", " + id + "," + val + ")";
                db.execSQL(query);
            } else {
                String query = "UPDATE StudentTasks SET completed = " + val +
                                " WHERE taskID = " + id + " AND studentID = " + studentID + "";
                db.execSQL(query);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
