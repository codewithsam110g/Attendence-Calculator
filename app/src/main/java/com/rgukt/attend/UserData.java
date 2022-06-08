package com.rgukt.attend;

public class UserData {

    public int totalDays;
    public int totalWorkingDates;
    public int totalPresentDates;
    public int telClass;
    public int engClass;
    public int matClass;
    public int phyClass;
    public int chemClass;
    //public int itClass;

    public UserData(){
     totalDays = 0;
     totalWorkingDates = 0;
     totalPresentDates = 0;
     telClass = 0;
     engClass = 0;
     matClass = 0;
     phyClass = 0;
     chemClass = 0;
     //itClass = 0;
    }

    public UserData(int totalDays, int totalWorkingDates, int totalPresentDates, int telClass, int engClass, int matClass, int phyClass, int chemClass) {
        this.totalDays = totalDays;
        this.totalWorkingDates = totalWorkingDates;
        this.totalPresentDates = totalPresentDates;
        this.telClass = telClass;
        this.engClass = engClass;
        this.matClass = matClass;
        this.phyClass = phyClass;
        this.chemClass = chemClass;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public int getTotalWorkingDates() {
        return totalWorkingDates;
    }

    public void setTotalWorkingDates(int totalWorkingDates) {
        this.totalWorkingDates = totalWorkingDates;
    }

    public int getTotalPresentDates() {
        return totalPresentDates;
    }

    public void setTotalPresentDates(int totalPresentDates) {
        this.totalPresentDates = totalPresentDates;
    }

    public int getTelClass() {
        return telClass;
    }

    public void setTelClass(int telClass) {
        this.telClass = telClass;
    }

    public int getEngClass() {
        return engClass;
    }

    public void setEngClass(int engClass) {
        this.engClass = engClass;
    }

    public int getMatClass() {
        return matClass;
    }

    public void setMatClass(int matClass) {
        this.matClass = matClass;
    }

    public int getPhyClass() {
        return phyClass;
    }

    public void setPhyClass(int phyClass) {
        this.phyClass = phyClass;
    }

    public int getChemClass() {
        return chemClass;
    }

    public void setChemClass(int chemClass) {
        this.chemClass = chemClass;
    }
}
