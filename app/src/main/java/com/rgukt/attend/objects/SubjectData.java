package com.rgukt.attend.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class SubjectData implements Parcelable {

    public static final Creator<SubjectData> CREATOR = new Creator<SubjectData>() {
        @Override
        public SubjectData createFromParcel(Parcel in) {
            return new SubjectData(in);
        }

        @Override
        public SubjectData[] newArray(int size) {
            return new SubjectData[size];
        }
    };
    private String subjectName;
    private int presentClasses;
    private int totalClasses;
    private int recommendedPercentage;
    private String subjectId;

    public SubjectData() {
        subjectName = "NAN";
        presentClasses = 0;
        totalClasses = 1;
        recommendedPercentage = 0;
        subjectId = "NAN";
    }

    public SubjectData(String subjectName, int presentClasses, int totalClasses, int recPercentage, String subjectID) {
        this.subjectName = subjectName;
        this.presentClasses = presentClasses;
        this.totalClasses = totalClasses;
        this.recommendedPercentage = recPercentage;
        this.subjectId = subjectID;
    }

    protected SubjectData(Parcel in) {
        subjectName = in.readString();
        presentClasses = in.readInt();
        totalClasses = in.readInt();
        recommendedPercentage = in.readInt();
        subjectId = in.readString();
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getPresentClasses() {
        return presentClasses;
    }

    public void setPresentClasses(int presentClasses) {
        this.presentClasses = presentClasses;
    }

    public int getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(int totalClasses) {
        this.totalClasses = totalClasses;
    }

    public int getRecommendedPercentage() {
        return recommendedPercentage;
    }

    public void setRecommendedPercentage(int recommendedPercentage) {
        this.recommendedPercentage = recommendedPercentage;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(subjectName);
        dest.writeInt(presentClasses);
        dest.writeInt(totalClasses);
        dest.writeInt(recommendedPercentage);
        dest.writeString(subjectId);
    }
}
