package com.example.syllabuspro.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.syllabuspro.R;
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
        SyllabusItem item = this.syllabusItems.get(position);
        holder.displayName.setText((CharSequence) item.getName());
        holder.displayWeight.setText((CharSequence) Integer.toString(item.getWeight()));
        holder.displayDeadline.setText((CharSequence) item.getDeadline().toString());
        holder.displayType.setText((CharSequence) item.getType().toString());
    }

    @Override
    public int getItemCount()
    {
        return this.syllabusItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        // display text views
        TextView displayName;
        TextView displayWeight;
        TextView displayDeadline;
        TextView displayType;

        // input text views
        TextView inputName;
        TextView inputWeight;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            displayName = itemView.findViewById(R.id.display_item_name);
            displayWeight = itemView.findViewById(R.id.display_weight);
            displayDeadline = itemView.findViewById(R.id.display_deadline);
            displayType = itemView.findViewById(R.id.display_type);
            // age = itemView.findViewById(R.id.item_age);
        }
    }
}
