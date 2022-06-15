package dev.eroberts.term_tracker.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import dev.eroberts.term_tracker.DAO.dao_assessments;
import dev.eroberts.term_tracker.DAO.dao_courses;
import dev.eroberts.term_tracker.DAO.dao_mentors;
import dev.eroberts.term_tracker.DAO.dao_terms;
import dev.eroberts.term_tracker.Entities.entity_assessment;
import dev.eroberts.term_tracker.Entities.entity_course;
import dev.eroberts.term_tracker.Entities.entity_mentor;
import dev.eroberts.term_tracker.Entities.entity_term;

public class TermTrackerRepo {
    private final dao_assessments mDaoassessments;
    private final dao_courses mDaocourses;
    private final dao_mentors mDaomentors;
    private final dao_terms mDaoterms;
    private final LiveData<List<entity_assessment>> mAllAssessments;
    private final LiveData<List<entity_assessment>> mAssociatedAssessments;
    private final LiveData<List<entity_course>> mAllCourses;
    private final LiveData<List<entity_course>> mAssociatedCourses;
    private final LiveData<List<entity_mentor>> mAllMentors;
    private final LiveData<List<entity_mentor>> mAssociatedMentors;
    private final LiveData<List<entity_term>> mAllTerms;
    private int courseID;
    private int termID;

    public TermTrackerRepo(Application application){
        TermTrackerDB db= TermTrackerDB.get_database(application);
        mDaoassessments =db.dao_assessments();
        mAllAssessments= mDaoassessments.get_all_assessments();
        mAssociatedAssessments= mDaoassessments.get_course_assessments(courseID);
        mDaocourses =db.dao_courses();
        mAllCourses= mDaocourses.get_all_courses();
        mAssociatedCourses= mDaocourses.get_associated_courses(termID);
        mDaomentors =db.dao_mentors();
        mAllMentors= mDaomentors.get_all_mentors();
        mAssociatedMentors= mDaomentors.get_course_mentors(courseID);
        mDaoterms =db.dao_terms();
        mAllTerms= mDaoterms.get_all_terms();

    }
    public LiveData<List<entity_assessment>> getAllAssessments(){
        return mAllAssessments;
    }
    public LiveData<List<entity_assessment>> getAssociatedAssessments(int courseID){
        return mAssociatedAssessments;}
    public LiveData<List<entity_course>> getAllCourses(){
        return mAllCourses;
    }
    public LiveData<List<entity_course>> getAssociatedCourses(int termID) { return mAssociatedCourses;}
    public LiveData<List<entity_mentor>> getAllMentors() { return mAllMentors;}
    public LiveData<List<entity_mentor>> getAssociatedMentors(int courseID) { return mAssociatedMentors;}
    public LiveData<List<entity_term>> getAllTerms() { return mAllTerms;}

    public void insert (entity_assessment entityassessment) {
        new insertAsyncTask1(mDaoassessments).execute(entityassessment);
    }

    private static class insertAsyncTask1 extends AsyncTask<entity_assessment, Void, Void> {

        private final dao_assessments mAsyncTaskDao;

        insertAsyncTask1(dao_assessments dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final entity_assessment... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void insert (entity_course entitycourse) {
        new insertAsyncTask2(mDaocourses).execute(entitycourse);
    }

    private static class insertAsyncTask2 extends AsyncTask<entity_course, Void, Void> {

        private final dao_courses mAsyncTaskDao;

        insertAsyncTask2(dao_courses dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final entity_course... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void insert (entity_mentor entitymentor) {
        new insertAsyncTask3(mDaomentors).execute(entitymentor);
    }

    private static class insertAsyncTask3 extends AsyncTask<entity_mentor, Void, Void> {

        private final dao_mentors mAsyncTaskDao;

        insertAsyncTask3(dao_mentors dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final entity_mentor... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void insert (entity_term entityterm) {
        new insertAsyncTask4(mDaoterms).execute(entityterm);
    }

    private static class insertAsyncTask4 extends AsyncTask<entity_term, Void, Void> {

        private final dao_terms mAsyncTaskDao;

        insertAsyncTask4(dao_terms dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final entity_term... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void delete (entity_assessment entityassessment) {
        new deleteAsyncTask1(mDaoassessments).execute(entityassessment);
    }

    private static class deleteAsyncTask1 extends AsyncTask<entity_assessment, Void, Void> {

        private final dao_assessments mAsyncTaskDao;

        deleteAsyncTask1(dao_assessments dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final entity_assessment... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void delete (entity_course entitycourse) {
        new deleteAsyncTask2(mDaocourses).execute(entitycourse);
    }

    private static class deleteAsyncTask2 extends AsyncTask<entity_course, Void, Void> {

        private final dao_courses mAsyncTaskDao;

        deleteAsyncTask2(dao_courses dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final entity_course... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void delete (entity_mentor entitymentor) {
        new deleteAsyncTask3(mDaomentors).execute(entitymentor);
    }

    private static class deleteAsyncTask3 extends AsyncTask<entity_mentor, Void, Void> {

        private final dao_mentors mAsyncTaskDao;

        deleteAsyncTask3(dao_mentors dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final entity_mentor... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void delete (entity_term entityterm) {
        new deleteAsyncTask4(mDaoterms).execute(entityterm);
    }

    private static class deleteAsyncTask4 extends AsyncTask<entity_term, Void, Void> {

        private final dao_terms mAsyncTaskDao;

        deleteAsyncTask4(dao_terms dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final entity_term... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
