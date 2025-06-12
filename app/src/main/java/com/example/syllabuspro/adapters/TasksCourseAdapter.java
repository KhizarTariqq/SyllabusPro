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
import com.example.syllabuspro.Course;
import com.example.syllabuspro.MainActivity;
import com.example.syllabuspro.R;
import com.example.syllabuspro.SpacingItemDecoration;
import com.example.syllabuspro.Task;
import com.example.syllabuspro.ui.tasks.TasksSharedViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class TasksCourseAdapter extends RecyclerView.Adapter<TasksCourseAdapter.ViewHolder>
{
    private Fragment fragment;
    private ArrayList<Course> courseList;
    private TasksSharedViewModel viewModel;

    public TasksCourseAdapter(Fragment fragment, ArrayList<Course> courseList, TasksSharedViewModel viewModel)
    {
        this.fragment = fragment;
        this.courseList = courseList;
        this.viewModel = viewModel;

        setHasStableIds(true);
    }

    @NonNull
    @NotNull
    @Override
    public TasksCourseAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_row_course_mode, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position)
    {
        String name = "Course: " + this.courseList.get(position).getName();
        holder.name.setText(name);

        // Add adapter, layout and ItemDecoration (to separate items)
        holder.tasksRecyclerView.setAdapter(new TasksCourseItemAdapter(getTaskList(position), this::onTaskClick));
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
        return courseList.get(position).getName().hashCode();
    }

    @Override
    public int getItemCount()
    {
        return this.courseList.size();
    }

    public ArrayList<Task> getTaskList(int position)
    {
        Course course = this.courseList.get(position);
        ArrayList<Task> courseTaskList = new ArrayList<>();

        for (Task task : MainActivity.getTaskList())
        {
            if (task.getCourse().equals(course))
            {
                courseTaskList.add(task);
            }
        }

        return courseTaskList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        RecyclerView tasksRecyclerView;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.task_course_name);
            tasksRecyclerView = itemView.findViewById(R.id.task_course_recyclerView);
        }

        public RecyclerView getTasksRecyclerView()
        {
            return tasksRecyclerView;
        }
    }

    private void onTaskClick(Task task) {
        viewModel.setSelectedTask(task);
        NavHostFragment.findNavController(fragment)
                .navigate(R.id.navigation_view_task_details);
    }
}
