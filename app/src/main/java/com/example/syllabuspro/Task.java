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

    public Course getCourse()
    {
        return this.course;
    }

    public String getName()
    {
        return this.name;
    }

    public String getDescription()
    {
        return this.description;
    }

    public Task.Priority getPriority()
    {
        return this.priority;
    }

    public boolean equals(Task task)
    {
        return this.name.equals(task.getName()) && this.description.equals(task.getDescription()) && this.course.equals(task.getCourse()) && this.priority == task.getPriority();
    }
}
