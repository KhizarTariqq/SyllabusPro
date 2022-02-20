package com.example.syllabuspro;

public class Task
{
    private String name;
    private Task.Priority priority;
    private String description;
    private Course course;

    public enum Priority
    {
        LOW,
        MEDIUM,
        HIGH,
        VERY_HIGH,
        EXTREME
    }

    public Task(String name, String description, Task.Priority priority, Course course)
    {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.course = course;
    }

    public String getName()
    {
        return this.name;
    }
}
