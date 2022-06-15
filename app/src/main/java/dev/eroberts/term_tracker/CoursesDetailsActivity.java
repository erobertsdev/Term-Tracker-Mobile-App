package dev.eroberts.term_tracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import dev.eroberts.term_tracker.Entities.entity_assessment;
import dev.eroberts.term_tracker.Entities.entity_mentor;

import dev.eroberts.term_tracker.ViewModel.assessment_view_model;
import dev.eroberts.term_tracker.ViewModel.course_view_model;
import dev.eroberts.term_tracker.ViewModel.MentorViewModel;
import dev.eroberts.term_tracker.ViewModel.MyReceiver;
import dev.eroberts.term_tracker.UI.assessment_adapter;
import dev.eroberts.term_tracker.UI.mentor_adapter;
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
    private course_view_model mCourseViewModel;
    private assessment_view_model mAssessmentViewModel;
    private MentorViewModel mMentorViewModel;
    private EditText mEditName;
    private EditText mEditStart;
    private EditText mEditEnd;
    private EditText mEditStatus;
    private EditText mEditNotes;
    long date;
    public static int numAssessments;
    public static int numMentors;
    private List<entity_assessment> filteredAssessments;
    private List<entity_mentor> filteredMentors;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCourseViewModel = new ViewModelProvider(this).get(course_view_model.class);
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


        mAssessmentViewModel = new ViewModelProvider(this).get(assessment_view_model.class);
        mAssessmentViewModel.getAllAssessments().observe(this, new Observer<List<entity_assessment>>() {
            @Override
            public void onChanged(@Nullable final List<entity_assessment> words) {
                List<entity_assessment> aNames = new ArrayList<>();
                for (entity_assessment a : words)
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
            final assessment_adapter adapter1 = new assessment_adapter(this);
            recyclerView1.setAdapter(adapter1);
            recyclerView1.setLayoutManager(new LinearLayoutManager(this));
            mAssessmentViewModel = new ViewModelProvider(this).get(assessment_view_model.class);
            mAssessmentViewModel.getAllAssessments().observe(this, new Observer<List<entity_assessment>>() {
                @Override
                public void onChanged(@Nullable final List<entity_assessment> words) {
                    filteredAssessments = new ArrayList<>();
                    for (entity_assessment a : words)
                        if (a.getCourseID() == getIntent().getIntExtra("courseID", 0))
                            filteredAssessments.add(a);
                    adapter1.setWords(filteredAssessments);
                    numAssessments = filteredAssessments.size();
                }
            });

            RecyclerView recyclerView2 = findViewById(R.id.mentorsRV);
            final mentor_adapter adapter2 = new mentor_adapter(this);
            recyclerView2.setAdapter(adapter2);
            recyclerView2.setLayoutManager(new LinearLayoutManager(this));
            mMentorViewModel = new ViewModelProvider(this).get(MentorViewModel.class);
            mMentorViewModel.getAllMentors().observe(this, new Observer<List<entity_mentor>>() {
                @Override
                public void onChanged(@Nullable final List<entity_mentor> words) {
                    filteredMentors = new ArrayList<>();
                    for (entity_mentor m : words)
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
        for(entity_assessment a : mAssessmentViewModel.getAllAssessments().getValue()) {
            popup.getMenu().add(a.getAssessmentName());
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                for(entity_assessment a : mAssessmentViewModel.getAllAssessments().getValue()) {
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
        for(entity_mentor m : mMentorViewModel.getAllMentors().getValue())
            popup.getMenu().add(m.getMentorName());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                for(entity_mentor m : mMentorViewModel.getAllMentors().getValue()) {
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