package com.example.syllabuspro.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.navtest.R;
import com.example.syllabuspro.SyllabusItem;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>
{
    private ArrayList<SyllabusItem> syllabusItems;

    public ItemsAdapter(ArrayList<SyllabusItem> syllabusItems)
    {
        this.syllabusItems = syllabusItems;
        for (SyllabusItem item : this.syllabusItems)
        {
            Log.d("adapter", item.toString());
        }
    }

    @NonNull
    @NotNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position)
    {
        Log.d("adapter spec", this.syllabusItems.get(position).toString());
        Log.d("adapter spec", String.valueOf(holder.name == null));
        holder.name.setText(this.syllabusItems.get(position).toString());
    }

    @Override
    public int getItemCount()
    {
        return this.syllabusItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            // age = itemView.findViewById(R.id.item_age);
        }
    }
}
