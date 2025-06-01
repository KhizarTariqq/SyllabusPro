package com.example.syllabuspro;

import android.util.Log;

import java.util.ArrayList;

public class Course {
    private final String name;
    private final ArrayList<SyllabusItem> syllabusItems;

    // TODO add colour field

    public Course (String name)
    {
        this.name = name;
        syllabusItems = new ArrayList<>();
    }

    public Course (String name, ArrayList<SyllabusItem> syllabusItems)
    {
        this.name = name;
        this.syllabusItems = syllabusItems;
    }

    public void addSyllabusItem(SyllabusItem item)
    {
        this.syllabusItems.add(item);
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
