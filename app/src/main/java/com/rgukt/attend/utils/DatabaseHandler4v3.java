package com.rgukt.attend.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rgukt.attend.objects.Day4v3;

import java.util.HashMap;

public class DatabaseHandler4v3 extends SQLiteOpenHelper {

    private static final String DB_NAME = "timetable.db";

    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "myTimetable";

    private static final String ID_COL = "id";

    private static final String DAY = "Day";

    private static final String PER1 = "Period_1";
    private static final String PER2 = "Period_2";
    private static final String PER3 = "Period_3";
    private static final String PER4 = "Period_4";
    private static final String PER5 = "Period_5";
    private static final String PER6 = "Period_6";
    private static final String PER7 = "Period_7";

    public DatabaseHandler4v3(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DAY + " TEXT,"
                + PER1 + " TEXT,"
                + PER2 + " TEXT,"
                + PER3 + " TEXT,"
                + PER4 + " TEXT,"
                + PER5 + " TEXT,"
                + PER6 + " TEXT,"
                + PER7 + " TEXT)";

        db.execSQL(query);
    }

    public boolean addDayToTable(Day4v3 d) {

        {
            Day4v3 ds = new Day4v3();
            if (d == ds) return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DAY, d.getDay());
        values.put(PER1, d.getMorningPeriods().get("p1"));
        values.put(PER2, d.getMorningPeriods().get("p2"));
        values.put(PER3, d.getMorningPeriods().get("p3"));
        values.put(PER4, d.getMorningPeriods().get("p4"));
        values.put(PER5, d.getAfterNoonPeriods().get("p1"));
        values.put(PER6, d.getAfterNoonPeriods().get("p2"));
        values.put(PER7, d.getAfterNoonPeriods().get("p3"));

        db.insert(TABLE_NAME, null, values);
        db.close();

        return true;

    }

    @SuppressLint("Range")
    public HashMap<String, Day4v3> readDaysInTable() {
        HashMap<String, Day4v3> res = new HashMap<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor days = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (days.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                res.put(days.getString(days.getColumnIndex(DAY)), new Day4v3(
                        days.getString(days.getColumnIndex(DAY)),
                        days.getString(days.getColumnIndex(PER1)),
                        days.getString(days.getColumnIndex(PER2)),
                        days.getString(days.getColumnIndex(PER3)),
                        days.getString(days.getColumnIndex(PER4)),
                        days.getString(days.getColumnIndex(PER5)),
                        days.getString(days.getColumnIndex(PER6)),
                        days.getString(days.getColumnIndex(PER7))
                ));
            } while (days.moveToNext());
        }

        days.close();
        return res;
    }

    public boolean updateDayInTable(String day, Day4v3 newDayData) {

        {
            Day4v3 ds = new Day4v3();
            if (newDayData == ds) return false;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(DAY, newDayData.getDay());
        values.put(PER1, newDayData.getMorningPeriods().get("p1"));
        values.put(PER2, newDayData.getMorningPeriods().get("p2"));
        values.put(PER3, newDayData.getMorningPeriods().get("p3"));
        values.put(PER4, newDayData.getMorningPeriods().get("p4"));
        values.put(PER5, newDayData.getAfterNoonPeriods().get("p1"));
        values.put(PER6, newDayData.getAfterNoonPeriods().get("p2"));
        values.put(PER7, newDayData.getAfterNoonPeriods().get("p3"));

        db.update(TABLE_NAME, values, "Day=?", new String[]{day});
        db.close();

        return true;

    }

    public boolean deleteDayInTable(String day) {
        SQLiteDatabase db = this.getReadableDatabase();
        int res = db.delete(TABLE_NAME, "Day=?", new String[]{day});
        db.close();

        if (res == SQLiteDatabase.CONFLICT_NONE) {
            return true;
        }

        return false;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
