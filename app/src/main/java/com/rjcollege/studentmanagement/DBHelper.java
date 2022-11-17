package com.rjcollege.studentmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    final static String DBNAME="Tution";
    final static int DBVERSION = 1;
    public DBHelper(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table students"+
                "(id integer primary key autoincrement," +
                        "name text," +
                        "currentclass text," +
                        "phone text," +
                        "admissiondate text," +
                        "lastfeepaiddate text," +
                        "fees text," +
                        "noteforstudent text)"
                );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP table if exists students");
        onCreate(sqLiteDatabase);
    }


    public boolean insertStudent(String name, String currentClass, String phone, String admissiondate, String noteforstudent, String fee){
        SQLiteDatabase database = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("currentclass",currentClass);
        values.put("phone",phone);
        values.put("admissiondate",admissiondate);
        values.put("noteforstudent",noteforstudent);
        values.put("fees",fee.toString());
        long id = database.insert("students",null,values);
        if (id<=0){
            return false;
        }else {
            return true;
        }
    }



    public ArrayList<StudentModel> getStudents(){
        ArrayList<StudentModel> students = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from students",null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                StudentModel model = new StudentModel(cursor.getInt(0),cursor.getString(1));
                model.setId(cursor.getInt(0));
                model.setName(cursor.getString(1));
                model.setCurrentClass(cursor.getString(2));
                model.setPhone(cursor.getString(3));
                model.setAdmissionDate(cursor.getString(4));
                model.setLastMonthpaidfee(cursor.getString(5));
                model.setNoteforstudent(cursor.getString(7));
//                model.setFee("200");
                model.setFee(cursor.getString(6));
                students.add(model);
            }
        }
        cursor.close();
        database.close();
        return students;
    }


    public Cursor getStudentById(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from students where id=" + id, null);
        if (cursor.moveToNext()){
            return cursor;
        }
        return cursor;
    }

    public boolean updateSudent(int id,String name, String currentClass, String phone, String admissiondate,String lastdateofPaidFee, String noteforstudent, String fee){
        SQLiteDatabase database = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("currentclass",currentClass);
        values.put("phone",phone);
        values.put("admissiondate",admissiondate);
        values.put("lastfeepaiddate",lastdateofPaidFee);
        values.put("noteforstudent",noteforstudent);
        values.put("fees",fee.toString());
        long row = database.update("students",values,"id="+id,null);
        if (row<=0){
            return false;
        }else {
            return true;
        }
    }


    public int deleteStudent(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete("students","id="+id,null);
    }


}
