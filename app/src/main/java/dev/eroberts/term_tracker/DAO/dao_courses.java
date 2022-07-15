package dev.eroberts.term_tracker.DAO;
import androidx.room.Query;
import androidx.room.Insert;
import androidx.room.Delete;
import androidx.room.Dao;
import androidx.room.OnConflictStrategy;
import androidx.lifecycle.LiveData;

import dev.eroberts.term_tracker.Entities.entity_course;

import java.util.List;

/**
 * The interface Dao courses.
 */
@Dao public interface dao_courses {
    /**
     * Insert.
     *
     * @param course the course
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(entity_course course);

    /**
     * Delete.
     *
     * @param course the course
     */
    @Delete
    void delete(entity_course course);

    /**
     * Gets all courses.
     *
     * @return the all courses
     */
    @Query("SELECT * FROM course_table ORDER BY courseID ASC")
    LiveData<List<entity_course>> get_all_courses();

    /**
     * Gets associated courses.
     *
     * @param termID the term id
     * @return the associated courses
     */
    @Query ("SELECT * FROM course_table WHERE termID= :termID ORDER BY courseName ASC")
    LiveData<List<entity_course>> get_associated_courses(int termID);
}
