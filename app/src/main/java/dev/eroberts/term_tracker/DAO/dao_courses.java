package dev.eroberts.term_tracker.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import dev.eroberts.term_tracker.Entities.entity_course;

import java.util.List;

@Dao
public interface dao_courses {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(entity_course course);

    @Delete
    void delete(entity_course course);

    @Query("DELETE FROM course_table")
    void deleteAllCourses();

    @Query("SELECT * FROM course_table ORDER BY courseID ASC")
    LiveData<List<entity_course>> getAllCourses();

    @Query ("SELECT * FROM course_table WHERE termID= :termID ORDER BY courseName ASC")
    LiveData<List<entity_course>> getAllAssociatedCourses(int termID);
}
