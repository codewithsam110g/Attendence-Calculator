package com.rgukt.attend.utils;

import com.rgukt.attend.objects.SubjectData;
import com.rgukt.attend.objects.SubjectViewData;

public class Utils {
    public static SubjectData toSubjectData(SubjectViewData subView) {
        SubjectData dat = new SubjectData();

        dat.setSubjectName(subView.getSubjectName());

        String s = subView.getPresentRatio().trim();
        String[] val = s.split("/");
        int present = Integer.parseInt(val[0]);
        int total = Integer.parseInt((val[1].trim().length() != 0) ? val[1].trim() : "1");

        dat.setPresentClasses(present);
        dat.setTotalClasses(total);
        dat.setSubjectId(subView.getSubjectId());

        return dat;
    }

    public static SubjectViewData toSubjectViewData(SubjectData sub) {
        SubjectViewData viewDat = new SubjectViewData();

        viewDat.setSubjectName(sub.getSubjectName());

        String presentRatio = (sub.getPresentClasses() + "/") + ((sub.getTotalClasses() != 0) ? sub.getTotalClasses() : 1);
        float percent = ((float) sub.getPresentClasses() / (float) ((sub.getTotalClasses() != 0) ? sub.getTotalClasses() : 1)) * 100;
        viewDat.setPresentRatio(presentRatio);
        viewDat.setPresentPercent(String.valueOf(percent));
        viewDat.setSubjectId(sub.getSubjectId());
        return viewDat;
    }

}
