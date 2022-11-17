package com.rjcollege.studentmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.R.id;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton admission;
    DBHelper helper;
    ArrayList<StudentModel> studentlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().setTitle("SD CLASSES");
        recyclerView=findViewById(R.id.studentRecyclerView);
        admission=findViewById(R.id.floatingActionButton);

        helper = new DBHelper(this);
        studentlist = helper.getStudents();

        

        Collections.sort(studentlist, new Comparator<StudentModel>() {
                    @Override
                    public int compare(StudentModel o, StudentModel t1) {

                        return o.getName().compareTo(t1.getName());
                    }
                });

        StudentAdapter adapter = new StudentAdapter(MainActivity.this,studentlist);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter.notifyItemInserted(studentlist.size()-1);
        recyclerView.scrollToPosition(studentlist.size()-1);



        admission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AdmissionActivity.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.fee_calulator){
            startActivity(new Intent(MainActivity.this,FeeCalculatorActivity.class));
        }
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        studentlist = helper.getStudents();
        StudentAdapter adapter = new StudentAdapter(MainActivity.this,studentlist);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

}