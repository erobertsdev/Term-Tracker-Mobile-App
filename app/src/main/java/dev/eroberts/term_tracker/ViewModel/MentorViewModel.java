package dev.eroberts.term_tracker.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import dev.eroberts.term_tracker.Database.ScheduleManagementRepository;
import dev.eroberts.term_tracker.Entities.entity_mentor;

import java.util.List;

public class MentorViewModel extends AndroidViewModel {
    int courseID;
    private ScheduleManagementRepository mRepository;
    private LiveData<List<entity_mentor>> mAssociatedMentors;
    private LiveData<List<entity_mentor>> mAllMentors;

    public MentorViewModel(Application application, int courseID){
        super(application);
        mRepository=new ScheduleManagementRepository(application);
        mAssociatedMentors=mRepository.getAssociatedMentors(courseID);
    }

    public MentorViewModel(Application application){
        super(application);
        mRepository=new ScheduleManagementRepository(application);
        mAllMentors=mRepository.getAllMentors();
        mAssociatedMentors=mRepository.getAssociatedMentors(courseID);
    }

    public LiveData<List<entity_mentor>> getAssociatedMentors(int courseID){
        return mRepository.getAssociatedMentors(courseID);
    }

    public LiveData<List<entity_mentor>> getAllMentors() { return mAllMentors; };
    public void insert(entity_mentor entitymentor) { mRepository.insert(entitymentor); }
    public void delete(entity_mentor entitymentor) { mRepository.delete(entitymentor);}
    public int lastID() { return mAllMentors.getValue().size(); }
}
