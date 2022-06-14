package com.example.c196_degree_tracker.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.c196_degree_tracker.Database.ScheduleManagementRepository;
import com.example.c196_degree_tracker.Entities.TermEntity;

import java.util.List;

public class TermViewModel extends AndroidViewModel {

    private ScheduleManagementRepository mRepository;
    private LiveData<List<TermEntity>> mAllTerms;
    public TermViewModel(Application application) {
        super(application);
        mRepository = new ScheduleManagementRepository(application);
        mAllTerms = mRepository.getAllTerms();
    }

    public LiveData<List<TermEntity>> getAllTerms() { return mAllTerms; }
    public void insert(TermEntity termEntity) { mRepository.insert(termEntity); }
    public void delete(TermEntity termEntity) { mRepository.delete(termEntity); }
    public int lastID() { return mAllTerms.getValue().size(); }

}
