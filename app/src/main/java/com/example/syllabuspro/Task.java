package com.example.syllabuspro;

import androidx.annotation.NonNull;

public class Task
{
    private Task.Priority priority;
    private String description;
    private Course course;

    public enum Priority
    {
        LOW,
        MEDIUM,
        HIGH;

        @NonNull
        @Override
        public String toString()
        {
            String priorityString = super.toString();
            return priorityString.charAt(0) + priorityString.substring(1).toLowerCase();
        }
    }

    public Task(String description, Task.Priority priority, Course course)
    {
        this.description = description;
        this.priority = priority;
        this.course = course;
    }

    public Course getCourse()
    {
        return this.course;
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
        return this.description.equals(task.getDescription()) && this.course.equals(task.getCourse()) && this.priority == task.getPriority();
    }
}
