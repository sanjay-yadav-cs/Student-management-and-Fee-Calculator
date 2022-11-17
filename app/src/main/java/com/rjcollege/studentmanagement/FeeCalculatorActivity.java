package com.rjcollege.studentmanagement;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class FeeCalculatorActivity extends AppCompatActivity {
EditText addmissiondate,lastfeedate,todaysdate;
ImageButton btn1,btn2,btn3;
Button calculate;
TextView textView,addmMonth,addmDay,remainingFee,btnClear;
final Calendar myCalendar = Calendar.getInstance();
    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_calculator);

        getSupportActionBar().setTitle("FEE CALCULATOR");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = simpleDateFormat.format(Calendar.getInstance().getTime());

        addmissiondate=findViewById(R.id.addmission_date);
        lastfeedate=findViewById(R.id.last_fee_date);
        todaysdate=findViewById(R.id.today_date);
        btn1=findViewById(R.id.button1);
        btn2=findViewById(R.id.button2);
        btn3=findViewById(R.id.button3);
        calculate=findViewById(R.id.calculate);
        btnClear=findViewById(R.id.btnClear);

        textView=findViewById(R.id.textView);
        addmMonth=findViewById(R.id.addmMonth);
        addmDay=findViewById(R.id.addmYear);
        remainingFee=findViewById(R.id.remaingFee);

        todaysdate.setText(date);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(addmissiondate,FeeCalculatorActivity.this);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(lastfeedate,FeeCalculatorActivity.this);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(todaysdate,FeeCalculatorActivity.this);
            }
        });


        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateFee();

            }
        });


        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addmissiondate.setText("");
                lastfeedate.setText("");
                textView.setText("");
                remainingFee.setText("");
            }
        });

}




    public void setDate(EditText editText, Context context){
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
//        DatePickerDialog datePicker = new DatePickerDialog(FeeCalculatorActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, date,
//                myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                myCalendar.get(Calendar.DAY_OF_MONTH));
//        datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        datePicker.show();



        new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void calculateFee(){
        String sDate = addmissiondate.getText().toString();
        String eDate = todaysdate.getText().toString();
        String FeeDate = lastfeedate.getText().toString();
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        if (sDate.equals("")||sDate.isEmpty()||sDate.length()<10){
            Toast.makeText(this, "Enter Addmission Date", Toast.LENGTH_SHORT).show();
            return;
        }
        if (FeeDate.equals("")||FeeDate.isEmpty()||FeeDate.length()<10){
            FeeDate=sDate;
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
                textView.setText(years + " Years | " + months + " Months | " + days + " Days");
//                textView.setText(years + " Years");
//                addmMonth.setText(months + " Months");
//                addmDay.setText(days + " Days");
            } else {
                Toast.makeText(FeeCalculatorActivity.this, "Admission Date should not be larger than today's date!", Toast.LENGTH_SHORT).show();
            }

            if (lastFeeDate>=startdate) {
                if (lastFeeDate <= endDate) {
                    org.joda.time.Period period2 = new Period(lastFeeDate, endDate, PeriodType.yearMonthDay());
                    int years = period2.getYears();
                    int months = period2.getMonths();
                    int days = period2.getDays();

                    // show the final output
                    remainingFee.setText(years + " Years | " + months + " Months | " + days + " Days");
//                textView.setText(years + " Years");
//                addmMonth.setText(months + " Months");
//                addmDay.setText(days + " Days");
                } else {
                    Toast.makeText(FeeCalculatorActivity.this, "Last Fee Paid Date should not be larger than today's date!", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Last Date of Fee Paid is Not Possible Before Admission", Toast.LENGTH_SHORT).show();
            }


        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(FeeCalculatorActivity.this, "Enter Date in Proper Format", Toast.LENGTH_SHORT).show();
        }
    }
}