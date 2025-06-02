package com.example.syllabuspro;

import android.util.Log;

import java.time.LocalDate;

public class SyllabusItem
{
    public enum Type
    {
        TermTest,
        Quiz,
        Assignment,
        ClassParticipation,
        FinalExam
    }

    private SyllabusItem.Type type;
    private String name;
    private LocalDate deadline;
    private int weight;

    public SyllabusItem(SyllabusItem.Type type, String name, int weight, LocalDate deadline)
    {
        this.type = type;
        this.name = name;
        this.weight = weight;
        this.deadline = deadline;
    }

    public void setType(SyllabusItem.Type type)
    {
        this.type = type;
    }

    public void setDeadline(LocalDate deadline)
    {
        this.deadline = deadline;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setWeight(int weight)
    {
        this.weight = weight;
    }

    public boolean notNull()
    {
        if (this.type == null || this.name == null || this.deadline == null || this.weight == 0)
        {
            Log.d("Item: type", String.valueOf(this.type == null));
            Log.d("Item: name", String.valueOf(this.name == null));
            Log.d("Item: Deadline", String.valueOf(this.deadline == null));
            Log.d("Item: weight", String.valueOf(this.weight == 0));
            return false;
        }

        else
        {
            return true;
        }
    }

    public String getName()
    {
        return this.name;
    }

    public Type getType()
    {
        return this.type;
    }

    public LocalDate getDeadline()
    {
        return this.deadline;
    }

    public int getWeight()
    {
        return this.weight;
    }

    public String toString()
    {
        if (this.notNull())
        {
            return "Name: " + name + "," + " Type: " + this.type.name() + "," + " Deadline: " + this.deadline + "," + " Weight: " + Integer.toString(weight);
        }

        else
        {
            return "Empty Item";
        }

    }
}
