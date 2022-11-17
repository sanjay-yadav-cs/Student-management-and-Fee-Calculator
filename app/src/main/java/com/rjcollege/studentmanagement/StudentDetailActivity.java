package com.rjcollege.studentmanagement;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StudentDetailActivity extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    EditText etNote;
    TextView etTotaldayfromadmission, etTotalreamningfee, etName, etPhone, etStandard, etAdmissionDate, etFees, feespaidtillthis;
    ImageButton btnsetdate;
    TextView savenote, btnEdit;
    RecyclerView recyclerView;
    Cursor cursor;
    String date;
    DBHelper helper = new DBHelper(StudentDetailActivity.this);
    int id;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);
        getSupportActionBar().hide();

        feespaidtillthis = findViewById(R.id.feePaidTillThisDate);
        btnsetdate = findViewById(R.id.setdateSD);
        etTotaldayfromadmission = findViewById(R.id.textViewSD);
        etName = findViewById(R.id.studentNameSD);
        etPhone = findViewById(R.id.phoneSD);
        etStandard = findViewById(R.id.standardSD);
        etTotalreamningfee = findViewById(R.id.remaingFeeSD);
        etAdmissionDate = findViewById(R.id.addmission_dateSD);
        recyclerView = findViewById(R.id.feepaidMonths);
        etNote = findViewById(R.id.noteForStudentSD);
        etFees = findViewById(R.id.feeSD);
        savenote = findViewById(R.id.savenoteSD);
        btnEdit = findViewById(R.id.btnedit);

        id = getIntent().getIntExtra("id", 1);
        cursor = helper.getStudentById(id);


        String name = cursor.getString(1);
        String currentClass = cursor.getString(2);
        String Mobile = cursor.getString(3);
        String admissiondate = cursor.getString(4);
        String fee = cursor.getString(6);
        String note = cursor.getString(7);

        etName.setText("Name: " + name);
        etStandard.setText("Class: " + currentClass);
        etAdmissionDate.setText("Admission Date: " + admissiondate);
        etPhone.setText("Mobile No: " + Mobile);
        etFees.setText("Fee: ₹" + fee);
        etNote.setText(note);

        String feefilltill = cursor.getString(5);
//        if (feefilltill == null) {
//           feefilltill = "";
//        }
        feespaidtillthis.setText(feefilltill);

        FeeCalculatorActivity feeCalculatorActivity = new FeeCalculatorActivity();
        btnsetdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(feespaidtillthis, StudentDetailActivity.this);
            }
        });


        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = simpleDateFormat.format(Calendar.getInstance().getTime());
        calculateFee();

        Dialog dialog = new Dialog(StudentDetailActivity.this);
        dialog.setContentView(R.layout.delete_dialogbox_layout);

        savenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText etUsername, etpassword;
                etUsername = dialog.findViewById(R.id.dialogUserName);
                etpassword = dialog.findViewById(R.id.dialogPassword);
                TextView btnok = dialog.findViewById(R.id.btnremoveDialog);
                btnok.setText("Save Changes");
                ImageView btndismiss = dialog.findViewById(R.id.dialogbtndissmiss);
                ImageView iconimage = dialog.findViewById(R.id.image);
                iconimage.setImageResource(R.drawable.ic_baseline_update_24);
                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String username = etUsername.getText().toString();
                        String password = etpassword.getText().toString();

                        if (username.equals("admin") && password.equals("Sanjay@1327")) {

                            boolean isUpdated = helper.updateSudent(id, name, currentClass, Mobile, admissiondate, feespaidtillthis.getText().toString(), etNote.getText().toString(), fee);
                            if (isUpdated) {
                                calculateFee();
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(getIntent());
                                overridePendingTransition(0, 0);
                                Toast.makeText(StudentDetailActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(StudentDetailActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(StudentDetailActivity.this, "Credentials wrong !!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                btndismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

        ArrayList<FeePaidMonthModel> list = new ArrayList<>();
        list.add(new FeePaidMonthModel(1, "January", true));
        list.add(new FeePaidMonthModel(1, "February", true));
        list.add(new FeePaidMonthModel(1, "March", true));
        list.add(new FeePaidMonthModel(1, "April", false));
        list.add(new FeePaidMonthModel(1, "May", false));

        PaidFeeMonthAdapter adapter = new PaidFeeMonthAdapter(list, StudentDetailActivity.this);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(StudentDetailActivity.this);
        recyclerView.setLayoutManager(layoutManager);


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = helper.getStudentById(id);
                String name = cursor.getString(1);
                String currentClass = cursor.getString(2);
                String Mobile = cursor.getString(3);
                String admissiondate = cursor.getString(4);
                String fee = cursor.getString(6);
                String note = cursor.getString(7);
                Intent i = new Intent(StudentDetailActivity.this, AdmissionActivity.class);
                i.putExtra("update", true);
                i.putExtra("id", id);
                i.putExtra("name", name);
                i.putExtra("class", currentClass);
                i.putExtra("admissiondate", admissiondate);
                i.putExtra("feepaidtill", feefilltill);
                i.putExtra("mobile", Mobile);
                i.putExtra("fee", fee);
                i.putExtra("note", note);
                startActivity(i);
            }
        });


    }


    public void calculateFee() {
        String sDate = cursor.getString(4);
        String eDate = date;
        String FeeDate = cursor.getString(5);
        if (FeeDate == null) {
            FeeDate = sDate;
        }
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        if (sDate.equals("") || sDate.isEmpty() || sDate.length() < 10) {
            Toast.makeText(this, "Enter Addmission Date", Toast.LENGTH_SHORT).show();
            return;
        }
        if (FeeDate.equals("") || FeeDate.isEmpty() || FeeDate.length() < 10) {
            FeeDate = sDate;
        }
        try {
            Date date1 = simpleDateFormat1.parse(sDate);
            Date date2 = simpleDateFormat1.parse(eDate);
            Date date3 = simpleDateFormat1.parse(FeeDate);

            long startdate = date1.getTime();
            long endDate = date2.getTime();
            long lastFeeDate = date3.getTime();

            if (startdate <= endDate) {
                org.joda.time.Period period = new Period(startdate, endDate, PeriodType.yearMonthDay());
                int years = period.getYears();
                int months = period.getMonths();
                int days = period.getDays();

                // show the final output
                etTotaldayfromadmission.setText(years + " Years | " + months + " Months | " + days + " Days");
            } else {
                Toast.makeText(StudentDetailActivity.this, "Admission Date should not be larger than today's date!", Toast.LENGTH_SHORT).show();
            }

            if (lastFeeDate >= startdate) {
                if (lastFeeDate <= endDate) {
                    org.joda.time.Period period2 = new Period(lastFeeDate, endDate, PeriodType.yearMonthDay());
                    int years = period2.getYears();
                    int months = period2.getMonths();
                    int days = period2.getDays();

                    // show the final output
                    etTotalreamningfee.setText(years + " Years | " + months + " Months | " + days + " Days");
                } else {
                    Toast.makeText(StudentDetailActivity.this, "Last Fee Paid Date should not be larger than today's date!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Last Date of Fee Paid is Not Possible Before Admission", Toast.LENGTH_SHORT).show();
            }


        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(StudentDetailActivity.this, "Enter Date in Proper Format", Toast.LENGTH_SHORT).show();
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
        DatePickerDialog datePicker = new DatePickerDialog(StudentDetailActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, date,
                myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePicker.show();


//        new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor cursor = helper.getStudentById(id);
        String name = cursor.getString(1);
        String currentClass = cursor.getString(2);
        String Mobile = cursor.getString(3);
        String admissiondate = cursor.getString(4);
        String fee = cursor.getString(6);
        String note = cursor.getString(7);
        etName.setText("Name: " + name);
        etStandard.setText("Class: " + currentClass);
        etAdmissionDate.setText("Admission Date: " + admissiondate);
        etPhone.setText("Mobile No: " + Mobile);
        etFees.setText("Fee: " + fee + "₹");
        etNote.setText(note);
    }
}