package com.example.syllabuspro;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.view.Window;
import android.view.WindowManager;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;

import java.io.*;
import java.net.URISyntaxException;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.example.navtest.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.navtest.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.lang.reflect.Type;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private TextView extractedTV;
    private String directory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
         getSupportActionBar().hide();

         this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
         WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // on app installation
        Intent openPDF = new Intent();
        openPDF.launch()
        SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);

        openSomeActivityForResult(findViewById(R.id.navigation_manage));

        if(!prefs.getBoolean("firstTime", false))
        {
            SharedPreferences.Editor editor = prefs.edit();

            // Courses ArrayList
            ArrayList<Course> courses = new ArrayList<Course>(0);
            Gson gson = new Gson();
            String json = gson.toJson(courses);
            editor.putString("courses", json);

            // Tasks ArrayList
            ArrayList<Task> Tasks = new ArrayList<Task>(0);
            Gson gson2 = new Gson();
            String json2 = gson2.toJson(courses);
            editor.putString("courses", json2);

            // Goals ArrayList
            ArrayList<Goal> goals = new ArrayList<Goal>(0);
            Gson gson3 = new Gson();
            String json3 = gson3.toJson(courses);
            editor.putString("courses", json3);

            // set first time boolean
            editor.putBoolean("firstTime", true);
            editor.commit();
        }


        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_calendar, R.id.navigation_goals, R.id.navigation_manage, R.id.navigation_tasks)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)

    // Request code for selecting a PDF document.
    private static final int PICK_PDF_FILE = 2;

    private void openFile(Uri pickerInitialUri)
    {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

        startActivityForResult(intent, PICK_PDF_FILE);
    }

    private void readTextFromUri(Uri uri) throws IOException
    {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream))))
             {
                String line;
                while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        this.directory = stringBuilder.toString();
    }

    public void openSomeActivityForResult(View view)
    {
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        Log.d("result", result.toString());
                        try {
                            openDirectory(data.getData());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        // Uri uri = data.getData();

                        // Log.d("testing in turn", uri.getPath());
                        // getText(uri.getPath());
                        // try {
                        //     readTextFromUri(uri);
                        // } catch (IOException e) {
                        //     e.printStackTrace();
                        // }
                    }
                }
            });

        //Create Intent

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        // intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

        // Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // intent.setType("image/jpg");
        // intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        //Launch activity to get result
        someActivityResultLauncher.launch(intent);
  }
    private void openDirectory(Uri uri) throws IOException
    {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream =
            getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(
            new InputStreamReader(Objects.requireNonNull(inputStream))))
            {
                String line;
                while ((line = reader.readLine()) != null)
                {
                    stringBuilder.append(line);
                }
            }
        Log.d("testingintent1", "directory");
        this.directory = stringBuilder.toString();
    }

    public void getText2()
    {
        File file = new File(new URI(path));
    }

    public void getText(String directory)
    {
        // Uri uri = new Uri();

        // get PDF text
        // get PDF from user TODO
        String extractedText = "";

        try
        {
            // creating a variable for pdf reader and passing our PDF file in it.
            PdfReader reader = new PdfReader(directory);

            // below line is for getting number of pages of PDF file.
            int n = reader.getNumberOfPages();

            // running a for loop to get the data from PDF we are storing that data inside our string.
            for (int i = 0; i < n; i++)
            {
                extractedText = extractedText + PdfTextExtractor.getTextFromPage(reader, i + 1).trim() + "\n";
                // to extract the PDF content from the different pages
            }

            // below line is used for closing reader.
            reader.close();
            this.directory = extractedText;
        }

        catch (Exception e)
        {
            // for handling error while extracting the text file.
            extractedTV.setText("Error found is : \n" + e);
        }
    }

    private Pair<SyllabusItem.Type, Boolean> findType(String[] words)
    {
        SyllabusItem.Type type = null;
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

    private SyllabusItem createSyllabusItem(String[] words) {
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
                    Log.d("Finding deadline", "Deadline found");

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

    public void addCourse(View view) throws IOException, URISyntaxException {
        // Get ArrayList of courses
        SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("courses", null);
        Type arrayType = new TypeToken<ArrayList<Course>>() {}.getType();

        // add course
        ArrayList<Course> courseList = gson.fromJson(json, arrayType);
        Course course = new Course("MAT232");
        courseList.add(course);

        // Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        // intent.addCategory(Intent.CATEGORY_OPENABLE);
        // intent.setType("application/pdf");
        // Log.d("testingintent", String.valueOf(intent == null));

        Log.d("testingintent", String.valueOf(this.directory == null));
        Log.d("testingintent", String.valueOf(this.directory == ""));
        // someActivityResultLauncher.launch(intent);

        // Uri uri = new Uri();
        // begin deadline process
        // set booleans
        boolean taskComplete = false;
        boolean tableFound = false;
        boolean deadlinesFound = false;

        Log.d("testingintent", this.directory);
        Scanner scanner = new Scanner(this.directory);

        // start task
        while (!taskComplete)
        {
            // find table
            while (!tableFound)
            {
                // Math department mode
                String line = scanner.nextLine();
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

        SharedPreferences.Editor editor = prefs.edit();
        Gson gson2 = new Gson();
        String json2 = gson.toJson(courseList);
        editor.putString("courses", json);
        editor.apply();
    }

    public void addTask(View view)
    {
        // Get ArrayList of tasks
        SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("tasks", null);
        Type arrayType = new TypeToken<ArrayList<Task>>() {}.getType();

        // add task
        ArrayList<Task> taskList = gson.fromJson(json, arrayType);

    }

    public void addGoal(View view)
    {
        // Get ArrayList of goals
        SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("goals", null);
        Type arrayType = new TypeToken<ArrayList<Goal>>() {}.getType();

        // add goal
        ArrayList<Goal> goalList = gson.fromJson(json, arrayType);

    }
}
