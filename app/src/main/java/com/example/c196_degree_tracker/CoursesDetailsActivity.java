package com.example.c196_degree_tracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.c196_degree_tracker.Entities.AssessmentEntity;
import com.example.c196_degree_tracker.Entities.MentorEntity;
import com.example.c196_degree_tracker.ViewModel.AssessmentViewModel;
import com.example.c196_degree_tracker.ViewModel.CourseViewModel;
import com.example.c196_degree_tracker.ViewModel.MentorViewModel;
import com.example.c196_degree_tracker.ViewModel.MyReceiver;
import com.example.c196_degree_tracker.ui.AssessmentAdapter;
import com.example.c196_degree_tracker.ui.MentorAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import android.widget.PopupMenu;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CoursesDetailsActivity extends AppCompatActivity {
    private CourseViewModel mCourseViewModel;
    private AssessmentViewModel mAssessmentViewModel;
    private MentorViewModel mMentorViewModel;
    private EditText mEditName;
    private EditText mEditStart;
    private EditText mEditEnd;
    private EditText mEditStatus;
    private EditText mEditNotes;
    long date;
    public static int numAssessments;
    public static int numMentors;
    private List<AssessmentEntity> filteredAssessments;
    private List<MentorEntity> filteredMentors;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        setContentView(R.layout.activity_courses_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mEditName = findViewById(R.id.textView9);
        mEditStart = findViewById(R.id.textView10);
        mEditEnd = findViewById(R.id.textView11);
        mEditStatus = findViewById(R.id.textView13);
        mEditNotes = findViewById(R.id.textView14);

        String temp = getIntent().getStringExtra("courseName");
        if (getIntent().getStringExtra("courseName") != null) {
            mEditName.setText(getIntent().getStringExtra("courseName"));
            mEditStart.setText(getIntent().getStringExtra("courseStart"));
            mEditEnd.setText(getIntent().getStringExtra("courseEnd"));
            mEditStatus.setText(getIntent().getStringExtra("courseStatus"));
            mEditNotes.setText(getIntent().getStringExtra("courseNotes"));
        }


        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        mAssessmentViewModel.getAllAssessments().observe(this, new Observer<List<AssessmentEntity>>() {
            @Override
            public void onChanged(@Nullable final List<AssessmentEntity> words) {
                List<AssessmentEntity> aNames = new ArrayList<>();
                for (AssessmentEntity a : words)
                    aNames.add(a);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener((view) -> {
            Intent intent = new Intent( CoursesDetailsActivity.this, CoursesEditActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            intent.putExtra("courseID", getIntent().getIntExtra("courseID", 0));
            intent.putExtra("Name", temp);
            intent.putExtra("Start", getIntent().getStringExtra("courseStart"));
            intent.putExtra("End", getIntent().getStringExtra("courseEnd"));
            intent.putExtra("Notes", getIntent().getStringExtra("courseNotes"));
            intent.putExtra("Status", getIntent().getStringExtra("courseStatus"));
            intent.putExtra("termID", getIntent().getIntExtra("termID", 0));
            intent.putExtra("numAssessments", numAssessments);
            intent.putExtra("numMentors", numMentors);
            startActivity(intent);
        });

        FloatingActionButton aFAB = findViewById(R.id.aFAB);
        aFAB.setOnClickListener((view) -> {
            showPopupA(aFAB);
        });

        FloatingActionButton mFAB = findViewById(R.id.mFAB);
        mFAB.setOnClickListener((view2) -> {
            showPopupM(mFAB);
        });

        try {
            RecyclerView recyclerView1 = findViewById(R.id.assessmentsRV);
            final AssessmentAdapter adapter1 = new AssessmentAdapter(this);
            recyclerView1.setAdapter(adapter1);
            recyclerView1.setLayoutManager(new LinearLayoutManager(this));
            mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
            mAssessmentViewModel.getAllAssessments().observe(this, new Observer<List<AssessmentEntity>>() {
                @Override
                public void onChanged(@Nullable final List<AssessmentEntity> words) {
                    filteredAssessments = new ArrayList<>();
                    for (AssessmentEntity a : words)
                        if (a.getCourseID() == getIntent().getIntExtra("courseID", 0))
                            filteredAssessments.add(a);
                    adapter1.setWords(filteredAssessments);
                    numAssessments = filteredAssessments.size();
                }
            });

            RecyclerView recyclerView2 = findViewById(R.id.mentorsRV);
            final MentorAdapter adapter2 = new MentorAdapter(this);
            recyclerView2.setAdapter(adapter2);
            recyclerView2.setLayoutManager(new LinearLayoutManager(this));
            mMentorViewModel = new ViewModelProvider(this).get(MentorViewModel.class);
            mMentorViewModel.getAllMentors().observe(this, new Observer<List<MentorEntity>>() {
                @Override
                public void onChanged(@Nullable final List<MentorEntity> words) {
                    filteredMentors = new ArrayList<>();
                    for (MentorEntity m : words)
                        if (m.getCourseID() == getIntent().getIntExtra("courseID", 0))
                            filteredMentors.add(m);
                    adapter2.setWords(filteredMentors);
                    numMentors = filteredMentors.size();
                }
            });
        } catch (NullPointerException e) {

        }
    }

    private void showPopupA(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        for(AssessmentEntity a : mAssessmentViewModel.getAllAssessments().getValue()) {
            popup.getMenu().add(a.getAssessmentName());
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                for(AssessmentEntity a : mAssessmentViewModel.getAllAssessments().getValue()) {
                         if(a.getAssessmentName().equals(item.toString())) {
                             a.setCourseID(getIntent().getIntExtra("courseID", 0));
                             mAssessmentViewModel.insert(a);
                    }
                }
                return false;
            }
        });
        popup.show();
    }

    private void showPopupM(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        for(MentorEntity m : mMentorViewModel.getAllMentors().getValue())
            popup.getMenu().add(m.getMentorName());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                for(MentorEntity m : mMentorViewModel.getAllMentors().getValue()) {
                    if(m.getMentorName().equals(item.toString())) {
                        m.setCourseID(getIntent().getIntExtra("courseID", 0));
                        mMentorViewModel.insert(m);
                    }
                }
                return false;
            }
        });
        popup.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_courses, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.sharing) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getStringExtra("courseName") + " Course Notes: " + getIntent().getStringExtra("courseNotes"));
            sendIntent.putExtra(Intent.EXTRA_TITLE, getIntent().getStringExtra("courseName") + " Notes");
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }
        if(id == R.id.CourseStartReminder) {
            Intent intent = new Intent(CoursesDetailsActivity.this, MyReceiver.class);
            intent.putExtra("key", "You have a course starting today!");
            PendingIntent sender= PendingIntent.getBroadcast(CoursesDetailsActivity.this, 0, intent, 0);
            AlarmManager alarmManager=(AlarmManager)getSystemService((Context.ALARM_SERVICE));
            SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date d = f.parse(mEditStart.getText().toString());
                long milli = d.getTime();
                date = milli;
                alarmManager.set(AlarmManager.RTC_WAKEUP, date, sender);
                Toast.makeText(getApplicationContext(), "Course Start Reminder Added!",Toast.LENGTH_LONG).show();
                return true;
            }
            catch (ParseException e) {
                System.out.println(e);
            }
        }
        if(id == R.id.CourseEndReminder) {
            Intent intent = new Intent(CoursesDetailsActivity.this, MyReceiver.class);
            intent.putExtra("key", "You have a course ending today!");
            PendingIntent sender= PendingIntent.getBroadcast(CoursesDetailsActivity.this, 1, intent, 0);
            AlarmManager alarmManager=(AlarmManager)getSystemService((Context.ALARM_SERVICE));
            SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date d = f.parse(mEditEnd.getText().toString());
                long milli = d.getTime();
                date = milli;
                alarmManager.set(AlarmManager.RTC_WAKEUP, date, sender);
                Toast.makeText(getApplicationContext(), "Course End Reminder Added!",Toast.LENGTH_LONG).show();
                return true;
            }
            catch (ParseException e) {
                System.out.println(e);
            }
        }
        return super.onOptionsItemSelected(item);
    }


        @Override
        public boolean onSupportNavigateUp () {
            Intent intent = new Intent(CoursesDetailsActivity.this, CoursesActivity.class);
            startActivity(intent);
            super.onBackPressed();
            return true;
    }
}