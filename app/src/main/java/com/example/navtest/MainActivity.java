                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    package com.example.navtest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.fragment.app.Fragment;
import com.example.navtest.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private TextView extractedTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // on app installation
        SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);

        if(!prefs.getBoolean("firstTime", false))
        {
            SharedPreferences.Editor editor = prefs.edit();

            // ArrayList
            ArrayList<String> courses = new ArrayList<String>(0);
            Gson gson = new Gson();
            String json = gson.toJson(courses);
            editor.putString("courses", json);

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

    public void addCourse(View view)
    {
        //test msg
        Toast myToast = Toast.makeText(getApplicationContext(), "Hello toast!", Toast.LENGTH_SHORT);
        //myToast.show();

        // Get ArrayList
        SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("courses", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> courses = gson.fromJson(json, type);

        // get PDF text
        String extractedText = "";
        try
        {
            Log.d("test", "test");
            // myToast.show();

            // creating a variable for pdf reader and passing our PDF file in it.
            PdfReader reader = new PdfReader("res/raw/syllabus.pdf");

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
        }

        catch (Exception e)
        {
            System.out.println("test");
            // for handling error while extracting the text file.
            extractedTV.setText("Error found is : \n" + e);
        }

        // print text
        Log.d("customtext", extractedText);

        // begin deadline process
        // set booleans
        boolean taskComplete = false;
        boolean tableFound = false;
        boolean deadlinesFound = false;

        Scanner scanner = new Scanner(extractedText);

        // start task
        while (!taskComplete || !scanner.hasNext())
        {
            // find table
            while (!tableFound)
            {
                // Math department mode
                String line = scanner.nextLine();
                String[] wordsLoop = line.split(" ");
                int findTableIndex = 0;

                // Log.d("customtext", Integer.toString(wordsLoop.length - 1));
                while (findTableIndex < wordsLoop.length - 1)
                {
                    Log.d("customtext", Arrays.toString(wordsLoop));
                    Log.d("customtext", Integer.toString(findTableIndex));
                    if (wordsLoop[findTableIndex].toLowerCase().contains("assessment") || wordsLoop[findTableIndex].toLowerCase().contains("deadlines"))
                    {
                        tableFound = true;
                    }
                    findTableIndex += 1;
                }
            }

            // collect lines and find the end of table
            while (!deadlinesFound)
            {
                String line = scanner.nextLine();
                String[] wordsLoop = line.split(" ");
                int findEndTableIndex = 0;
                boolean percentFound = false;

                // collect deadlines

                // find end of table
                while (findEndTableIndex != wordsLoop.length)
                {
                    char[] charArray = wordsLoop[findEndTableIndex].toCharArray();

                    for (char character : charArray)
                    {
                        if (character == '%')
                        {
                            percentFound = true;
                        }
                    }
                    findEndTableIndex += 1;
                }

                if (!percentFound)
                {
                    deadlinesFound = true;
                    taskComplete = true;
                }
            }

        }
    }
}