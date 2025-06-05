package com.example.syllabuspro;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class SyllabusItem
{
    public enum Type
    {
        ASSIGNMENT,
        LAB,
        PARTICIPATION,
        PRE_CLASS_ACTIVITY,
        OTHER,
        QUIZ,
        MIDTERM,
        EXAM,
        UNKNOWN
    }

    private SyllabusItem.Type type;
    private String description;
    @SerializedName("due_date")
    private LocalDate dueDate;
    private float weight;

    public SyllabusItem(SyllabusItem.Type type, String description, float weight, LocalDate deadline)
    {
        this.type = type;
        this.description = description;
        this.weight = weight;
        this.dueDate = deadline;
    }

    public SyllabusItem()
    {
    }

    public void setType(SyllabusItem.Type type)
    {
        this.type = type;
    }

    public void setDueDate(LocalDate dueDate)
    {
        this.dueDate = dueDate;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setWeight(int weight)
    {
        this.weight = weight;
    }

    public boolean notNull()
    {
        return this.type != null && this.description != null && this.dueDate != null && this.weight != 0;
    }

    public String getDescription()
    {
        return this.description;
    }

    public String getType()
    {
        String typeString = type.toString();
        return typeString.charAt(0) + this.type.toString().substring(1).toLowerCase();
    }

    public LocalDate getDueDate()
    {
        return this.dueDate;
    }

    public float getWeight()
    {
        return this.weight;
    }

    public String toString()
    {
        if (this.notNull())
        {
            return "Name: " + description + "," + " Type: " + this.type.name() + "," + " Deadline: " + this.dueDate + "," + " Weight: " + Float.toString(weight);
        }

        else
        {
            return "Empty Item";
        }

    }
}
