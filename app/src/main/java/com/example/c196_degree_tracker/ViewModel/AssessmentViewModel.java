package com.example.c196_degree_tracker.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.c196_degree_tracker.DAO.AssessmentDAO;
import com.example.c196_degree_tracker.Database.ScheduleManagementRepository;
import com.example.c196_degree_tracker.Entities.AssessmentEntity;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {
    int courseID;
    private ScheduleManagementRepository mRepository;
    private LiveData<List<AssessmentEntity>> mAssociatedAssessments;
    private LiveData<List<AssessmentEntity>> mAllAssessments;

    public AssessmentViewModel(Application application, int courseID){
        super(application);
        mRepository=new ScheduleManagementRepository(application);
        mAssociatedAssessments=mRepository.getAssociatedAssessments(courseID);
    }

    public AssessmentViewModel(Application application){
        super(application);
        mRepository=new ScheduleManagementRepository(application);
        mAllAssessments=mRepository.getAllAssessments();
        mAssociatedAssessments=mRepository.getAssociatedAssessments(courseID);
    }

    public LiveData<List<AssessmentEntity>> getAssociatedAssessments(int courseID){
        return mRepository.getAssociatedAssessments(courseID);
    }

    public LiveData<List<AssessmentEntity>> getAllAssessments() { return mAllAssessments; };
    public void insert(AssessmentEntity assessmentEntity) { mRepository.insert(assessmentEntity); }
    public void delete(AssessmentEntity assessmentEntity) { mRepository.delete(assessmentEntity); }
    public int lastID() { return mAllAssessments.getValue().size(); }
}
