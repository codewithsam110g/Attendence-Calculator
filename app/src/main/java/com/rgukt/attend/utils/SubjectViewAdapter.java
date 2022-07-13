package com.rgukt.attend.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rgukt.attend.R;
import com.rgukt.attend.objects.SubjectViewData;

import java.util.ArrayList;

public class SubjectViewAdapter extends RecyclerView.Adapter<SubjectViewAdapter.ViewHolder> {

    int lastPos = -1;
    private ArrayList<SubjectViewData> viewDataArray;
    private Context context;
    private SubjectViewClickInterface subjectViewClickInterface;

    public SubjectViewAdapter(ArrayList<SubjectViewData> viewDataArray, Context context, SubjectViewClickInterface subjectViewClickInterface) {
        this.viewDataArray = viewDataArray;
        this.context = context;
        this.subjectViewClickInterface = subjectViewClickInterface;
    }

    @NonNull
    @Override
    public SubjectViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subject_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewAdapter.ViewHolder holder, int position) {
        SubjectViewData dat = viewDataArray.get(position);
        holder.name.setText(dat.getSubjectName());
        holder.ratio.setText(dat.getPresentRatio());
        holder.percent.setText(dat.getCurrentPresentPercentage() + "%");
        holder.recPercent.setText(dat.getRecommendedPercentage() + "%");
        setAnimation(holder.itemView, position);
        holder.itemView.setOnClickListener(v -> {
            subjectViewClickInterface.onSubjectClick(position);
        });
    }

    private void setAnimation(View itemView, int pos) {
        if (pos > lastPos) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = pos;
        }
    }

    @Override
    public int getItemCount() {
        return viewDataArray.size();
    }

    public interface SubjectViewClickInterface {
        void onSubjectClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, ratio, percent, recPercent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_subject_name);
            ratio = itemView.findViewById(R.id.txt_present_ratio);
            percent = itemView.findViewById(R.id.txt_percentage);
            recPercent = itemView.findViewById(R.id.txt_rec_percent);
        }
    }
}
