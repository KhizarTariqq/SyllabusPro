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
import com.example.syllabuspro.adapters.AddCourseAdapter;
import com.example.syllabuspro.adapters.CustomAdapter;
import com.example.syllabuspro.databinding.ActivityMainBinding;
import com.example.syllabuspro.databinding.AddItemsFragmentBinding;
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
    public static ArrayList <Course> courseList = new ArrayList<Course>();
    public static ArrayList <Task> taskList = new ArrayList<Task>();
    public static ArrayList <Goal> goalList = new ArrayList<Goal>();

    public static NavController navController;
    public static FragmentManager fragmentManager;

    ArrayList<CharSequence> arrayListCollection = new ArrayList<>();
    ArrayAdapter<CharSequence> adapter;
    public static EditText txt; // user input bar

    // File selector launcher for PDF
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
    new ActivityResultCallback<Uri>()
    {
        @Override
        public void onActivityResult(Uri uri)
        {
            // Handle the returned Uri
            String fullFilePath = UriUtils.getPathFromUri(getApplicationContext(), uri);
            try
            {
                setDirectory(fullFilePath);
            }

            catch (IOException | URISyntaxException e)
            {
                e.printStackTrace();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
         getSupportActionBar().hide();

         this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
         WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);

        // On first time app installation create the directories to store courses, tasks, and goals
        if(!prefs.getBoolean("firstTime", false))
        {
            Log.d("first time", "1");
            SharedPreferences.Editor editor = prefs.edit();

            // Courses ArrayList
            ArrayList<Course> courses = new ArrayList<Course>(0);
            saveArrayList(courses, "courses");

            // Tasks ArrayList
            ArrayList<Task> tasks = new ArrayList<Task>(0);
            saveArrayList(tasks, "tasks");

            // Goals ArrayList
            ArrayList<Goal> goals = new ArrayList<Goal>(0);
            saveArrayList(goals, "goals");

            // set first time boolean
            editor.putBoolean("firstTime", true);
            editor.apply();
        }

        // if it's not the first time installing the app, get the list of courses, task, and goals.
        else
        {
            Log.d("first time", "2");

            // Get ArrayList of courses
            courseList = (ArrayList<Course>) getArrayList("courses");
            Log.d("first: courses", courseList.toString());

            taskList = (ArrayList<Task>) getArrayList("tasks");
            Log.d("first: tasks", taskList.toString());

            goalList = (ArrayList<Goal>) getArrayList("goals");
            Log.d("first: goals", goalList.toString());
        }

        fragmentManager = getSupportFragmentManager();

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Make bottom navigation bar titles static
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);
        // Passing each menu ID as a set of Ids becourses each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_calendar, R.id.navigation_goals, R.id.navigation_manage, R.id.navigation_tasks)
                .build();
        this.navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    public void saveArrayList(ArrayList<?> list, String key)
    {
        SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public ArrayList<?> getArrayList(String key)
    {
        SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<Course>>() {}.getType();
        return gson.fromJson(json, type);
    }

    private void setDirectory(String directory) throws IOException, URISyntaxException
    {
        /**
         * This method runs once a file is selected.
         * This sets the directory of the chosen file
         * and launches addCourse.
         */
        this.directory = directory;
        addCourse();
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

    public void collectInput(View view, Course course) throws IOException, URISyntaxException {
        // convert edit text to string
        String getInput = txt.getText().toString();

        // ensure that user input bar is not empty
        if (getInput ==null || getInput.trim().equals(""))
        {
            Toast.makeText(getBaseContext(), "Please add a group name", Toast.LENGTH_LONG).show();
        }    // add input into an data collection arraylist

        else
        {
            arrayListCollection.add(getInput);

            RecyclerView recyclerView = view.getRootView().findViewById(R.id.recyclerView);
            CustomAdapter adapter = (CustomAdapter) recyclerView.getAdapter();
            ArrayList<Course> courseList = adapter.getCourseList();
            courseList.add(course);
            adapter.notifyDataSetChanged();

            // hide keyboard and start new fragment
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            Navigation.findNavController(view).navigate(R.id.navigation_add_items);

            // launchPDFSelector();
        }
    }

    public void launchTextInput(View view) throws IOException, URISyntaxException
    {
        // set recycler view
        ArrayList<SyllabusItem> syllabusItems = new ArrayList<SyllabusItem>();

        // Get course name
        AlertDialog.Builder alertName = new AlertDialog.Builder(this);
        final EditText editTextName1 = new EditText(MainActivity.this);
        alertName.setTitle("Enter the course name: ");
        // titles can be used regardless of a custom layout or not
        alertName.setView(editTextName1);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(editTextName1); // displays the user input bar

        alertName.setView(layout);


        // final EditText name = (EditText) view.findViewById(R.id.name);

        // if (dialogView.requestFocus()) {
        //     InputMethodManager imm = (InputMethodManager)
        //             getSystemService(Context.INPUT_METHOD_SERVICE);
        //     imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        // }


        alertName.setPositiveButton("Continue", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                layout.removeView(editTextName1);
                txt = editTextName1; // variable to collect user input

                Course course = new Course(txt.getText().toString(), syllabusItems);

                try
                {
                    collectInput(view, course); // analyze input (txt) in this method
                }

                catch (IOException | URISyntaxException e)
                {
                    e.printStackTrace();
                }
            }
        });

        alertName.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                dialog.cancel(); // closes dialog alertName.show() // display the dialog

            }
        });




        AlertDialog dialog = alertName.create();

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();
    }

    public void launchPDFSelector() throws IOException, URISyntaxException
    {
        /**
         * This is a click listener for the add course button,
         * this gets storage permission and then launches file selector
         */
        // Get storage permission

        Log.d("empty", String.valueOf(Build.VERSION.SDK_INT));
        Log.d("empty", "1");
        mGetContent.launch("application/pdf");
    }

    public ArrayList<Course> getCourseList()
    {
        return this.courseList;
    }

    public void addCourse() throws IOException, URISyntaxException
    {
        /**
         * After a PDF file is selected, this method performs the actions
         * of getting the course list and adds the course to the list.
         * Then scans the content of the file and adds the according
         * syllabus items to the course.
         */

        Course course = new Course(txt.getText().toString());
        courseList.add(course);

        // begin deadline process
        // set booleans
        boolean taskComplete = false;
        boolean tableFound = false;
        boolean deadlinesFound = false;

        String extractedText = getText();
        // Log.d("testingintent", extractedText);

        Scanner scanner = new Scanner(extractedText);

        // start task
        while (!taskComplete)
        {
            // find table
            while (!tableFound)
            {
                // Math department mode
                String line = scanner.nextLine();
                Log.d("new", line);
                String[] wordsLoop = line.split(" ");
                int findTableIndex = 0;

                while (findTableIndex < wordsLoop.length - 1)
                {
                    if (wordsLoop[findTableIndex].toLowerCase().contains("assessment") || wordsLoop[findTableIndex].toLowerCase().contains("deadlines"))
                    {
                        tableFound = true;
                    }
                    findTableIndex += 1;
                }
            }

            scanner.nextLine();

            // collect lines until the end of table
            while (!deadlinesFound)
            {
                String line = scanner.nextLine();
                String[] wordsLoop = line.split(" ");
                int findEndTableIndex = 0;
                boolean percentFound = false;
                boolean totalFound = false;

                // Check if end of table is reached
                while (findEndTableIndex != wordsLoop.length)
                {
                    if (wordsLoop[findEndTableIndex].trim().equals("Total"))
                    {
                        totalFound = true;
                        break;
                    }

                    else
                    {
                        char[] charArray = wordsLoop[findEndTableIndex].toCharArray();

                        for (char character : charArray)
                        {
                            if (character == '%')
                            {
                                percentFound = true;
                            }
                        }
                    }
                    findEndTableIndex += 1;
                }

                // to end of table of reached
                if (!percentFound || totalFound)
                {
                    deadlinesFound = true;
                    taskComplete = true;
                }

                // if valid collect syllabus items
                else
                {
                    SyllabusItem item = createSyllabusItem(wordsLoop);
                    course.addSyllabusItem(item);
                }
            }
        }

        // print courses
        Log.d("testingtext", course.getSyllabusItems().get(0).toString());
        for (SyllabusItem item : course.getSyllabusItems())
        {
            Log.d("testingtext", item.toString());
        }

        saveArrayList(courseList, "courses");

        Button button = binding.getRoot().findViewById(R.id.open_items_button);
        button.setTag(txt.getText().toString());

        // Update RecyclerView
        this.recyclerView = binding.getRoot().findViewById(R.id.recyclerView);
        CustomAdapter adapter = (CustomAdapter) recyclerView.getAdapter();
        adapter.notifyDataSetChanged();
    }

    public void addTask(View view)
    {
        // add task
        Task task = new Task("testTask", Task.Priority.MEDIUM, "testTask");
        taskList.add(task);

    }

    public void addGoal(View view)
    {
        // add goal
        Task task = new Task("testTask", Task.Priority.MEDIUM, "testTask");
        Goal goal = new Goal(new Deadline(0, 0, 0), task, "test goal");
        goalList.add(goal);
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

        RecyclerView recyclerView = this.binding.getRoot().findViewById(R.id.addCourseRecyclerView);
        AddCourseAdapter adapter = (AddCourseAdapter) recyclerView.getAdapter();
        ArrayList<SyllabusItem> syllabusItems = adapter.getSyllabusItems();
        SyllabusItem item = syllabusItems.get(syllabusItems.size() - 1);

        item.setDeadline(new Deadline(year, month, day));
    }
}
