package com.example.c196_degree_tracker.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.c196_degree_tracker.Database.ScheduleManagementRepository;
import com.example.c196_degree_tracker.Entities.CourseEntity;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    int termID;
    private ScheduleManagementRepository mRepository;
    private LiveData<List<CourseEntity>> mAssociatedCourses;
    private LiveData<List<CourseEntity>> mAllCourses;

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

    public LiveData<List<CourseEntity>> getAssociatedCourses(int termID){
        return mRepository.getAssociatedCourses(termID);
    }

    public LiveData<List<CourseEntity>> getAllCourses() { return mAllCourses; };
    public void insert(CourseEntity courseEntity) { mRepository.insert(courseEntity); }
    public void delete(CourseEntity courseEntity) { mRepository.delete(courseEntity);}
    public int lastID() { return mAllCourses.getValue().size(); }
}
