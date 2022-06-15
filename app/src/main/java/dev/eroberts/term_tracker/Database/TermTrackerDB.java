package dev.eroberts.term_tracker.Database;
import android.content.Context;
import android.os.AsyncTask;
import dev.eroberts.term_tracker.DAO.dao_assessments;
import dev.eroberts.term_tracker.DAO.dao_courses;
import dev.eroberts.term_tracker.DAO.dao_mentors;
import dev.eroberts.term_tracker.DAO.dao_terms;
import dev.eroberts.term_tracker.Entities.entity_assessment;
import dev.eroberts.term_tracker.Entities.entity_course;
import dev.eroberts.term_tracker.Entities.entity_mentor;
import dev.eroberts.term_tracker.Entities.entity_term;
import androidx.room.Room;
import androidx.annotation.NonNull;
import androidx.room.RoomDatabase;
import androidx.room.Database;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * The type Term tracker db.
 */
@Database(entities = {entity_assessment.class, entity_course.class, entity_mentor.class, entity_term.class}, version = 4, exportSchema = false)

public abstract class TermTrackerDB extends RoomDatabase {
    /**
     * Dao assessments dao assessments.
     *
     * @return the dao assessments
     */
    public abstract dao_assessments dao_assessments();

    /**
     * Dao courses dao courses.
     *
     * @return the dao courses
     */
    public abstract dao_courses dao_courses();

    /**
     * Dao mentors dao mentors.
     *
     * @return the dao mentors
     */
    public abstract dao_mentors dao_mentors();

    /**
     * Dao terms dao terms.
     *
     * @return the dao terms
     */
    public abstract dao_terms dao_terms();

    private static volatile TermTrackerDB tdb;

    /**
     * Gets database.
     *
     * @param context the context
     * @return the database
     */
    static TermTrackerDB get_database(final Context context) {
        if (tdb == null) {
            synchronized (TermTrackerDB.class) {
                if (tdb == null) {
                    tdb = Room.databaseBuilder(context.getApplicationContext(), TermTrackerDB.class, "schedule_management_database").fallbackToDestructiveMigration().addCallback(S_ROOM_DATABASE_CALLBACK).build();
                }
            }
        }
        return tdb;
    }
    private static final RoomDatabase.Callback S_ROOM_DATABASE_CALLBACK = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new DB_Fill(tdb).execute();
        }
    };

    private static class DB_Fill extends AsyncTask<Void, Void, Void> {

        /**
         * Instantiates a new Db fill.
         *
         * @param db the db
         */
        DB_Fill(TermTrackerDB db) {
            db.dao_assessments();
            db.dao_courses();
            db.dao_mentors();
            db.dao_terms();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            return null;
        }
    }
}
