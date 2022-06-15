package dev.eroberts.term_tracker;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import dev.eroberts.term_tracker.ViewModel.assessment_view_model;
import dev.eroberts.term_tracker.ViewModel.notification_receiver;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The type Assessments details activity.
 */
public class AssessmentsDetailsActivity extends AppCompatActivity {
    private assessment_view_model assessment_view_model_e;
    private EditText edit_date_e;
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
        assessment_view_model_e = new ViewModelProvider(this).get(assessment_view_model.class);
        setContentView(R.layout.activity_assessments_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        EditText edit_name_e = findViewById(R.id.assessment_details_name);
        edit_date_e =findViewById(R.id.assessment_details_date);
        EditText edit_type_e = findViewById(R.id.assessment_details_type);
        String temp=getIntent().getStringExtra("assessmentName");
        if(getIntent().getStringExtra("assessmentName")!=null) {
            edit_name_e.setText(getIntent().getStringExtra("assessmentName"));
            edit_date_e.setText(getIntent().getStringExtra("assessmentDate"));
            edit_type_e.setText(getIntent().getStringExtra("assessmentType"));
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        ImageView assessmentsEdit = findViewById(R.id.fab);
        assessmentsEdit.setOnClickListener((view) -> {
            Intent intent = new Intent( AssessmentsDetailsActivity.this, AssessmentsEditActivity.class);
            startActivityForResult(intent, REQUEST);
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
        if (id == R.id.AssessmentDateReminder) {
            Intent intent = new Intent(AssessmentsDetailsActivity.this, notification_receiver.class);
            intent.putExtra("key", "You have an Assessment today!");
            PendingIntent sender = PendingIntent.getBroadcast(AssessmentsDetailsActivity.this, 5, intent, 0);
            AlarmManager alarmManager = (AlarmManager)getSystemService((Context.ALARM_SERVICE));
            SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date d = f.parse(edit_date_e.getText().toString());
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
