package dev.eroberts.term_tracker;
import android.content.Intent;
import android.os.Bundle;
import dev.eroberts.term_tracker.Entities.entity_term;
import dev.eroberts.term_tracker.ViewModel.term_view_model;
import dev.eroberts.term_tracker.UI.term_adapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;
import java.util.List;

/**
 * The type Terms activity.
 */
public class TermsActivity extends AppCompatActivity {
    private term_view_model term_view_model_e;
    /**
     * The constant REQUEST.
     */
    public static final int REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        term_view_model_e =new ViewModelProvider(this).get(term_view_model.class);
        setContentView(R.layout.activity_terms);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FloatingActionButton fab = findViewById(R.id.fab);
        ImageView termsAdd = findViewById(R.id.fab);
        termsAdd.setOnClickListener((view) -> {
            Intent intent = new Intent( TermsActivity.this, TermsAddActivity.class);
            startActivityForResult(intent, REQUEST);
        });
        RecyclerView recyclerView = findViewById(R.id.termsRV);
        final term_adapter adapter = new term_adapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        term_view_model_e.getAllTerms().observe(this, new Observer<List<entity_term>>() {
            @Override
            public void onChanged(@Nullable final List<entity_term> words) {
                adapter.setWords(words);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {entity_term term = new entity_term(term_view_model_e.lastID()+1, data.getStringExtra("termName"), data.getStringExtra("termStart"), data.getStringExtra("termEnd"));
            term_view_model_e.insert(term);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent( TermsActivity.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
        return true;
    }
}
