package com.example.syllabuspro;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;
import com.example.syllabuspro.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

public class MainActivity extends AppCompatActivity
{
    public static ActivityMainBinding binding;
    private static ArrayList <Course> courseList;
    private static ArrayList <Task> taskList;

    // List for syllabus items for when adding course
    private static ArrayList<SyllabusItem> syllabusItems = new ArrayList<>();

    public static FragmentManager fragmentManager;

    // variable for accessing user storage
    public static SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        prefs = this.getPreferences(Context.MODE_PRIVATE);

        // On first time app installation create the directories to store courses, and tasks
        if(!prefs.getBoolean("firstTime", false))
        {
            Log.d("first time", "1");
            SharedPreferences.Editor editor = prefs.edit();

            // Courses ArrayList
            courseList = new ArrayList<>(0);
            saveCourseList();

            // Tasks ArrayList
            taskList = new ArrayList<>(0);
            saveTaskList();

            // set first time boolean
            editor.putBoolean("firstTime", true);
            editor.apply();
        }

        // if it's not the first time installing the app, get the list of courses, and tasks.
        else
        {
            Log.d("first time", "2");

            // Get ArrayList of courses, and tasks
            SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
            Gson gson = new Gson();

            String courseJson = prefs.getString("courses", null);
            Type typeCourse = new TypeToken<ArrayList<Course>>() {}.getType();
            courseList = gson.fromJson(courseJson, typeCourse);
            Log.d("first: courses", courseList.toString());

            String tasksJson = prefs.getString("tasks", null);
            Type typeTask = new TypeToken<ArrayList<Task>>() {}.getType();
            taskList = gson.fromJson(tasksJson, typeTask);
            Log.d("first: tasks", taskList.toString());
        }

        fragmentManager = getSupportFragmentManager();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setup ActionBar
        Toolbar toolbar = binding.mainToolbar;
        setSupportActionBar(toolbar);

        // Make bottom navigation bar titles static
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

        // Set up bottom navigation bar
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_summary, R.id.navigation_calendar, R.id.navigation_courses, R.id.navigation_tasks)
                .build();
        NavController navController = ((NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_activity_main))
                .getNavController();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    public static ArrayList<Course> getCourseList()
    {
        return courseList;
    }

    public static void addToCourseList(Course course) {
        courseList.add(course);
    }

    public static void saveCourseList(){
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(courseList);
        editor.putString("courses", json);
        editor.apply();
    }

    public static ArrayList<Task> getTaskList()
    {
        return taskList;
    }

    public static void addToTaskList(Task task)
    {
        taskList.add(task);
    }

    public static void saveTaskList(){
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(taskList);
        editor.putString("tasks", json);
        editor.apply();
    }

    public static ArrayList<SyllabusItem> getSyllabusItems()
    {
        return syllabusItems;
    }

    public static void setSyllabusItems(ArrayList<SyllabusItem> syllabusItemsNew){
        syllabusItems = syllabusItemsNew;
    }

    public static ArrayList<String> courseListToStringArray()
    {
        ArrayList<String> courseListString = new ArrayList<>();
        for (Course course : courseList)
        {
            courseListString.add(course.getName());
        }

        return courseListString;
    }

    public static Course getCourseFromString(String courseName)
    {
        Course chosenCourse = null;
        for (Course course : courseList)
        {
            if (course.getName().equals(courseName))
            {
                chosenCourse = course;
            }
        }

        return chosenCourse;
    }
}
