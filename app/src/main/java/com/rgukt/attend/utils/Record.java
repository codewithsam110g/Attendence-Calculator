package com.rgukt.attend.utils;

public class Record {

    private String SubjectName;
    private String PresentRatio;
    private String PresentPercent;


    public Record() {
        SubjectName = "NAN";
        PresentRatio = "NAN";
        PresentPercent = "NAN";
    }

    public Record(String subjectName, String presentRatio, String presentPercent) {
        SubjectName = subjectName;
        PresentRatio = presentRatio;
        PresentPercent = presentPercent;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public String getPresentRatio() {
        return PresentRatio;
    }

    public void setPresentRatio(String presentRatio) {
        PresentRatio = presentRatio;
    }

    public String getPresentPercent() {
        return PresentPercent;
    }

    public void setPresentPercent(String presentPercent) {
        PresentPercent = presentPercent;
    }
}
