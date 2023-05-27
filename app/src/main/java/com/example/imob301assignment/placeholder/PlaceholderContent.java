package com.example.imob301assignment.placeholder;

import android.content.Context;

import com.example.imob301assignment.ActivityFunctionality;
import com.example.imob301assignment.AddInstructor;
import com.example.imob301assignment.AddStudent;
import com.example.imob301assignment.AddTask;
import com.example.imob301assignment.CreateModule;
import com.example.imob301assignment.MainActivity;
import com.example.imob301assignment.R;
import com.example.imob301assignment.ViewTasks;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class PlaceholderContent {

    public static final List<PlaceholderItem> ITEMS = new ArrayList<PlaceholderItem>();
    public static final Map<String, PlaceholderItem> ITEM_MAP = new HashMap<String, PlaceholderItem>();


    public static void addItem(PlaceholderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static void clear() {
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    public static class PlaceholderItem {
        public final String id;
        public final String actionName;

        public final int actionLayout;

        public PlaceholderItem(
            String id,
            String actionName,
            int actionLayout
        ) {
            this.id = id;
            this.actionName = actionName;
            this.actionLayout = actionLayout;
        }
    }
}