package com.example.syllabuspro;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.widget.DatePicker;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import java.io.*;
import java.net.URISyntaxException;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.syllabuspro.adapters.*;
import com.example.syllabuspro.databinding.ActivityMainBinding;
import com.example.syllabuspro.ui.tasks.TaskPriorityType;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.*;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{
    public static ActivityMainBinding binding;
    private String directory;
    private RecyclerView recyclerView;
    private static ArrayList <Course> courseList;
    private static ArrayList <Task> taskList;

    // List first syllabus items for when adding course
    private static ArrayList<SyllabusItem> syllabusItems = new ArrayList<SyllabusItem>();

    // fragment controllers
    public static NavController navController;
    public static FragmentManager fragmentManager;

    public static EditText txt; // user input bar

    // variable for accessing user storage
    public static SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();

         this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
         WindowManager.LayoutParams.FLAG_FULLSCREEN);
         prefs = this.getPreferences(Context.MODE_PRIVATE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        prefs = this.getPreferences(Context.MODE_PRIVATE);

        // On first time app installation create the directories to store courses, and tasks
        if(!prefs.getBoolean("firstTime", false))
        {
            Log.d("first time", "1");
            SharedPreferences.Editor editor = prefs.edit();

            // Courses ArrayList
            courseList = new ArrayList<Course>(0);
            saveCourseList();

            // Tasks ArrayList
            taskList = new ArrayList<Task>(0);
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

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Make bottom navigation bar titles static
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

        // Set up bottom navigation bar
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_summary, R.id.navigation_calendar, R.id.navigation_courses, R.id.navigation_tasks)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
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

    public String getText()
    {
        /**
         * This method sets the extractedText string to the contents of the selected PDF.
         */

        // get PDF text
        String extractedText = "";

        try
        {
            // creating a variable for pdf reader and passing our PDF file in it.
            File file = new File(this.directory);
            PdfReader reader = new PdfReader(new FileInputStream(file.getPath()));

            // below line is for getting number of pages of PDF file.
            int n = reader.getNumberOfPages();

            // running a for loop to get the data from PDF we are storing that data inside our string.
            for (int i = 0; i < n; i++)
            {
                extractedText = extractedText + PdfTextExtractor.getTextFromPage(reader, i + 1).trim() + "\n";
            }

            // below line is used for closing reader.
            reader.close();
            return extractedText;
        }

        catch (Exception e)
        {
            // for handling error while extracting the text file.
            // extractedTV.setText("Error found is : \n" + e);

            Log.d("manage","Error found is : \n" + e);
        }

        return extractedText;
    }

    private Pair<SyllabusItem.Type, Boolean> findType(String[] words)
    {
        /**
         * This method finds the SyllabusCores.Type of a syllabus item
         * @param words A line of words representing a syllabus item.
         * @return A pair that contains the syllabus item type and a
         * boolean that represents if the type is one or two words.
         */

        if (words[0].equals("Quiz"))
        {
            return new Pair<>(SyllabusItem.Type.Quiz, false);
        }

        else if (words[0].equals("Assignment"))
        {
            return new Pair<>(SyllabusItem.Type.Assignment, false);
        }

        else if (words[0].equals("Term") && words[1].equals("Test"))
        {
            return new Pair<>(SyllabusItem.Type.TermTest, true);
        }

        else if (words[0].equals("Class") && words[1].equals("Participation"))
        {
            return new Pair<>(SyllabusItem.Type.ClassParticipation, true);
        }

        else if (words[0].equals("Final") && words[1].equals("Exam"))
        {
            return new Pair<>(SyllabusItem.Type.FinalExam, true);
        }

        return null;
    }

    private SyllabusItem createSyllabusItem(String[] words)
    {
        /**
         * This method returns a SyllabusItem object given a line representing a syllabus item.
         * @param words A line of words representing a syllabus item.
         * @return The SyllabusCores object represented by the parameter.
         */

        StringBuilder nameBuilder = new StringBuilder();
        boolean deadlineFound = false;
        boolean weightFound = false;
        int getNameWordIndex = 0;
        Deadline deadline = null;

        // Get type
        SyllabusItem.Type type = findType(words).first;
        boolean twoWords = findType(words).second;

        // change word index
        if (twoWords)
        {
            getNameWordIndex += 2;
        }

        else
        {
            getNameWordIndex += 1;
        }

        while (getNameWordIndex < words.length)
        {
            // detect tba/ongoing
            int wordLength = words[getNameWordIndex].length();
            int getNameCharIndex = 0;

            if (words[getNameWordIndex].equals("TBA"))
            {
                deadline = new Deadline(Deadline.Alternative.TBA);
                deadlineFound = true;
            }

            if (words[getNameWordIndex].equals("On-going"))
            {
                deadline = new Deadline(Deadline.Alternative.Ongoing);
                deadlineFound = true;
            }

            // Get name and deadline
            while (getNameCharIndex < wordLength)
            {
                char[] charArray = words[getNameWordIndex].toCharArray();

                // detect XXXX-XX-XX or tba/ongoing
                // detect XXXX-XX-XX
                if (charArray.length >= 10 &&
                    Character.isDigit(charArray[0]) && Character.isDigit(charArray[1]) &&
                    Character.isDigit(charArray[2]) && Character.isDigit(charArray[3]) &&
                    charArray[4] == '-' &&
                    Character.isDigit(charArray[5]) && Character.isDigit(charArray[6]) &&
                    charArray[7] == '-' &&
                    Character.isDigit(charArray[8]) && Character.isDigit(charArray[9])
                    )
                {
                    deadlineFound = true;

                    // create deadline
                    deadline = new Deadline(words[getNameWordIndex]);

                    break;
                }
                getNameCharIndex += 1;
            }

            if (!deadlineFound)
            {
                nameBuilder.append(" " + words[getNameWordIndex]);
            }

            getNameWordIndex += 1;
        }

        // Get weight
        getNameWordIndex -= 1;
        char[] charArray = words[getNameWordIndex].toCharArray();
        int weight = 0;

        if (Character.isDigit(charArray[0]) && Character.isDigit(charArray[1]))
        {
            weight = Integer.parseInt(words[getNameWordIndex].substring(0, 2));
        }

        else
        {
            weight = Integer.parseInt(words[getNameWordIndex].substring(0, 1));
        }

        String name = nameBuilder.toString();
        String trimName = name.substring(1, name.length());

        if (deadline == null)
        {
            SyllabusItem item = new SyllabusItem(type, trimName, weight);
            return item;
        }

        else
        {
            SyllabusItem item = new SyllabusItem(type, trimName, weight, deadline);
            return item;
        }
    }

    public static ArrayList<String> courseListToStringArray()
    {
        ArrayList<String> courseListString = new ArrayList<String>();
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

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day)
    {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, day);
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
        Log.d("Date", selectedDate);

        RecyclerView recyclerView = binding.getRoot().findViewById(R.id.addCourseRecyclerView);
        AddCourseAdapter adapter = (AddCourseAdapter) recyclerView.getAdapter();
        ArrayList<SyllabusItem> syllabusItems = adapter.getSyllabusItems();
        SyllabusItem item = syllabusItems.get(syllabusItems.size() - 1);

        item.setDeadline(new Deadline(year, month, day));
    }
}
