package com.example.syllabuspro;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
// import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.navtest.R;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>
{
    private ArrayList<Course> courseList = new ArrayList<Course>();
    private Course selectedCourse;

    public CustomAdapter(ArrayList<Course> courseList)
    {
        this.courseList = courseList;
        Log.d("Adapter", courseList.toString());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.course_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position)
    {
        holder.name.setText(this.courseList.get(position).getName());
        // holder.age.setText(this.courseList.get(position).getName());
    }

    @Override
    public int getItemCount()
    {
        return this.courseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name, age;
        public ViewHolder(@NonNull View itemView)
        {
             super(itemView);
             name = itemView.findViewById(R.id.item_name);
             // age = itemView.findViewById(R.id.item_age);
        }
    }

    public void setSelectedCourse(Course selectedCourse)
    {
        Log.d("view items 2", selectedCourse.toString());
        this.selectedCourse = selectedCourse;
    }

    public Course getSelectedCourse()
    {
        return this.selectedCourse;
    }
}