package dev.eroberts.term_tracker.DAO;
import androidx.room.Query;
import androidx.room.Insert;
import androidx.room.Delete;
import androidx.room.Dao;
import androidx.room.OnConflictStrategy;
import androidx.lifecycle.LiveData;

import dev.eroberts.term_tracker.Entities.entity_course;

import java.util.List;

@Dao public interface dao_courses {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(entity_course course);

    @Delete
    void delete(entity_course course);

    @Query("SELECT * FROM course_table ORDER BY courseID ASC")
    LiveData<List<entity_course>> get_all_courses();

    @Query ("SELECT * FROM course_table WHERE termID= :termID ORDER BY courseName ASC")
    LiveData<List<entity_course>> get_associated_courses(int termID);
}
