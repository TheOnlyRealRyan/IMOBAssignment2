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
                        "name VARCHAR(255), dueDate DATE, moduleID INTEGER, completed BOOLEAN DEFAULT 0," +
                        "FOREIGN KEY (moduleID) REFERENCES Modules(moduleID) )";

        String modulesSQL = "CREATE TABLE Modules (moduleID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "name VARCHAR(255), duration INTEGER)";

        String studentsSQL = "CREATE TABLE Students (studentID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "name VARCHAR(100), surname VARCHAR(100), password VARCHAR(255), " +
                            "username VARCHAR(255), dob DATE )";

        String instructorSQL = "CREATE TABLE Instructors (instructorID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "username VARCHAR(255), password VARCHAR(255) )";

        String studentModulesSQL = "CREATE TABLE StudentModules (id INTEGER PRIMARY KEY " +
                                    "AUTOINCREMENT, moduleID INTEGER, studentID INTEGER, " +
                                    "FOREIGN KEY (moduleID) REFERENCES Modules(moduleID)," +
                                    "FOREIGN KEY (studentID) REFERENCES Modules(moduleID))";


        db.execSQL(modulesSQL);
        db.execSQL(instructorSQL);
        db.execSQL(studentsSQL);
        db.execSQL(taskSQL);
        // db.execSQL(studentModulesSQL);
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

    public Cursor getUser(String tableName, String userName, String userPass) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + tableName + " WHERE username = '" + userName +
                        "' AND password = '" + userPass + "'";

        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }
}
