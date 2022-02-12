package com.example.syllabuspro.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.syllabuspro.R;
import com.example.syllabuspro.SyllabusItem;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AddCourseAdapter extends RecyclerView.Adapter<AddCourseAdapter.ViewHolder>
{
    private ArrayList<SyllabusItem> syllabusItems;

    public AddCourseAdapter(ArrayList<SyllabusItem> syllabusItems)
    {
        this.syllabusItems = syllabusItems;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.add_items_dialog_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position)
    {
        holder.name.setText("Name:");
        Spinner spinner = (Spinner) holder.itemView.findViewById(R.id.types_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(holder.itemView.getContext(),
                R.array.types_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    @Override
    public int getItemCount()
    {
        return this.syllabusItems.size();
    }

    public ArrayList<SyllabusItem> getSyllabusItems()
    {
        return this.syllabusItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.enter_item_name);
            // age = itemView.findViewById(R.id.item_age);


        }
    }
}
