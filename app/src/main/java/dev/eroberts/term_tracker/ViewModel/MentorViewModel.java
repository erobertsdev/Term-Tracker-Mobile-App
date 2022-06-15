package dev.eroberts.term_tracker.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import dev.eroberts.term_tracker.Database.TermTrackerRepo;
import dev.eroberts.term_tracker.Entities.entity_mentor;

import java.util.List;

public class MentorViewModel extends AndroidViewModel {
    int courseID;
    private TermTrackerRepo mRepository;
    private LiveData<List<entity_mentor>> mAssociatedMentors;
    private LiveData<List<entity_mentor>> mAllMentors;

    public MentorViewModel(Application application, int courseID){
        super(application);
        mRepository=new TermTrackerRepo(application);
        mAssociatedMentors=mRepository.get_associated_mentors(courseID);
    }

    public MentorViewModel(Application application){
        super(application);
        mRepository=new TermTrackerRepo(application);
        mAllMentors=mRepository.get_all_mentors();
        mAssociatedMentors=mRepository.get_associated_mentors(courseID);
    }

    public LiveData<List<entity_mentor>> getAssociatedMentors(int courseID){
        return mRepository.get_associated_mentors(courseID);
    }

    public LiveData<List<entity_mentor>> getAllMentors() { return mAllMentors; };
    public void insert(entity_mentor entitymentor) { mRepository.insert(entitymentor); }
    public void delete(entity_mentor entitymentor) { mRepository.delete(entitymentor);}
    public int lastID() { return mAllMentors.getValue().size(); }
}
