package dev.eroberts.term_tracker.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import dev.eroberts.term_tracker.Database.TermTrackerRepo;
import dev.eroberts.term_tracker.Entities.entity_term;

import java.util.List;

public class TermViewModel extends AndroidViewModel {

    private TermTrackerRepo mRepository;
    private LiveData<List<entity_term>> mAllTerms;
    public TermViewModel(Application application) {
        super(application);
        mRepository = new TermTrackerRepo(application);
        mAllTerms = mRepository.getAllTerms();
    }

    public LiveData<List<entity_term>> getAllTerms() { return mAllTerms; }
    public void insert(entity_term entityterm) { mRepository.insert(entityterm); }
    public void delete(entity_term entityterm) { mRepository.delete(entityterm); }
    public int lastID() { return mAllTerms.getValue().size(); }

}
