package com.example.syllabuspro;

public class Goal
{
    private Deadline deadline;
    private Task task;
    private String description;

    public Goal(Deadline deadline, Task task, String description)
    {
        this.deadline = deadline;
        this.task = task;
        this.description = description;
    }
}
