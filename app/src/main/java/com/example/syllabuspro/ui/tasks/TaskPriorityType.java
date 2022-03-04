package com.example.syllabuspro.ui.tasks;

import com.example.syllabuspro.MainActivity;
import com.example.syllabuspro.Task;
import java.util.ArrayList;

public class TaskPriorityType
{
    private Task.Priority priority;
    private ArrayList<Task> taskList;

    public TaskPriorityType(Task.Priority priority)
    {
        this.priority = priority;
        setTaskList();
    }

    public void setTaskList()
    {
        ArrayList<Task> priorityTaskList = new ArrayList<Task>();
        for (Task task : MainActivity.taskList)
        {
            if (task.getPriority() == this.priority)
            {
                priorityTaskList.add(task);
            }
        }

        this.taskList = priorityTaskList;
    }

    public Task.Priority getPriority()
    {
        return this.priority;
    }

    public ArrayList<Task> getTaskList()
    {
        return this.taskList;
    }
}
