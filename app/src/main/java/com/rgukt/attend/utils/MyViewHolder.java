package com.rgukt.attend.utils;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rgukt.attend.R;


public class MyViewHolder extends RecyclerView.ViewHolder {

    // data variables to link with
    // the respective id from the view
    TextView subjectName, presentRatio, percentage;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        subjectName = itemView.findViewById(R.id.subjectName);
        presentRatio = itemView.findViewById(R.id.presentRat);
        percentage = itemView.findViewById(R.id.percentage);

    }
}
