package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    List<task> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView detail;
        TextView task_name;

        public ViewHolder(View view) {
            super(view);
            detail = view.findViewById(R.id.detail);
            task_name = view.findViewById(R.id.Category_name);
        }

        public void bind(task item) {
            detail.setText(item.detail);
            task_name.setText(item.task_name);
        }
    }

    public CustomAdapter(List<task> dataSet) {
        this.localDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(localDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
