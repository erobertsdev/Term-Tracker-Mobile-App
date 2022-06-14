package com.example.c196_degree_tracker.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.c196_degree_tracker.Database.ScheduleManagementRepository;
import com.example.c196_degree_tracker.Entities.MentorEntity;

import java.util.List;

public class MentorViewModel extends AndroidViewModel {
    int courseID;
    private ScheduleManagementRepository mRepository;
    private LiveData<List<MentorEntity>> mAssociatedMentors;
    private LiveData<List<MentorEntity>> mAllMentors;

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

    public LiveData<List<MentorEntity>> getAssociatedMentors(int courseID){
        return mRepository.getAssociatedMentors(courseID);
    }

    public LiveData<List<MentorEntity>> getAllMentors() { return mAllMentors; };
    public void insert(MentorEntity mentorEntity) { mRepository.insert(mentorEntity); }
    public void delete(MentorEntity mentorEntity) { mRepository.delete(mentorEntity);}
    public int lastID() { return mAllMentors.getValue().size(); }
}
