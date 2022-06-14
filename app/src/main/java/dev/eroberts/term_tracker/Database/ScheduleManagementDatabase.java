package dev.eroberts.term_tracker.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import dev.eroberts.term_tracker.DAO.AssessmentsDAO;
import dev.eroberts.term_tracker.DAO.CoursesDAO;
import dev.eroberts.term_tracker.DAO.MentorsDAO;
import dev.eroberts.term_tracker.DAO.TermsDAO;
import dev.eroberts.term_tracker.Entities.AssessmentEntity;
import dev.eroberts.term_tracker.Entities.CourseEntity;
import dev.eroberts.term_tracker.Entities.MentorEntity;
import dev.eroberts.term_tracker.Entities.TermEntity;

@Database(entities = {AssessmentEntity.class, CourseEntity.class, MentorEntity.class, TermEntity.class}, version = 4, exportSchema = false)

public abstract class ScheduleManagementDatabase extends RoomDatabase {
    public abstract AssessmentsDAO assessmentDAO();
    public abstract CoursesDAO courseDAO();
    public abstract MentorsDAO mentorDAO();
    public abstract TermsDAO termDAO();

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

        private final AssessmentsDAO mAssessmentsDao;
        private final CoursesDAO mCoursesDao;
        private final MentorsDAO mMentorsDAO;
        private final TermsDAO mTermsDAO;

        PopulateDbAsync(ScheduleManagementDatabase db) {
            mAssessmentsDao = db.assessmentDAO();
            mCoursesDao = db.courseDAO();
            mMentorsDAO = db.mentorDAO();
            mTermsDAO = db.termDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            mAssessmentsDao.deleteAllAssessments();
            mCoursesDao.deleteAllCourses();
            mMentorsDAO.deleteAllMentors();
            mTermsDAO.deleteAllTerms();

        TermEntity term1=new TermEntity(1, "Term 1", "2/1/2020", "8/1/2020");
        mTermsDAO.insert(term1);

        CourseEntity course=new CourseEntity(1, "C196 Mobile Testing", "2/1/2020",
                "4/1/2020", "In Progress", "Uses Android Studio", 1);
        mCoursesDao.insert(course);

        AssessmentEntity assessment=new AssessmentEntity(1, "C196 PA", "4/1/2020",
                "Performance Assessment", 1);
        mAssessmentsDao.insert(assessment);

        MentorEntity mentor=new MentorEntity(1, "Natasha Abner", "natasha.abner@wgu.edu",
                "877.435.7948 Ext. 4125", 1);
        mMentorsDAO.insert(mentor);

            return null;
        }
    }
}