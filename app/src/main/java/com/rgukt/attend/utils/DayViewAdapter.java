package com.rgukt.attend.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rgukt.attend.R;
import com.rgukt.attend.objects.Day4v3;

import java.util.ArrayList;

public class DayViewAdapter extends RecyclerView.Adapter<DayViewAdapter.ViewHolder> {

    private ArrayList<Day4v3> viewDataArray;
    private Context context;

    public DayViewAdapter(ArrayList<Day4v3> viewDataArray, Context context) {
        this.viewDataArray = viewDataArray;
        this.context = context;
    }

    public void SetDataArray(ArrayList<Day4v3> days) {
        this.viewDataArray = days;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DayViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.day_item, parent, false);
        return new DayViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewAdapter.ViewHolder holder, int position) {
        Day4v3 day = viewDataArray.get(position);
        holder.day.setText("Day: " + day.getDay());
        holder.p1.setText("Period 1: " + day.getMorningPeriods().get("p1"));
        holder.p2.setText("Period 2: " + day.getMorningPeriods().get("p2"));
        holder.p3.setText("Period 3: " + day.getMorningPeriods().get("p3"));
        holder.p4.setText("Period 4: " + day.getMorningPeriods().get("p4"));
        holder.p5.setText("Period 5: " + day.getAfterNoonPeriods().get("p1"));
        holder.p6.setText("Period 6: " + day.getAfterNoonPeriods().get("p2"));
        holder.p7.setText("Period 7: " + day.getAfterNoonPeriods().get("p3"));
    }


    @Override
    public int getItemCount() {
        return viewDataArray.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView day, p1, p2, p3, p4, p5, p6, p7;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.txt_day);
            p1 = itemView.findViewById(R.id.txt_p1);
            p2 = itemView.findViewById(R.id.txt_p2);
            p3 = itemView.findViewById(R.id.txt_p3);
            p4 = itemView.findViewById(R.id.txt_p4);
            p5 = itemView.findViewById(R.id.txt_p5);
            p6 = itemView.findViewById(R.id.txt_p6);
            p7 = itemView.findViewById(R.id.txt_p7);
        }
    }
}
