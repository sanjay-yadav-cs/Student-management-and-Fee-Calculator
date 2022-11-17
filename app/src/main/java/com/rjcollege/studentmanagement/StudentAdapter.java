package com.rjcollege.studentmanagement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.viewHolder> {
    Context context;
    ArrayList<StudentModel> list;
    ArrayList<StudentModel> studentlist;

    public StudentAdapter(Context context, ArrayList<StudentModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_profile_layout, parent, false);
        return new viewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        StudentModel model = list.get(position);
        holder.name.setText(model.getName());
        holder.currentClass.setText(new StringBuilder().append("Class: ").append(model.getCurrentClass()).toString());
        holder.remaningFee.setText(model.getLastMonthpaidfee());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, StudentDetailActivity.class);
                i.putExtra("id", model.getId());
                i.putExtra("name", model.getName());
                context.startActivity(i);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.delete_dialogbox_layout);

                new AlertDialog.Builder(context)
                        .setMessage("Do Want to Remove This Student")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                EditText etUsername, etpassword;
                                etUsername = dialog.findViewById(R.id.dialogUserName);
                                etpassword = dialog.findViewById(R.id.dialogPassword);
                                TextView btnok = dialog.findViewById(R.id.btnremoveDialog);
                                ImageView btndismiss = dialog.findViewById(R.id.dialogbtndissmiss);
                                btnok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String username = etUsername.getText().toString();
                                        String password = etpassword.getText().toString();

                                        if (username.equals("admin") && password.equals("Sanjay@1327")) {
                                            DBHelper helper = new DBHelper(context);
                                            helper.deleteStudent(model.getId());
                                            dialog.dismiss();
                                            notifyItemRemoved(position);
                                            studentlist = helper.getStudents();
                                            StudentAdapter adapter = new StudentAdapter(context, studentlist);
                                            RecyclerView recyclerView = ((Activity) context).findViewById(R.id.studentRecyclerView);
                                            recyclerView.setAdapter(adapter);
                                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                                            recyclerView.setLayoutManager(linearLayoutManager);
                                            Toast.makeText(context, "Removed Successfully !!", Toast.LENGTH_SHORT).show();

                                        }else {
                                            Toast.makeText(context, "Credentials wrong !!", Toast.LENGTH_SHORT).show();
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
                        })
                        .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();

                return true;
            }
        });


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = simpleDateFormat.format(Calendar.getInstance().getTime());

        String sDate = model.getAdmissionDate();
        String eDate = date;
        String FeeDate = model.getLastMonthpaidfee();

        if (FeeDate == null) {
            FeeDate = sDate;
        }
        if (FeeDate.equals("") || FeeDate.isEmpty() || FeeDate.length() < 10) {
            FeeDate = sDate;
        }
//        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date1 = simpleDateFormat.parse(sDate);
            Date date2 = simpleDateFormat.parse(eDate);
            Date date3 = simpleDateFormat.parse(FeeDate);

            long startdate = date1.getTime();
            long endDate = date2.getTime();
            long lastFeeDate = date3.getTime();


            if (lastFeeDate >= startdate) {
                if (lastFeeDate <= endDate) {
                    org.joda.time.Period period2 = new Period(lastFeeDate, endDate, PeriodType.yearMonthDay());
                    int years = period2.getYears();
                    int months = period2.getMonths();
                    int days = period2.getDays();
                    // show the final output
                    holder.remaningFee.setText(years + " Years | " + months + " Months | " + days + " Days");
                    int rupee = months * (Integer.parseInt(model.getFee()));
                    holder.bakifeeinrupee.setText("₹ -" + rupee);
                }
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView name, currentClass, remaningFee, bakifeeinrupee;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.studentName);
            currentClass = itemView.findViewById(R.id.currentClass);
            remaningFee = itemView.findViewById(R.id.ReamningFee);
            bakifeeinrupee = itemView.findViewById(R.id.bakifee);
        }
    }


}
