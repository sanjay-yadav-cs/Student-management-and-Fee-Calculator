package com.rjcollege.studentmanagement;

public class FeePaidMonthModel {
    private int id;
    private String monthName;
    private boolean paid;

    public FeePaidMonthModel(int id, String monthName, boolean paid) {
        this.id = id;
        this.monthName = monthName;
        this.paid = paid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
