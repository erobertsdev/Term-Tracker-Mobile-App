package dev.eroberts.term_tracker.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import dev.eroberts.term_tracker.DAO.dao_assessments;
import dev.eroberts.term_tracker.DAO.dao_courses;
import dev.eroberts.term_tracker.DAO.dao_mentors;
import dev.eroberts.term_tracker.DAO.dao_terms;
import dev.eroberts.term_tracker.Entities.AssessmentEntity;
import dev.eroberts.term_tracker.Entities.CourseEntity;
import dev.eroberts.term_tracker.Entities.MentorEntity;
import dev.eroberts.term_tracker.Entities.TermEntity;

public class ScheduleManagementRepository {
    private final dao_assessments mDaoassessments;
    private final dao_courses mDaocourses;
    private final dao_mentors mDaomentors;
    private final dao_terms mDaoterms;
    private final LiveData<List<AssessmentEntity>> mAllAssessments;
    private final LiveData<List<AssessmentEntity>> mAssociatedAssessments;
    private final LiveData<List<CourseEntity>> mAllCourses;
    private final LiveData<List<CourseEntity>> mAssociatedCourses;
    private final LiveData<List<MentorEntity>> mAllMentors;
    private final LiveData<List<MentorEntity>> mAssociatedMentors;
    private final LiveData<List<TermEntity>> mAllTerms;
    private int courseID;
    private int termID;

    public ScheduleManagementRepository(Application application){
        ScheduleManagementDatabase db=ScheduleManagementDatabase.getDatabase(application);
        mDaoassessments =db.assessmentDAO();
        mAllAssessments= mDaoassessments.getAllAssessments();
        mAssociatedAssessments= mDaoassessments.getAllAssociatedAssessments(courseID);
        mDaocourses =db.courseDAO();
        mAllCourses= mDaocourses.getAllCourses();
        mAssociatedCourses= mDaocourses.getAllAssociatedCourses(termID);
        mDaomentors =db.mentorDAO();
        mAllMentors= mDaomentors.getAllMentors();
        mAssociatedMentors= mDaomentors.getAllAssociatedMentors(courseID);
        mDaoterms =db.termDAO();
        mAllTerms= mDaoterms.getAllTerms();

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
        new insertAsyncTask1(mDaoassessments).execute(assessmentEntity);
    }

    private static class insertAsyncTask1 extends AsyncTask<AssessmentEntity, Void, Void> {

        private final dao_assessments mAsyncTaskDao;

        insertAsyncTask1(dao_assessments dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final AssessmentEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void insert (CourseEntity courseEntity) {
        new insertAsyncTask2(mDaocourses).execute(courseEntity);
    }

    private static class insertAsyncTask2 extends AsyncTask<CourseEntity, Void, Void> {

        private final dao_courses mAsyncTaskDao;

        insertAsyncTask2(dao_courses dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CourseEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void insert (MentorEntity mentorEntity) {
        new insertAsyncTask3(mDaomentors).execute(mentorEntity);
    }

    private static class insertAsyncTask3 extends AsyncTask<MentorEntity, Void, Void> {

        private final dao_mentors mAsyncTaskDao;

        insertAsyncTask3(dao_mentors dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MentorEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void insert (TermEntity termEntity) {
        new insertAsyncTask4(mDaoterms).execute(termEntity);
    }

    private static class insertAsyncTask4 extends AsyncTask<TermEntity, Void, Void> {

        private final dao_terms mAsyncTaskDao;

        insertAsyncTask4(dao_terms dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TermEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void delete (AssessmentEntity assessmentEntity) {
        new deleteAsyncTask1(mDaoassessments).execute(assessmentEntity);
    }

    private static class deleteAsyncTask1 extends AsyncTask<AssessmentEntity, Void, Void> {

        private final dao_assessments mAsyncTaskDao;

        deleteAsyncTask1(dao_assessments dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final AssessmentEntity... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void delete (CourseEntity courseEntity) {
        new deleteAsyncTask2(mDaocourses).execute(courseEntity);
    }

    private static class deleteAsyncTask2 extends AsyncTask<CourseEntity, Void, Void> {

        private final dao_courses mAsyncTaskDao;

        deleteAsyncTask2(dao_courses dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CourseEntity... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void delete (MentorEntity mentorEntity) {
        new deleteAsyncTask3(mDaomentors).execute(mentorEntity);
    }

    private static class deleteAsyncTask3 extends AsyncTask<MentorEntity, Void, Void> {

        private final dao_mentors mAsyncTaskDao;

        deleteAsyncTask3(dao_mentors dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MentorEntity... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void delete (TermEntity termEntity) {
        new deleteAsyncTask4(mDaoterms).execute(termEntity);
    }

    private static class deleteAsyncTask4 extends AsyncTask<TermEntity, Void, Void> {

        private final dao_terms mAsyncTaskDao;

        deleteAsyncTask4(dao_terms dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TermEntity... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
