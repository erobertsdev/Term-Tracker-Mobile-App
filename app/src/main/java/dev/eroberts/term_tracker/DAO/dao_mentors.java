package dev.eroberts.term_tracker.DAO;
import androidx.room.Insert;
import androidx.room.Delete;
import androidx.lifecycle.LiveData;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Dao;

import dev.eroberts.term_tracker.Entities.entity_mentor;

import java.util.List;

/**
 * The interface Dao mentors.
 */
@Dao
public interface dao_mentors {
    /**
     * Insert.
     *
     * @param mentor the mentor
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(entity_mentor mentor);

    /**
     * Delete.
     *
     * @param mentor the mentor
     */
    @Delete
    void delete(entity_mentor mentor);

    /**
     * Gets all mentors.
     *
     * @return the all mentors
     */
    @Query("SELECT * FROM mentor_table ORDER BY mentorID ASC")
    LiveData<List<entity_mentor>> get_all_mentors();

    /**
     * Gets course mentors.
     *
     * @param courseID the course id
     * @return the course mentors
     */
    @Query ("SELECT * FROM mentor_table WHERE courseID= :courseID ORDER BY mentorName ASC")
    LiveData<List<entity_mentor>> get_course_mentors(int courseID);
}

