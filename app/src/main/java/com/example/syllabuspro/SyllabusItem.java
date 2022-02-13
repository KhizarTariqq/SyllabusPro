package com.example.syllabuspro;

import android.util.Log;

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
    private Deadline deadline;
    private int weight;
    private boolean noDeadline;

    public SyllabusItem(SyllabusItem.Type type, String name, int weight, Deadline deadline)
    {
        this.type = type;
        this.name = name;
        this.weight = weight;
        this.deadline = deadline;
    }

    public SyllabusItem(SyllabusItem.Type type, String name, int weight)
    {
        this.type = type;
        this.name = name;
        this.weight = weight;
        this.noDeadline = true;
        this.deadline = new Deadline(2022,1,20);
    }

    public SyllabusItem()
    {

    }
    public void setProperties(SyllabusItem.Type type, String name, Deadline deadline, int weight)
    {
        this.type = type;
        this.name = name;
        this.deadline = deadline;
        this.weight = weight;
    }

    public Type getType()
    {
        return this.type;
    }

    public Deadline getDeadline()
    {
        return this.deadline;
    }

    public int getWeight()
    {
        return this.weight;
    }

    public String toString()
    {
        return "Name: " + name + "," + " Type: " + this.type.name() + "," + " Deadline: " + this.deadline + "," + " Weight: " + Integer.toString(weight);
    }
}
