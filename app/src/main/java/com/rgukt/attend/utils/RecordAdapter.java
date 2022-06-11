package com.rgukt.attend.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rgukt.attend.R;

import java.util.ArrayList;

// Extends the Adapter class to RecyclerView.Adapter
// and implement the unimplemented methods
public class RecordAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    ArrayList<Record> list;

    // constructor with Record's data model list and view context
    public RecordAdapter(Context context, ArrayList<Record> list) {
        this.context = context;
        this.list = list;
    }

    // Binding data to the
    // into specified position
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the recycler view
        // layout in its view component
        View v = LayoutInflater.from(context).inflate(R.layout.record,parent,false);
        return new MyViewHolder(v);
    }

    // holder method to set respective
    // data to their components
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Record user = list.get(position);
        holder.subjectName.setText(user.getSubjectName());
        holder.presentRatio.setText(user.getPresentRatio());
        holder.percentage.setText(user.getPresentPercent());
    }

    // method to return the
    // position of the item
    @Override
    public int getItemCount() {
        return list.size();
    }

}
