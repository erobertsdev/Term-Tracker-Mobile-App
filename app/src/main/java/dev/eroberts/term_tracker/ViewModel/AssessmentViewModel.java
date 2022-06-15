package dev.eroberts.term_tracker.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import dev.eroberts.term_tracker.Database.TermTrackerRepo;
import dev.eroberts.term_tracker.Entities.entity_assessment;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {
    int courseID;
    private TermTrackerRepo mRepository;
    private LiveData<List<entity_assessment>> mAssociatedAssessments;
    private LiveData<List<entity_assessment>> mAllAssessments;

    public AssessmentViewModel(Application application, int courseID){
        super(application);
        mRepository=new TermTrackerRepo(application);
        mAssociatedAssessments=mRepository.get_associated_assessments(courseID);
    }

    public AssessmentViewModel(Application application){
        super(application);
        mRepository=new TermTrackerRepo(application);
        mAllAssessments=mRepository.get_all_assessments();
        mAssociatedAssessments=mRepository.get_associated_assessments(courseID);
    }

    public LiveData<List<entity_assessment>> get_course_assessments(int courseID){
        return mRepository.get_associated_assessments(courseID);
    }

    public LiveData<List<entity_assessment>> getAllAssessments() { return mAllAssessments; };
    public void insert(entity_assessment entityassessment) { mRepository.insert(entityassessment); }
    public void delete(entity_assessment entityassessment) { mRepository.delete(entityassessment); }
    public int lastID() { return mAllAssessments.getValue().size(); }
}
