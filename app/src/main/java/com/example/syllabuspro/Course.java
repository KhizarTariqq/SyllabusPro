package com.example.syllabuspro;

import android.util.Log;

import java.util.ArrayList;

public class Course {
    private final String name;
    private ArrayList<SyllabusItem> syllabusItems = new ArrayList<SyllabusItem>();
    // add parameter color

    public Course (String name)
    {
        this.name = name;
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
        String string = "";
        string += "Course: " + this.name + " ";

        int syllabusItemsIndex = 0;

        for (SyllabusItem item : this.syllabusItems)
        {
            string += item.toString() + " " ;
        }

        return string;
    }

    public boolean equals(Course course)
    {
        return this.name.equals(course.getName());
    }
}
