package dev.eroberts.term_tracker.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import dev.eroberts.term_tracker.Database.TermTrackerRepo;
import dev.eroberts.term_tracker.Entities.entity_course;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    int termID;
    private TermTrackerRepo mRepository;
    private LiveData<List<entity_course>> mAssociatedCourses;
    private LiveData<List<entity_course>> mAllCourses;

    public CourseViewModel(Application application, int termID){
        super(application);
        mRepository=new TermTrackerRepo(application);
        mAssociatedCourses=mRepository.get_associated_courses(termID);
    }

    public CourseViewModel(Application application){
        super(application);
        mRepository=new TermTrackerRepo(application);
        mAllCourses=mRepository.get_all_courses();
        mAssociatedCourses=mRepository.get_associated_courses(termID);
    }

    public LiveData<List<entity_course>> getAssociatedCourses(int termID){
        return mRepository.get_associated_courses(termID);
    }

    public LiveData<List<entity_course>> getAllCourses() { return mAllCourses; };
    public void insert(entity_course entitycourse) { mRepository.insert(entitycourse); }
    public void delete(entity_course entitycourse) { mRepository.delete(entitycourse);}
    public int lastID() { return mAllCourses.getValue().size(); }
}
