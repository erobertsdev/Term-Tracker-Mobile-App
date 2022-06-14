package dev.eroberts.term_tracker;

import android.content.Intent;
import android.os.Bundle;

import dev.eroberts.term_tracker.Entities.entity_mentor;

import dev.eroberts.term_tracker.ViewModel.MentorViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MentorsAddActivity extends AppCompatActivity {
    private MentorViewModel mMentorViewModel;
    private EditText mEditName;
    private EditText mEditEmail;
    private EditText mEditPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMentorViewModel = new ViewModelProvider(this).get(MentorViewModel.class);
        setContentView(R.layout.activity_mentors_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mEditName=findViewById(R.id.mentorNameTxt);
        mEditEmail=findViewById(R.id.mentorEmailTxt);
        mEditPhone=findViewById(R.id.mentorPhoneTxt);

        try {

            mMentorViewModel.getAllMentors().observe(this, new Observer<List<entity_mentor>>() {
                @Override
                public void onChanged(List<entity_mentor> mentorEntities) {

                }
            });

        } catch (NullPointerException e) {

        }
        try {
            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String name = mEditName.getText().toString();
                    String email = mEditEmail.getText().toString();
                    String phone = mEditPhone.getText().toString();

                    if(name.matches("") || email.matches("") || phone.matches("")) {
                        Toast.makeText(getApplicationContext(), "Name, Email, & Phone fields cannot be blank.",Toast.LENGTH_LONG).show();
                    }
                    else {
                        entity_mentor mentor = new entity_mentor(mMentorViewModel.lastID() + 1, mEditName.getText().toString(), mEditEmail.getText().toString(),
                                mEditPhone.getText().toString(), 0);
                        mMentorViewModel.insert(mentor);
                        Intent intent = new Intent(MentorsAddActivity.this, MentorsActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
        catch(NullPointerException e) {

        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
