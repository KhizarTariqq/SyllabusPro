package com.example.syllabuspro;

import android.util.Log;

import java.util.ArrayList;

public class Course {
    private final String name;

    // TODO reevaluate if syllabusItems should be an Array or ArrayList
    private ArrayList<SyllabusItem> syllabusItems;

    // TODO add colour field

    public Course (String name)
    {
        this.name = name;
    }

    public Course (String name, ArrayList<SyllabusItem> syllabusItems)
    {
        this.name = name;
        this.syllabusItems = syllabusItems;
    }

    public void setSyllabusItems(ArrayList<SyllabusItem> syllabusItems)
    {
        this.syllabusItems = syllabusItems;
    }

    public ArrayList<SyllabusItem> getSyllabusItems()
    {
        return this.syllabusItems;
    }

    public String getName()
    {
        return this.name;
    }

    public String toString()
    {
        StringBuilder string = new StringBuilder();
        string.append(String.format("Course: %s - Items: ", name));


        for (SyllabusItem item : this.syllabusItems)
        {
            string.append(item.toString()).append(" ");
        }

        return string.toString();
    }

    public boolean equals(Course course)
    {
        return this.name.equals(course.getName());
    }
}
