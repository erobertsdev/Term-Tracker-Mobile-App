package dev.eroberts.term_tracker.DAO;
import androidx.room.Insert;
import androidx.room.Delete;
import androidx.lifecycle.LiveData;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Dao;

import dev.eroberts.term_tracker.Entities.entity_instructor;

import java.util.List;

/**
 * The interface Dao instructors.
 */
@Dao
public interface dao_instructors {
    /**
     * Insert.
     *
     * @param instructor the instructor
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(entity_instructor instructor);

    /**
     * Delete.
     *
     * @param instructor the instructor
     */
    @Delete
    void delete(entity_instructor instructor);

    /**
     * Gets all mentors.
     *
     * @return the all mentors
     */
    @Query("SELECT * FROM mentor_table ORDER BY mentorID ASC")
    LiveData<List<entity_instructor>> get_all_instructors();

    /**
     * Gets course mentors.
     *
     * @param courseID the course id
     * @return the course mentors
     */
    @Query ("SELECT * FROM mentor_table WHERE courseID= :courseID ORDER BY mentorName ASC")
    LiveData<List<entity_instructor>> get_course_mentors(int courseID);
}

