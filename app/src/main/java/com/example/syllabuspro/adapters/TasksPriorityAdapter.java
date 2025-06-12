package com.example.syllabuspro.adapters;

import android.os.Parcelable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.syllabuspro.R;
import com.example.syllabuspro.SpacingItemDecoration;
import com.example.syllabuspro.Task;
import com.example.syllabuspro.ui.tasks.TaskPriorityType;
import com.example.syllabuspro.ui.tasks.TasksSharedViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TasksPriorityAdapter extends RecyclerView.Adapter<TasksPriorityAdapter.ViewHolder>
{
    private Fragment fragment;
    private ArrayList<TaskPriorityType> taskPriorityList;
    private TasksSharedViewModel viewModel;


    public TasksPriorityAdapter(Fragment fragment, ArrayList<TaskPriorityType> taskPriorityList, TasksSharedViewModel viewModel)
    {
        this.fragment = fragment;
        this.taskPriorityList = taskPriorityList;
        this.viewModel = viewModel;
        setHasStableIds(true);
    }

    @NonNull
    @NotNull
    @Override
    public TasksPriorityAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_row_priority_mode, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position)
    {
        String priority = "Priority: " + this.taskPriorityList.get(position).getPriority().toString();
        holder.priorityType.setText(priority);

        // Add adapter, layout and ItemDecoration (to separate items)
        holder.tasksRecyclerView.setAdapter(new TasksPriorityItemAdapter(this.taskPriorityList.get(position).getTaskList(), this::onTaskClick));
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.tasksRecyclerView.getContext(), RecyclerView.HORIZONTAL, false);
        holder.tasksRecyclerView.setLayoutManager(layoutManager);
        int spacingInPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 12, holder.tasksRecyclerView.getContext().getResources().getDisplayMetrics());

        holder.tasksRecyclerView.addItemDecoration(new SpacingItemDecoration(spacingInPx, LinearLayoutManager.HORIZONTAL));

        // Restore scroll state in the recyclerview
        long courseId = getItemId(position);
        Parcelable state = viewModel.getScrollState(courseId);

        if (state != null) {
            layoutManager.onRestoreInstanceState(state);
        }
    }

    @Override
    public long getItemId(int position) {
        return taskPriorityList.get(position).getPriority().hashCode();
    }

    @Override
    public int getItemCount()
    {
        return this.taskPriorityList.size();
    }

    public void setTaskPriorityList(ArrayList<TaskPriorityType> taskPriorityList)
    {
        this.taskPriorityList = taskPriorityList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView priorityType;
        RecyclerView tasksRecyclerView;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            priorityType = itemView.findViewById(R.id.task_priority);
            tasksRecyclerView = itemView.findViewById(R.id.task_priority_recyclerView);
        }

        public RecyclerView getTasksRecyclerView() {
            return tasksRecyclerView;
        }
    }

    private void onTaskClick(Task task) {
        viewModel.setSelectedTask(task);
        NavHostFragment.findNavController(fragment)
                .navigate(R.id.navigation_view_task_details);
    }
}
