package com.example.imob301assignment.placeholder;

import com.example.imob301assignment.AddInstructor;
import com.example.imob301assignment.AddStudent;
import com.example.imob301assignment.AddTask;
import com.example.imob301assignment.CreateModule;
import com.example.imob301assignment.MainActivity;
import com.example.imob301assignment.R;
import com.example.imob301assignment.ViewTasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaceholderContent {

    public static final List<PlaceholderItem> ITEMS = new ArrayList<PlaceholderItem>();
    public static final Map<String, PlaceholderItem> ITEM_MAP = new HashMap<String, PlaceholderItem>();



    static {
        String role = MainActivity.preferences.getString("role", "student");

        if (role.equals("admin")) {
            addItem(new PlaceholderItem("1", "Create Module", R.layout.activity_create_module));
            addItem(new PlaceholderItem("2", "Add Instructor", R.layout.activity_add_instructor));
            addItem(new PlaceholderItem("3", "Add Student", R.layout.activity_add_student));
        } else if (role.equals("instructor")) {
            addItem(new PlaceholderItem("1", "Add Task", R.layout.activity_add_task));
        } else {
            addItem(new PlaceholderItem("1", "View Tasks", R.layout.activity_view_tasks));
        }
    }

    private static void addItem(PlaceholderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class PlaceholderItem {
        public final String id;
        public final String actionName;

        public final int actionLayout;

        public PlaceholderItem(String id, String actionName, int actionLayout) {
            this.id = id;
            this.actionName = actionName;
            this.actionLayout = actionLayout;
        }

    }
}