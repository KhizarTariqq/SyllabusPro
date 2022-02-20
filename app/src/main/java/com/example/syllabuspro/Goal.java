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
}
