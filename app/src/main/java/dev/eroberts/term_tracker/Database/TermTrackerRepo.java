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

/**
 * The type Term tracker repo.
 */
public class TermTrackerRepo {
    private final dao_assessments dao_assessments_e;
    private final dao_courses dao_courses_e;
    private final dao_mentors dao_mentors_e;
    private final dao_terms dao_terms_e;
    private final LiveData<List<entity_assessment>> all_assessments_e;
    private final LiveData<List<entity_assessment>> associated_assessments_e;
    private final LiveData<List<entity_course>> all_courses_e;
    private final LiveData<List<entity_course>> associated_courses_e;
    private final LiveData<List<entity_mentor>> all_mentors_e;
    private final LiveData<List<entity_mentor>> associated_mentors_e;
    private final LiveData<List<entity_term>> all_terms_e;
    private int course_id;
    private int term_id;

    /**
     * Instantiates a new Term tracker repo.
     *
     * @param application the application
     */
    public TermTrackerRepo(Application application){
        TermTrackerDB db = TermTrackerDB.get_database(application);
        dao_assessments_e = db.dao_assessments();
        all_assessments_e = dao_assessments_e.get_all_assessments();
        associated_assessments_e = dao_assessments_e.get_course_assessments(course_id);
        dao_courses_e = db.dao_courses();
        all_courses_e = dao_courses_e.get_all_courses();
        associated_courses_e = dao_courses_e.get_associated_courses(term_id);
        dao_mentors_e = db.dao_mentors();
        all_mentors_e = dao_mentors_e.get_all_mentors();
        associated_mentors_e = dao_mentors_e.get_course_mentors(course_id);
        dao_terms_e = db.dao_terms();
        all_terms_e = dao_terms_e.get_all_terms();

    }

    /**
     * Get all assessments live data.
     *
     * @return the live data
     */
    public LiveData<List<entity_assessment>> get_all_assessments(){
        return all_assessments_e;
    }

    /**
     * Get associated assessments live data.
     *
     * @param course_id the course id
     * @return the live data
     */
    public LiveData<List<entity_assessment>> get_associated_assessments(int course_id){
        return associated_assessments_e;}

    /**
     * Get all courses live data.
     *
     * @return the live data
     */
    public LiveData<List<entity_course>> get_all_courses(){
        return all_courses_e;
    }

    /**
     * Gets associated courses.
     *
     * @param term_id the term id
     * @return the associated courses
     */
    public LiveData<List<entity_course>> get_associated_courses(int term_id) { return associated_courses_e;}

    /**
     * Gets all mentors.
     *
     * @return the all mentors
     */
    public LiveData<List<entity_mentor>> get_all_mentors() { return all_mentors_e;}

    /**
     * Gets associated mentors.
     *
     * @param course_id the course id
     * @return the associated mentors
     */
    public LiveData<List<entity_mentor>> get_associated_mentors(int course_id) { return associated_mentors_e;}

    /**
     * Gets all terms.
     *
     * @return the all terms
     */
    public LiveData<List<entity_term>> get_all_terms() { return all_terms_e;}

    /**
     * Insert.
     *
     * @param entityassessment the entityassessment
     */
    public void insert (entity_assessment entityassessment) {
        new insert_async_task_1(dao_assessments_e).execute(entityassessment);
    }

    private static class insert_async_task_1 extends AsyncTask<entity_assessment, Void, Void> {

        private final dao_assessments async_task_dao_e;

        /**
         * Instantiates a new Insert async task 1.
         *
         * @param dao the dao
         */
        insert_async_task_1(dao_assessments dao) {
            async_task_dao_e = dao;
        }

        @Override
        protected Void doInBackground(final entity_assessment... params) {
            async_task_dao_e.insert(params[0]);
            return null;
        }
    }

    /**
     * Insert.
     *
     * @param entitycourse the entitycourse
     */
    public void insert (entity_course entitycourse) {
        new insert_async_task_2(dao_courses_e).execute(entitycourse);
    }

    private static class insert_async_task_2 extends AsyncTask<entity_course, Void, Void> {

        private final dao_courses async_task_dao_e;

        /**
         * Instantiates a new Insert async task 2.
         *
         * @param dao the dao
         */
        insert_async_task_2(dao_courses dao) {
            async_task_dao_e = dao;
        }

        @Override
        protected Void doInBackground(final entity_course... params) {
            async_task_dao_e.insert(params[0]);
            return null;
        }
    }

    /**
     * Insert.
     *
     * @param entitymentor the entitymentor
     */
    public void insert (entity_mentor entitymentor) {
        new insert_async_task_3(dao_mentors_e).execute(entitymentor);
    }

    private static class insert_async_task_3 extends AsyncTask<entity_mentor, Void, Void> {

        private final dao_mentors async_task_dao_e;

        /**
         * Instantiates a new Insert async task 3.
         *
         * @param dao the dao
         */
        insert_async_task_3(dao_mentors dao) {
            async_task_dao_e = dao;
        }

        @Override
        protected Void doInBackground(final entity_mentor... params) {
            async_task_dao_e.insert(params[0]);
            return null;
        }
    }

    /**
     * Insert.
     *
     * @param entityterm the entityterm
     */
    public void insert (entity_term entityterm) {
        new insert_async_task_4(dao_terms_e).execute(entityterm);
    }

    private static class insert_async_task_4 extends AsyncTask<entity_term, Void, Void> {

        private final dao_terms async_task_dao_e;

        /**
         * Instantiates a new Insert async task 4.
         *
         * @param dao the dao
         */
        insert_async_task_4(dao_terms dao) {
            async_task_dao_e = dao;
        }

        @Override
        protected Void doInBackground(final entity_term... params) {
            async_task_dao_e.insert(params[0]);
            return null;
        }
    }

    /**
     * Delete.
     *
     * @param entityassessment the entityassessment
     */
    public void delete (entity_assessment entityassessment) {
        new delete_async_task_1(dao_assessments_e).execute(entityassessment);
    }

    private static class delete_async_task_1 extends AsyncTask<entity_assessment, Void, Void> {

        private final dao_assessments async_task_dao_e;

        /**
         * Instantiates a new Delete async task 1.
         *
         * @param dao the dao
         */
        delete_async_task_1(dao_assessments dao) {
            async_task_dao_e = dao;
        }

        @Override
        protected Void doInBackground(final entity_assessment... params) {
            async_task_dao_e.delete(params[0]);
            return null;
        }
    }

    /**
     * Delete.
     *
     * @param entitycourse the entitycourse
     */
    public void delete (entity_course entitycourse) {
        new delete_async_task_2(dao_courses_e).execute(entitycourse);
    }

    private static class delete_async_task_2 extends AsyncTask<entity_course, Void, Void> {

        private final dao_courses async_task_dao_e;

        /**
         * Instantiates a new Delete async task 2.
         *
         * @param dao the dao
         */
        delete_async_task_2(dao_courses dao) {
            async_task_dao_e = dao;
        }

        @Override
        protected Void doInBackground(final entity_course... params) {
            async_task_dao_e.delete(params[0]);
            return null;
        }
    }

    /**
     * Delete.
     *
     * @param entitymentor the entitymentor
     */
    public void delete (entity_mentor entitymentor) {
        new delete_async_task_3(dao_mentors_e).execute(entitymentor);
    }

    private static class delete_async_task_3 extends AsyncTask<entity_mentor, Void, Void> {

        private final dao_mentors async_task_dao_e;

        /**
         * Instantiates a new Delete async task 3.
         *
         * @param dao the dao
         */
        delete_async_task_3(dao_mentors dao) {
            async_task_dao_e = dao;
        }

        @Override
        protected Void doInBackground(final entity_mentor... params) {
            async_task_dao_e.delete(params[0]);
            return null;
        }
    }

    /**
     * Delete.
     *
     * @param entityterm the entityterm
     */
    public void delete (entity_term entityterm) {
        new delete_async_task_4(dao_terms_e).execute(entityterm);
    }

    private static class delete_async_task_4 extends AsyncTask<entity_term, Void, Void> {

        private final dao_terms async_task_dao_e;

        /**
         * Instantiates a new Delete async task 4.
         *
         * @param dao the dao
         */
        delete_async_task_4(dao_terms dao) {
            async_task_dao_e = dao;
        }

        @Override
        protected Void doInBackground(final entity_term... params) {
            async_task_dao_e.delete(params[0]);
            return null;
        }
    }
}
