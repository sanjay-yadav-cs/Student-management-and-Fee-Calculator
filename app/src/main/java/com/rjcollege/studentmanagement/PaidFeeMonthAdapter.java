package com.rjcollege.studentmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PaidFeeMonthAdapter extends RecyclerView.Adapter<PaidFeeMonthAdapter.ViewHolder> {
    ArrayList<FeePaidMonthModel> list = new ArrayList<>();
    Context context;

    public PaidFeeMonthAdapter(ArrayList<FeePaidMonthModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PaidFeeMonthAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fees_paid_months,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaidFeeMonthAdapter.ViewHolder holder, int position) {
        FeePaidMonthModel model = list.get(position);
        holder.month.setText(model.getMonthName());
        holder.checkBox.setChecked(model.isPaid());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView month;
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           month= itemView.findViewById(R.id.monthname);
           checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
