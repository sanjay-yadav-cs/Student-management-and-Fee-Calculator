package com.rjcollege.studentmanagement;

import androidx.annotation.NonNull;

public class StudentModel {
    private int id;
    private String name;
    private String currentClass;
    private String phone;
    private String admissionDate;
    private String lastMonthpaidfee;
    private String noteforstudent;
    private String fee;



    public StudentModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public StudentModel(int id, String name, String currentClass, String phone, String admissionDate, String lastMonthpaidfee, String noteforstudent, String fee) {
        this.id = id;
        this.name = name;
        this.currentClass = currentClass;
        this.phone = phone;
        this.admissionDate = admissionDate;
        this.lastMonthpaidfee = lastMonthpaidfee;
        this.noteforstudent = noteforstudent;
        this.fee = fee;
    }

//    public StudentModel(int id, String name, String currentClass, String admissionDate, String lastMonthpaidfee) {
//        this.id = id;
//        this.name = name;
//        this.currentClass = currentClass;
//        this.admissionDate = admissionDate;
//        this.lastMonthpaidfee = lastMonthpaidfee;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentClass() {
        return currentClass;
    }

    public void setCurrentClass(String currentClass) {
        this.currentClass = currentClass;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getLastMonthpaidfee() {
        return lastMonthpaidfee;
    }

    public void setLastMonthpaidfee(String lastMonthpaidfee) {
        this.lastMonthpaidfee = lastMonthpaidfee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNoteforstudent() {
        return noteforstudent;
    }

    public void setNoteforstudent(String noteforstudent) {
        this.noteforstudent = noteforstudent;
    }
}
