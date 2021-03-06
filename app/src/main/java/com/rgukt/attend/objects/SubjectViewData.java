package com.rgukt.attend.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class SubjectViewData implements Parcelable {

    public static final Creator<SubjectViewData> CREATOR = new Creator<SubjectViewData>() {
        @Override
        public SubjectViewData createFromParcel(Parcel in) {
            return new SubjectViewData(in);
        }

        @Override
        public SubjectViewData[] newArray(int size) {
            return new SubjectViewData[size];
        }
    };
    private String subjectName;
    private String presentRatio;
    private String currentPresentPercentage;
    private String recommendedPercentage;
    private String subjectId;

    public SubjectViewData() {

    }

    public SubjectViewData(String subjectName, String presentRatio, String presentPercent, String recPercent, String subjectId) {
        this.subjectName = subjectName;
        this.presentRatio = presentRatio;
        this.currentPresentPercentage = presentPercent;
        this.recommendedPercentage = recPercent;
        this.subjectId = subjectId;
    }

    protected SubjectViewData(Parcel in) {
        subjectName = in.readString();
        presentRatio = in.readString();
        currentPresentPercentage = in.readString();
        recommendedPercentage = in.readString();
        subjectId = in.readString();
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getPresentRatio() {
        return presentRatio;
    }

    public void setPresentRatio(String presentRatio) {
        this.presentRatio = presentRatio;
    }

    public String getCurrentPresentPercentage() {
        return currentPresentPercentage;
    }

    public void setCurrentPresentPercentage(String currentPresentPercentage) {
        this.currentPresentPercentage = currentPresentPercentage;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getRecommendedPercentage() {
        return recommendedPercentage;
    }

    public void setRecommendedPercentage(String recommendedPercentage) {
        this.recommendedPercentage = recommendedPercentage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(subjectName);
        dest.writeString(presentRatio);
        dest.writeString(currentPresentPercentage);
        dest.writeString(recommendedPercentage);
        dest.writeString(subjectId);
    }
}
