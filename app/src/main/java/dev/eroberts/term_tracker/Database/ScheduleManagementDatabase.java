package dev.eroberts.term_tracker.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import dev.eroberts.term_tracker.DAO.dao_assessments;
import dev.eroberts.term_tracker.DAO.dao_courses;
import dev.eroberts.term_tracker.DAO.dao_mentors;
import dev.eroberts.term_tracker.DAO.dao_terms;
import dev.eroberts.term_tracker.Entities.AssessmentEntity;
import dev.eroberts.term_tracker.Entities.CourseEntity;
import dev.eroberts.term_tracker.Entities.MentorEntity;
import dev.eroberts.term_tracker.Entities.TermEntity;

@Database(entities = {AssessmentEntity.class, CourseEntity.class, MentorEntity.class, TermEntity.class}, version = 4, exportSchema = false)

public abstract class ScheduleManagementDatabase extends RoomDatabase {
    public abstract dao_assessments assessmentDAO();
    public abstract dao_courses courseDAO();
    public abstract dao_mentors mentorDAO();
    public abstract dao_terms termDAO();

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
    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final dao_assessments mDaoassessments;
        private final dao_courses mDaocourses;
        private final dao_mentors mDaomentors;
        private final dao_terms mDaoterms;

        PopulateDbAsync(ScheduleManagementDatabase db) {
            mDaoassessments = db.assessmentDAO();
            mDaocourses = db.courseDAO();
            mDaomentors = db.mentorDAO();
            mDaoterms = db.termDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            mDaoassessments.deleteAllAssessments();
            mDaocourses.deleteAllCourses();
            mDaomentors.deleteAllMentors();
            mDaoterms.deleteAllTerms();

        TermEntity term1=new TermEntity(1, "Term 1", "2/1/2020", "8/1/2020");
        mDaoterms.insert(term1);

        CourseEntity course=new CourseEntity(1, "C196 Mobile Testing", "2/1/2020",
                "4/1/2020", "In Progress", "Uses Android Studio", 1);
        mDaocourses.insert(course);

        AssessmentEntity assessment=new AssessmentEntity(1, "C196 PA", "4/1/2020",
                "Performance Assessment", 1);
        mDaoassessments.insert(assessment);

        MentorEntity mentor=new MentorEntity(1, "Natasha Abner", "natasha.abner@wgu.edu",
                "877.435.7948 Ext. 4125", 1);
        mDaomentors.insert(mentor);

            return null;
        }
    }
}
