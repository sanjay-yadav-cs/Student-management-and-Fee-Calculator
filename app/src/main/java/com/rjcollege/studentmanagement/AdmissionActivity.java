package com.rjcollege.studentmanagement;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AdmissionActivity extends AppCompatActivity {
    EditText etname, etcurrentClass, etphone, etfees, etaddmissiondate, etnoteforstudent;
    Button save;
    ImageView setdate;

    DBHelper helper = new DBHelper(this);
    final Calendar myCalendar = Calendar.getInstance();

//    boolean update = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission);



        getSupportActionBar().setTitle("Admission Page");
        getSupportActionBar().setHomeButtonEnabled(true);

       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = simpleDateFormat.format(Calendar.getInstance().getTime());

        etname = findViewById(R.id.studentNameAA);
        etcurrentClass = findViewById(R.id.standardAA);
        etphone = findViewById(R.id.phoneAA);
        etfees = findViewById(R.id.feesAA);
        etaddmissiondate = findViewById(R.id.addmission_dateAA);
        etnoteforstudent = findViewById(R.id.noteForStudentAA);
        save = findViewById(R.id.btnSaveAA);
        setdate = findViewById(R.id.setdateAA);


        boolean update = getIntent().getBooleanExtra("update", false);

        if (update) {
            int id = getIntent().getIntExtra("id",-1);
            etname.setText(getIntent().getStringExtra("name"));
            etcurrentClass.setText(getIntent().getStringExtra("class"));
            etphone.setText(getIntent().getStringExtra("mobile"));
            etfees.setText(getIntent().getStringExtra("fee"));
            etaddmissiondate.setText(getIntent().getStringExtra("admissiondate"));
            etnoteforstudent.setText(getIntent().getStringExtra("note"));
            String feesfilltill = getIntent().getStringExtra("feepaidtill");
            save.setText("Update");
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  boolean isupdated =  helper.updateSudent(id,etname.getText().toString(),
                            etcurrentClass.getText().toString(),
                            etphone.getText().toString(),
                            etaddmissiondate.getText().toString(),
                            feesfilltill,
                            etnoteforstudent.getText().toString(),
                            etfees.getText().toString());
                  if (isupdated){
                      Toast.makeText(AdmissionActivity.this, "Updated Succesfully", Toast.LENGTH_SHORT).show();
                  }else {
                      Toast.makeText(AdmissionActivity.this, "Error", Toast.LENGTH_SHORT).show();
                  }
                }
            });
        }else {
            etaddmissiondate.setText(date);

            setdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setDate(etaddmissiondate,AdmissionActivity.this);
                }
            });


            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = etname.getText().toString();
                    String currentClass = etcurrentClass.getText().toString();
                    String phone = etphone.getText().toString();
                    String fees = etfees.getText().toString();
                    String addmissiondate = etaddmissiondate.getText().toString();
                    String noteforstudent = etnoteforstudent.getText().toString();
                    boolean isInserted = helper.insertStudent(name, currentClass, phone, addmissiondate, noteforstudent, fees);
                    etname.setText("");
                    etcurrentClass.setText("");
                    etphone.setText("");
                    etfees.setText("");
                    etaddmissiondate.setText("");
                    etnoteforstudent.setText("");
                    if (isInserted) {
                        Toast.makeText(AdmissionActivity.this, name+" Admitted Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AdmissionActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public void setDate(TextView editText, Context context) {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                editText.setText(dateFormat.format(myCalendar.getTime()));
//                check_dob_set = false;
            }


        };

//    white wala datepicker
//        DatePickerDialog datePicker = new DatePickerDialog(AdmissionActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, date,
//                myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                myCalendar.get(Calendar.DAY_OF_MONTH));
//        datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        datePicker.show();


        new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }



    @Override
    protected void onResume() {
        super.onResume();
        etname.setText(getIntent().getStringExtra("name"));
        etcurrentClass.setText(getIntent().getStringExtra("class"));
        etphone.setText(getIntent().getStringExtra("mobile"));
        etfees.setText(getIntent().getStringExtra("fee"));
        etaddmissiondate.setText(getIntent().getStringExtra("admissiondate"));
        etnoteforstudent.setText(getIntent().getStringExtra("note"));
        String feesfilltill = getIntent().getStringExtra("feepaidtill");
    }
}