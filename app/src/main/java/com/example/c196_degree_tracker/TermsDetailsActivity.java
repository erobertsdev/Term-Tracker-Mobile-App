package com.example.c196_degree_tracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.c196_degree_tracker.Entities.AssessmentEntity;
import com.example.c196_degree_tracker.Entities.CourseEntity;
import com.example.c196_degree_tracker.Entities.MentorEntity;
import com.example.c196_degree_tracker.Entities.TermEntity;
import com.example.c196_degree_tracker.ViewModel.CourseViewModel;
import com.example.c196_degree_tracker.ViewModel.MyReceiver;
import com.example.c196_degree_tracker.ViewModel.TermViewModel;
import com.example.c196_degree_tracker.ui.CourseAdapter;
import com.example.c196_degree_tracker.ui.TermAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TermsDetailsActivity extends AppCompatActivity {
    private TermViewModel mTermViewModel;
    private CourseViewModel mCourseViewModel;
    private EditText mEditName;
    private EditText mEditStart;
    private EditText mEditEnd;
    public static int numCourses;
    private List<CourseEntity> filteredCourses;
    long date;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        setContentView(R.layout.activity_terms_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mEditName=findViewById(R.id.textView9);
        mEditStart=findViewById(R.id.textView10);
        mEditEnd=findViewById(R.id.textView11);

        String temp=getIntent().getStringExtra("termName");
        if(getIntent().getStringExtra("termName")!=null) {
            mEditName.setText(getIntent().getStringExtra("termName"));
            mEditStart.setText(getIntent().getStringExtra("termStart"));
            mEditEnd.setText(getIntent().getStringExtra("termEnd"));
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        ImageView termsEdit = findViewById(R.id.fab);
        termsEdit.setOnClickListener((view) -> {

            Intent intent = new Intent( TermsDetailsActivity.this, TermsEditActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            intent.putExtra("Name", temp);
            intent.putExtra("Start", getIntent().getStringExtra("termStart"));
            intent.putExtra("End", getIntent().getStringExtra("termEnd"));
            intent.putExtra("termID", getIntent().getIntExtra("termID", 0));
            intent.putExtra("numCourses", numCourses);
            startActivity(intent);
        });

        FloatingActionButton cFAB = findViewById(R.id.cFAB);
        cFAB.setOnClickListener((view) -> {
            showPopup(cFAB);
        });

        RecyclerView recyclerView = findViewById(R.id.termsRV);
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        mCourseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(@Nullable final List<CourseEntity> words) {
                filteredCourses=new ArrayList<>();
                for(CourseEntity t:words) if(t.getTermID()==getIntent().getIntExtra("termID",0))filteredCourses.add(t);
                adapter.setWords(filteredCourses);
                numCourses=filteredCourses.size();
            }
        });
    }

    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        for(CourseEntity c : mCourseViewModel.getAllCourses().getValue()) {
            popup.getMenu().add(c.getCourseName());
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                for(CourseEntity c : mCourseViewModel.getAllCourses().getValue()) {
                    if(c.getCourseName().equals(item.toString())) {
                        c.setTermID(getIntent().getIntExtra("termID", 0));
                        mCourseViewModel.insert(c);
                    }
                }
                return false;
            }
        });
        popup.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_terms, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if(id == R.id.TermStartReminder) {
            Intent intent = new Intent(TermsDetailsActivity.this, MyReceiver.class);
            intent.putExtra("key", "You have a Term starting today!");
            PendingIntent sender= PendingIntent.getBroadcast(TermsDetailsActivity.this, 3, intent, 0);
            AlarmManager alarmManager=(AlarmManager)getSystemService((Context.ALARM_SERVICE));
            SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date d = f.parse(mEditStart.getText().toString());
                long milli = d.getTime();
                date = milli;
                alarmManager.set(AlarmManager.RTC_WAKEUP, date, sender);
                Toast.makeText(getApplicationContext(), "Term Start Reminder Added!",Toast.LENGTH_LONG).show();
                return true;
            }
            catch (ParseException e) {
                System.out.println(e);
            }
        }
        if(id == R.id.TermEndReminder) {
            Intent intent = new Intent(TermsDetailsActivity.this, MyReceiver.class);
            intent.putExtra("key", "You have a Term ending today!");
            PendingIntent sender= PendingIntent.getBroadcast(TermsDetailsActivity.this, 4, intent, 0);
            AlarmManager alarmManager=(AlarmManager)getSystemService((Context.ALARM_SERVICE));
            SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date d = f.parse(mEditEnd.getText().toString());
                long milli = d.getTime();
                date = milli;
                alarmManager.set(AlarmManager.RTC_WAKEUP, date, sender);
                Toast.makeText(getApplicationContext(), "Term End Reminder Added!",Toast.LENGTH_LONG).show();
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
        Intent intent = new Intent( TermsDetailsActivity.this, TermsActivity.class);
        startActivity(intent);
        super.onBackPressed();
        return true;
    }
}
