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

    public Task(String name, Task.Priority priority, String description, Course course)
    {
        this.name = name;
        this.priority = priority;
        this.description = description;
        this.course = course;
    }
}
