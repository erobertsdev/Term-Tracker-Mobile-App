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
import dev.eroberts.term_tracker.ViewModel.mentor_view_model;
import dev.eroberts.term_tracker.ViewModel.notification_receiver;
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

/**
 * The type Courses details activity.
 */
public class Courses_Details_Activity extends AppCompatActivity {
    private course_view_model course_view_model_e;
    private assessment_view_model assessment_view_model_e;
    private mentor_view_model mentor_view_model_e;
    private EditText edit_start_txt;
    private EditText edit_end_txt;
    /**
     * The Date.
     */
    long date;
    /**
     * The constant number_of_assessments.
     */
    public static int number_of_assessments;
    /**
     * The constant number_of_mentors.
     */
    public static int number_of_mentors;
    private List<entity_assessment> filtered_assessments_list;
    private List<entity_mentor> filtered_mentors_list;
    /**
     * The constant REQUEST.
     */
    public static final int REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        course_view_model_e = new ViewModelProvider(this).get(course_view_model.class);
        setContentView(R.layout.activity_courses_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        EditText edit_name_txt = findViewById(R.id.assessment_details_name);
        edit_start_txt = findViewById(R.id.assessment_details_date);
        edit_end_txt = findViewById(R.id.assessment_details_type);
        EditText edit_status_txt = findViewById(R.id.textView13);
        EditText edit_notes_txt = findViewById(R.id.course_status_text_view);
        String temp = getIntent().getStringExtra("courseName");
        if (getIntent().getStringExtra("courseName") != null) {
            edit_name_txt.setText(getIntent().getStringExtra("courseName"));
            edit_start_txt.setText(getIntent().getStringExtra("courseStart"));
            edit_end_txt.setText(getIntent().getStringExtra("courseEnd"));
            edit_status_txt.setText(getIntent().getStringExtra("courseStatus"));
            edit_notes_txt.setText(getIntent().getStringExtra("courseNotes"));
        }
        assessment_view_model_e = new ViewModelProvider(this).get(assessment_view_model.class);
        assessment_view_model_e.getAllAssessments().observe(this, new Observer<List<entity_assessment>>() {
            @Override
            public void onChanged(@Nullable final List<entity_assessment> words) {
                List<entity_assessment> aNames = new ArrayList<>();
                for (entity_assessment a : words)
                    aNames.add(a);
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener((view) -> {
            Intent intent = new Intent( Courses_Details_Activity.this, Courses_Edit_Activity.class);
            startActivityForResult(intent, REQUEST);
            intent.putExtra("courseID", getIntent().getIntExtra("courseID", 0));
            intent.putExtra("Name", temp);
            intent.putExtra("Start", getIntent().getStringExtra("courseStart"));
            intent.putExtra("End", getIntent().getStringExtra("courseEnd"));
            intent.putExtra("Notes", getIntent().getStringExtra("courseNotes"));
            intent.putExtra("Status", getIntent().getStringExtra("courseStatus"));
            intent.putExtra("termID", getIntent().getIntExtra("termID", 0));
            intent.putExtra("numAssessments", number_of_assessments);
            intent.putExtra("numMentors", number_of_mentors);
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
            assessment_view_model_e = new ViewModelProvider(this).get(assessment_view_model.class);
            assessment_view_model_e.getAllAssessments().observe(this, new Observer<List<entity_assessment>>() {
                @Override
                public void onChanged(@Nullable final List<entity_assessment> words) {
                    filtered_assessments_list = new ArrayList<>();
                    for (entity_assessment a : words)
                        if (a.getCourseID() == getIntent().getIntExtra("courseID", 0))
                            filtered_assessments_list.add(a);
                    adapter1.setWords(filtered_assessments_list);
                    number_of_assessments = filtered_assessments_list.size();
                }
            });
            RecyclerView recyclerView2 = findViewById(R.id.mentorsRV);
            final mentor_adapter adapter2 = new mentor_adapter(this);
            recyclerView2.setAdapter(adapter2);
            recyclerView2.setLayoutManager(new LinearLayoutManager(this));
            mentor_view_model_e = new ViewModelProvider(this).get(mentor_view_model.class);
            mentor_view_model_e.getAllMentors().observe(this, new Observer<List<entity_mentor>>() {
                @Override
                public void onChanged(@Nullable final List<entity_mentor> words) {
                    filtered_mentors_list = new ArrayList<>();
                    for (entity_mentor m : words)
                        if (m.getCourseID() == getIntent().getIntExtra("courseID", 0))
                            filtered_mentors_list.add(m);
                    adapter2.setWords(filtered_mentors_list);
                    number_of_mentors = filtered_mentors_list.size();
                }
            });
        } catch (NullPointerException e) {
        }
    }

    private void showPopupA(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        for(entity_assessment a : assessment_view_model_e.getAllAssessments().getValue()) {
            popup.getMenu().add(a.getAssessmentName());
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                for(entity_assessment a : assessment_view_model_e.getAllAssessments().getValue()) {
                         if(a.getAssessmentName().equals(item.toString())) {
                             a.setCourseID(getIntent().getIntExtra("courseID", 0));
                             assessment_view_model_e.insert(a);
                    }
                }
                return false;
            }
        });
        popup.show();
    }

    private void showPopupM(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        for(entity_mentor m : mentor_view_model_e.getAllMentors().getValue())
            popup.getMenu().add(m.getMentorName());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                for(entity_mentor m : mentor_view_model_e.getAllMentors().getValue()) {
                    if(m.getMentorName().equals(item.toString())) {
                        m.setCourseID(getIntent().getIntExtra("courseID", 0));
                        mentor_view_model_e.insert(m);
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
        if (id == R.id.sharing_menu) {
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
            Intent intent = new Intent(Courses_Details_Activity.this, notification_receiver.class);
            intent.putExtra("key", "A course is starting today");
            PendingIntent sender= PendingIntent.getBroadcast(Courses_Details_Activity.this, 0, intent, 0);
            AlarmManager alarmManager=(AlarmManager)getSystemService((Context.ALARM_SERVICE));
            SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date d = f.parse(edit_start_txt.getText().toString());
                long milli = d.getTime();
                date = milli;
                alarmManager.set(AlarmManager.RTC_WAKEUP, date, sender);
                Toast.makeText(getApplicationContext(), "Created Course Starting Reminder",Toast.LENGTH_LONG).show();
                return true;
            }
            catch (ParseException e) {
                System.out.println(e);
            }
        }
        if(id == R.id.CourseEndReminder) {
            Intent intent = new Intent(Courses_Details_Activity.this, notification_receiver.class);
            intent.putExtra("key", "A course is ending today");
            PendingIntent sender= PendingIntent.getBroadcast(Courses_Details_Activity.this, 1, intent, 0);
            AlarmManager alarmManager=(AlarmManager)getSystemService((Context.ALARM_SERVICE));
            SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date d = f.parse(edit_end_txt.getText().toString());
                long milli = d.getTime();
                date = milli;
                alarmManager.set(AlarmManager.RTC_WAKEUP, date, sender);
                Toast.makeText(getApplicationContext(), "Created Course Ending Reminder",Toast.LENGTH_LONG).show();
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
            Intent intent = new Intent(Courses_Details_Activity.this, Courses_Activity.class);
            startActivity(intent);
            super.onBackPressed();
            return true;
    }
}