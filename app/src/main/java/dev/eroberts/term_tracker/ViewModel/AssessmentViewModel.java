package dev.eroberts.term_tracker.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import dev.eroberts.term_tracker.Database.ScheduleManagementRepository;
import dev.eroberts.term_tracker.Entities.entity_assessment;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {
    int courseID;
    private ScheduleManagementRepository mRepository;
    private LiveData<List<entity_assessment>> mAssociatedAssessments;
    private LiveData<List<entity_assessment>> mAllAssessments;

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

    public LiveData<List<entity_assessment>> getAssociatedAssessments(int courseID){
        return mRepository.getAssociatedAssessments(courseID);
    }

    public LiveData<List<entity_assessment>> getAllAssessments() { return mAllAssessments; };
    public void insert(entity_assessment entityassessment) { mRepository.insert(entityassessment); }
    public void delete(entity_assessment entityassessment) { mRepository.delete(entityassessment); }
    public int lastID() { return mAllAssessments.getValue().size(); }
}
