package com.example.c196_degree_tracker.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.c196_degree_tracker.DAO.*;
import com.example.c196_degree_tracker.Entities.*;

import java.util.List;

public class ScheduleManagementRepository {
    private AssessmentDAO mAssessmentDAO;
    private CourseDAO mCourseDAO;
    private MentorDAO mMentorDAO;
    private TermDAO mTermDAO;
    private LiveData<List<AssessmentEntity>> mAllAssessments;
    private LiveData<List<AssessmentEntity>> mAssociatedAssessments;
    private LiveData<List<CourseEntity>> mAllCourses;
    private LiveData<List<CourseEntity>> mAssociatedCourses;
    private LiveData<List<MentorEntity>> mAllMentors;
    private LiveData<List<MentorEntity>> mAssociatedMentors;
    private LiveData<List<TermEntity>> mAllTerms;
    private int courseID;
    private int termID;

    public ScheduleManagementRepository(Application application){
        ScheduleManagementDatabase db=ScheduleManagementDatabase.getDatabase(application);
        mAssessmentDAO=db.assessmentDAO();
        mAllAssessments=mAssessmentDAO.getAllAssessments();
        mAssociatedAssessments=mAssessmentDAO.getAllAssociatedAssessments(courseID);
        mCourseDAO=db.courseDAO();
        mAllCourses=mCourseDAO.getAllCourses();
        mAssociatedCourses=mCourseDAO.getAllAssociatedCourses(termID);
        mMentorDAO=db.mentorDAO();
        mAllMentors=mMentorDAO.getAllMentors();
        mAssociatedMentors=mMentorDAO.getAllAssociatedMentors(courseID);
        mTermDAO=db.termDAO();
        mAllTerms=mTermDAO.getAllTerms();

    }
    public LiveData<List<AssessmentEntity>> getAllAssessments(){
        return mAllAssessments;
    }
    public LiveData<List<AssessmentEntity>> getAssociatedAssessments(int courseID){
        return mAssociatedAssessments;}
    public LiveData<List<CourseEntity>> getAllCourses(){
        return mAllCourses;
    }
    public LiveData<List<CourseEntity>> getAssociatedCourses(int termID) { return mAssociatedCourses;}
    public LiveData<List<MentorEntity>> getAllMentors() { return mAllMentors;}
    public LiveData<List<MentorEntity>> getAssociatedMentors(int courseID) { return mAssociatedMentors;}
    public LiveData<List<TermEntity>> getAllTerms() { return mAllTerms;}

    public void insert (AssessmentEntity assessmentEntity) {
        new insertAsyncTask1(mAssessmentDAO).execute(assessmentEntity);
    }

    private static class insertAsyncTask1 extends AsyncTask<AssessmentEntity, Void, Void> {

        private AssessmentDAO mAsyncTaskDao;

        insertAsyncTask1(AssessmentDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final AssessmentEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void insert (CourseEntity courseEntity) {
        new insertAsyncTask2(mCourseDAO).execute(courseEntity);
    }

    private static class insertAsyncTask2 extends AsyncTask<CourseEntity, Void, Void> {

        private CourseDAO mAsyncTaskDao;

        insertAsyncTask2(CourseDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CourseEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void insert (MentorEntity mentorEntity) {
        new insertAsyncTask3(mMentorDAO).execute(mentorEntity);
    }

    private static class insertAsyncTask3 extends AsyncTask<MentorEntity, Void, Void> {

        private MentorDAO mAsyncTaskDao;

        insertAsyncTask3(MentorDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MentorEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void insert (TermEntity termEntity) {
        new insertAsyncTask4(mTermDAO).execute(termEntity);
    }

    private static class insertAsyncTask4 extends AsyncTask<TermEntity, Void, Void> {

        private TermDAO mAsyncTaskDao;

        insertAsyncTask4(TermDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TermEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void delete (AssessmentEntity assessmentEntity) {
        new deleteAsyncTask1(mAssessmentDAO).execute(assessmentEntity);
    }

    private static class deleteAsyncTask1 extends AsyncTask<AssessmentEntity, Void, Void> {

        private AssessmentDAO mAsyncTaskDao;

        deleteAsyncTask1(AssessmentDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final AssessmentEntity... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void delete (CourseEntity courseEntity) {
        new deleteAsyncTask2(mCourseDAO).execute(courseEntity);
    }

    private static class deleteAsyncTask2 extends AsyncTask<CourseEntity, Void, Void> {

        private CourseDAO mAsyncTaskDao;

        deleteAsyncTask2(CourseDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CourseEntity... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void delete (MentorEntity mentorEntity) {
        new deleteAsyncTask3(mMentorDAO).execute(mentorEntity);
    }

    private static class deleteAsyncTask3 extends AsyncTask<MentorEntity, Void, Void> {

        private MentorDAO mAsyncTaskDao;

        deleteAsyncTask3(MentorDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MentorEntity... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void delete (TermEntity termEntity) {
        new deleteAsyncTask4(mTermDAO).execute(termEntity);
    }

    private static class deleteAsyncTask4 extends AsyncTask<TermEntity, Void, Void> {

        private TermDAO mAsyncTaskDao;

        deleteAsyncTask4(TermDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TermEntity... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
