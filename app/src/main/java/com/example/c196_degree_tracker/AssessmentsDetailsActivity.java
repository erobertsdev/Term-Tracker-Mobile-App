package com.example.c196_degree_tracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.c196_degree_tracker.Entities.CourseEntity;
import com.example.c196_degree_tracker.ViewModel.AssessmentViewModel;
import com.example.c196_degree_tracker.ViewModel.CourseViewModel;
import com.example.c196_degree_tracker.ViewModel.MentorViewModel;
import com.example.c196_degree_tracker.ViewModel.MyReceiver;
import com.example.c196_degree_tracker.ui.CourseAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssessmentsDetailsActivity extends AppCompatActivity {
    private AssessmentViewModel mAssessmentViewModel;
    private EditText mEditName;
    private EditText mEditDate;
    private EditText mEditType;
    long date;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        setContentView(R.layout.activity_assessments_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mEditName=findViewById(R.id.textView9);
        mEditDate=findViewById(R.id.textView10);
        mEditType=findViewById(R.id.textView11);

        String temp=getIntent().getStringExtra("assessmentName");
        if(getIntent().getStringExtra("assessmentName")!=null) {
            mEditName.setText(getIntent().getStringExtra("assessmentName"));
            mEditDate.setText(getIntent().getStringExtra("assessmentDate"));
            mEditType.setText(getIntent().getStringExtra("assessmentType"));
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        ImageView assessmentsEdit = findViewById(R.id.fab);
        assessmentsEdit.setOnClickListener((view) -> {
            Intent intent = new Intent( AssessmentsDetailsActivity.this, AssessmentsEditActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            intent.putExtra("assessmentID", getIntent().getIntExtra("assessmentID", 0));
            intent.putExtra("Name", temp);
            intent.putExtra("Date", getIntent().getStringExtra("assessmentDate"));
            intent.putExtra("Type", getIntent().getStringExtra("assessmentType"));
            intent.putExtra("courseID", getIntent().getIntExtra("courseID", 0));
            startActivity(intent);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_assessments, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if(id == R.id.AssessmentDateReminder) {
            Intent intent = new Intent(AssessmentsDetailsActivity.this, MyReceiver.class);
            intent.putExtra("key", "You have an Assessment today!");
            PendingIntent sender= PendingIntent.getBroadcast(AssessmentsDetailsActivity.this, 5, intent, 0);
            AlarmManager alarmManager=(AlarmManager)getSystemService((Context.ALARM_SERVICE));
            SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date d = f.parse(mEditDate.getText().toString());
                long milli = d.getTime();
                date = milli;
                alarmManager.set(AlarmManager.RTC_WAKEUP, date, sender);
                Toast.makeText(getApplicationContext(), "Assessment Date Reminder Added!",Toast.LENGTH_LONG).show();
                return true;
            }
            catch (ParseException e) {
                System.out.println(e);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent( AssessmentsDetailsActivity.this, AssessmentsActivity.class);
        startActivity(intent);
        super.onBackPressed();
        return true;
    }
}
