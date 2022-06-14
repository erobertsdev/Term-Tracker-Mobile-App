package com.example.c196_degree_tracker.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.c196_degree_tracker.DAO.*;
import com.example.c196_degree_tracker.Entities.*;

@Database(entities = {AssessmentEntity.class,CourseEntity.class, MentorEntity.class, TermEntity.class}, version = 4, exportSchema = false)

public abstract class ScheduleManagementDatabase extends RoomDatabase {
    public abstract AssessmentDAO assessmentDAO();
    public abstract CourseDAO courseDAO();
    public abstract MentorDAO mentorDAO();
    public abstract TermDAO termDAO();

    private static volatile ScheduleManagementDatabase INSTANCE;

    static ScheduleManagementDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ScheduleManagementDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ScheduleManagementDatabase.class, "schedule_management_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AssessmentDAO mAssessmentDao;
        private final CourseDAO mCourseDao;
        private final MentorDAO mMentorDAO;
        private final TermDAO mTermDAO;

        PopulateDbAsync(ScheduleManagementDatabase db) {
            mAssessmentDao = db.assessmentDAO();
            mCourseDao = db.courseDAO();
            mMentorDAO = db.mentorDAO();
            mTermDAO = db.termDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            mAssessmentDao.deleteAllAssessments();
            mCourseDao.deleteAllCourses();
            mMentorDAO.deleteAllMentors();
            mTermDAO.deleteAllTerms();

        TermEntity term1=new TermEntity(1, "Term 1", "2/1/2020", "8/1/2020");
        mTermDAO.insert(term1);

        CourseEntity course=new CourseEntity(1, "C196 Mobile Testing", "2/1/2020",
                "4/1/2020", "In Progress", "Uses Android Studio", 1);
        mCourseDao.insert(course);

        AssessmentEntity assessment=new AssessmentEntity(1, "C196 PA", "4/1/2020",
                "Performance Assessment", 1);
        mAssessmentDao.insert(assessment);

        MentorEntity mentor=new MentorEntity(1, "Natasha Abner", "natasha.abner@wgu.edu",
                "877.435.7948 Ext. 4125", 1);
        mMentorDAO.insert(mentor);

            return null;
        }
    }
}
