package com.rgukt.attend.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rgukt.attend.R;
import com.rgukt.attend.objects.Day4v3;
import com.rgukt.attend.utils.DatabaseHandler4v3;
import com.rgukt.attend.utils.DayViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ReadDayFragment extends Fragment {

    String[] days = {
            "monday",
            "tuesday",
            "wednesday",
            "thursday",
            "friday",
            "saturday"
    };
    private ArrayList<Day4v3> dayList;
    private DatabaseHandler4v3 db;
    private DayViewAdapter adapter;
    private RecyclerView rv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read_day, container, false);

        dayList = new ArrayList<>();
        db = new DatabaseHandler4v3(getContext());
        dayList = getDays(db.readDaysInTable());
        adapter = new DayViewAdapter(dayList, getContext());
        rv = view.findViewById(R.id.rv_days);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);

        return view;
    }

    private ArrayList<Day4v3> getDays(HashMap<String, Day4v3> d) {
        return new ArrayList<>(d.values());
    }


    @Override
    public void onResume() {
        super.onResume();
        adapter.SetDataArray(getDays(db.readDaysInTable()));
    }
}