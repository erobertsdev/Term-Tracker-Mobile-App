package dev.eroberts.term_tracker.DAO;
import androidx.room.OnConflictStrategy;
import androidx.room.Insert;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Dao;
import androidx.lifecycle.LiveData;


import dev.eroberts.term_tracker.Entities.entity_assessment;
import java.util.List;

@Dao
public interface dao_assessments {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(entity_assessment assessment);

    @Delete
    void delete(entity_assessment assessment);

    @Query("SELECT * FROM assessment_table ORDER BY assessmentID ASC")
    LiveData<List<entity_assessment>> get_all_assessments();

    @Query ("SELECT * FROM assessment_table WHERE courseID= :courseID ORDER BY assessmentName ASC")
    LiveData<List<entity_assessment>> get_course_assessments(int courseID);

}

