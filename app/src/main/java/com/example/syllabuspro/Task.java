package com.example.syllabuspro;

public class Task
{
    private String name;
    private Task.Priority priority;
    private String description;
    private Course course;

    // Goal

    public enum Priority
    {
        LOW,
        MEDIUM,
        HIGH,
        VERY_HIGH,
        EXTREME
    }

    public Task(String name, Task.Priority priority, String description)
    {
        this.name = name;
        this.priority = priority;
        this.description = description;
    }

    public Course getCourse()
    {
        return this.course;
    }

    public String getName()
    {
        return this.name;
    }
}
