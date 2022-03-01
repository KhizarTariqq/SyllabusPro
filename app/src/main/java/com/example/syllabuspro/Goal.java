package com.example.syllabuspro;

public class Goal
{
    private String description;
    private Course course;
    private Deadline deadline;
    private Task task;

    public Goal(String description, Task task, Deadline deadline)
    {
        this.description = description;
        this.task = task;
        this.course = task.getCourse();
        this.deadline = deadline;
    }

    public String getDescription()
    {
        return this.description;
    }

    public Course getCourse()
    {
        return this.course;
    }

    public Deadline getDeadline()
    {
        return this.deadline;
    }

    public Task getTask()
    {
        return this.task;
    }

    public String toString()
    {
        return "Description: " + this.description + " Course: " + this.course.toString() + " Deadline: " + this.deadline.toString() + " Task: " + this.task.toString();
    }
}
