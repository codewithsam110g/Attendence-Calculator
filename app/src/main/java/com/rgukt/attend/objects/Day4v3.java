package com.rgukt.attend.objects;

import java.util.HashMap;

public class Day4v3 {
    private String Day;
    private String fnP1;
    private String fnP2;
    private String fnP3;
    private String fnP4;
    private String anP1;
    private String anP2;
    private String anP3;

    /**
     * This Return Empty Values of Every Field
     */
    public Day4v3() {
        this.Day = "NAN";
        this.fnP1 = "NAN";
        this.fnP2 = "NAN";
        this.fnP3 = "NAN";
        this.fnP4 = "NAN";
        this.anP1 = "NAN";
        this.anP2 = "NAN";
        this.anP3 = "NAN";
    }

    public Day4v3(String day, String fnP1, String fnP2, String fnP3, String fnP4, String anP1, String anP2, String anP3) {
        Day = day;
        this.fnP1 = fnP1;
        this.fnP2 = fnP2;
        this.fnP3 = fnP3;
        this.fnP4 = fnP4;
        this.anP1 = anP1;
        this.anP2 = anP2;
        this.anP3 = anP3;
    }

    public HashMap<String, String> getMorningPeriods() {
        HashMap<String, String> res = new HashMap<>();

        res.put("p1", fnP1);
        res.put("p2", fnP2);
        res.put("p3", fnP3);
        res.put("p4", fnP4);

        return res;
    }

    public HashMap<String, String> getAfterNoonPeriods() {
        HashMap<String, String> res = new HashMap<>();

        res.put("p1", anP1);
        res.put("p2", anP2);
        res.put("p3", anP3);

        return res;
    }

    public void setMorningPeriods(String p1, String p2, String p3, String p4) {
        this.fnP1 = p1;
        this.fnP2 = p2;
        this.fnP3 = p3;
        this.fnP4 = p4;
    }

    public void setAfternoonPeriods(String p1, String p2, String p3) {
        this.anP1 = p1;
        this.anP2 = p2;
        this.anP3 = p3;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }
}
