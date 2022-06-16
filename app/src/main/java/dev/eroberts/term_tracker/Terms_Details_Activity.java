package dev.eroberts.term_tracker;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import dev.eroberts.term_tracker.Entities.entity_course;
import dev.eroberts.term_tracker.ViewModel.course_view_model;
import dev.eroberts.term_tracker.ViewModel.notification_receiver;
import dev.eroberts.term_tracker.ViewModel.term_view_model;
import dev.eroberts.term_tracker.UI.course_adapter;
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
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The type Terms details activity.
 */
public class Terms_Details_Activity extends AppCompatActivity {
    private term_view_model term_view_model_e;
    private course_view_model course_view_model_e;
    private EditText edit_start_text;
    private EditText edit_end_text;
    /**
     * The constant number_of_courses.
     */
    public static int number_of_courses;
    private List<entity_course> filtered_courses_list;
    /**
     * The Date.
     */
    long date;
    /**
     * The constant REQUEST.
     */
    public static final int REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        term_view_model_e = new ViewModelProvider(this).get(term_view_model.class);
        setContentView(R.layout.activity_terms_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        EditText edit_name_text = findViewById(R.id.assessment_details_name);
        edit_start_text =findViewById(R.id.assessment_details_date);
        edit_end_text =findViewById(R.id.assessment_details_type);
        String temp=getIntent().getStringExtra("termName");
        if(getIntent().getStringExtra("termName")!=null) {
            edit_name_text.setText(getIntent().getStringExtra("termName"));
            edit_start_text.setText(getIntent().getStringExtra("termStart"));
            edit_end_text.setText(getIntent().getStringExtra("termEnd"));
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        ImageView termsEdit = findViewById(R.id.fab);
        termsEdit.setOnClickListener((view) -> {
            Intent intent = new Intent( Terms_Details_Activity.this, Terms_Edit_Activity.class);
            startActivityForResult(intent, REQUEST);
            intent.putExtra("Name", temp);
            intent.putExtra("Start", getIntent().getStringExtra("termStart"));
            intent.putExtra("End", getIntent().getStringExtra("termEnd"));
            intent.putExtra("termID", getIntent().getIntExtra("termID", 0));
            intent.putExtra("numCourses", number_of_courses);
            startActivity(intent);
        });
        FloatingActionButton cFAB = findViewById(R.id.course_fab);
        cFAB.setOnClickListener((view) -> {
            showPopup(cFAB);
        });
        RecyclerView recyclerView = findViewById(R.id.termsRV);
        final course_adapter adapter = new course_adapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        course_view_model_e = new ViewModelProvider(this).get(course_view_model.class);
        course_view_model_e.getAllCourses().observe(this, new Observer<List<entity_course>>() {
            @Override
            public void onChanged(@Nullable final List<entity_course> words) {
                filtered_courses_list =new ArrayList<>();
                for(entity_course t:words) if(t.getTermID()==getIntent().getIntExtra("termID",0))
                    filtered_courses_list.add(t);
                adapter.setWords(filtered_courses_list);
                number_of_courses = filtered_courses_list.size();
            }
        });
    }

    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        for(entity_course c : course_view_model_e.getAllCourses().getValue()) {
            popup.getMenu().add(c.getCourseName());
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                for(entity_course c : course_view_model_e.getAllCourses().getValue()) {
                    if(c.getCourseName().equals(item.toString())) {
                        c.setTermID(getIntent().getIntExtra("termID", 0));
                        course_view_model_e.insert(c);
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
            Intent intent = new Intent(Terms_Details_Activity.this, notification_receiver.class);
            intent.putExtra("key", "Term starts today!");
            PendingIntent sender= PendingIntent.getBroadcast(Terms_Details_Activity.this, 3, intent, 0);
            AlarmManager alarmManager=(AlarmManager)getSystemService((Context.ALARM_SERVICE));
            SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date d = f.parse(edit_start_text.getText().toString());
                long milli = d.getTime();
                date = milli;
                alarmManager.set(AlarmManager.RTC_WAKEUP, date, sender);
                Toast.makeText(getApplicationContext(), "Created Term Starting Reminder",Toast.LENGTH_LONG).show();
                return true;
            }
            catch (ParseException e) {
                System.out.println(e);
            }
        }
        if(id == R.id.TermEndReminder) {
            Intent intent = new Intent(Terms_Details_Activity.this, notification_receiver.class);
            intent.putExtra("key", "Term ends today!");
            PendingIntent sender= PendingIntent.getBroadcast(Terms_Details_Activity.this, 4, intent, 0);
            AlarmManager alarmManager=(AlarmManager)getSystemService((Context.ALARM_SERVICE));
            SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date d = f.parse(edit_end_text.getText().toString());
                long milli = d.getTime();
                date = milli;
                alarmManager.set(AlarmManager.RTC_WAKEUP, date, sender);
                Toast.makeText(getApplicationContext(), "Created Term Ending Reminder",Toast.LENGTH_LONG).show();
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
        Intent intent = new Intent( Terms_Details_Activity.this, Terms_Activity.class);
        startActivity(intent);
        super.onBackPressed();
        return true;
    }
}
