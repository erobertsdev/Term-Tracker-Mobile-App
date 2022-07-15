package dev.eroberts.term_tracker.DAO;
import androidx.room.OnConflictStrategy;
import androidx.room.Insert;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Dao;
import androidx.lifecycle.LiveData;


import dev.eroberts.term_tracker.Entities.entity_assessment;
import java.util.List;

/**
 * The interface Dao assessments.
 */
@Dao
public interface dao_assessments {
    /**
     * Insert.
     *
     * @param assessment the assessment
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(entity_assessment assessment);

    /**
     * Delete.
     *
     * @param assessment the assessment
     */
    @Delete
    void delete(entity_assessment assessment);

    /**
     * Gets all assessments.
     *
     * @return the all assessments
     */
    @Query("SELECT * FROM assessment_table ORDER BY assessmentID ASC")
    LiveData<List<entity_assessment>> get_all_assessments();

    /**
     * Gets course assessments.
     *
     * @param courseID the course id
     * @return the course assessments
     */
    @Query ("SELECT * FROM assessment_table WHERE courseID= :courseID ORDER BY assessmentName ASC")
    LiveData<List<entity_assessment>> get_course_assessments(int courseID);

}
