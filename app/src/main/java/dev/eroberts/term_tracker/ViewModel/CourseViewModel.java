package dev.eroberts.term_tracker.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import dev.eroberts.term_tracker.Database.ScheduleManagementRepository;
import dev.eroberts.term_tracker.Entities.entity_course;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    int termID;
    private ScheduleManagementRepository mRepository;
    private LiveData<List<entity_course>> mAssociatedCourses;
    private LiveData<List<entity_course>> mAllCourses;

    public CourseViewModel(Application application, int termID){
        super(application);
        mRepository=new ScheduleManagementRepository(application);
        mAssociatedCourses=mRepository.getAssociatedCourses(termID);
    }

    public CourseViewModel(Application application){
        super(application);
        mRepository=new ScheduleManagementRepository(application);
        mAllCourses=mRepository.getAllCourses();
        mAssociatedCourses=mRepository.getAssociatedCourses(termID);
    }

    public LiveData<List<entity_course>> getAssociatedCourses(int termID){
        return mRepository.getAssociatedCourses(termID);
    }

    public LiveData<List<entity_course>> getAllCourses() { return mAllCourses; };
    public void insert(entity_course entitycourse) { mRepository.insert(entitycourse); }
    public void delete(entity_course entitycourse) { mRepository.delete(entitycourse);}
    public int lastID() { return mAllCourses.getValue().size(); }
}
