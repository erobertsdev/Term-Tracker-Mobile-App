package dev.eroberts.term_tracker.DAO;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import dev.eroberts.term_tracker.Entities.entity_assessment;

import java.util.List;

@Dao
public interface dao_assessments {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(entity_assessment assessment);

    @Delete
    void delete(entity_assessment assessment);

    @Query("DELETE FROM assessment_table")
    void deleteAllAssessments();

    @Query("SELECT * FROM assessment_table ORDER BY assessmentID ASC")
    LiveData<List<entity_assessment>> getAllAssessments();

    @Query ("SELECT * FROM assessment_table WHERE courseID= :courseID ORDER BY assessmentName ASC")
    LiveData<List<entity_assessment>> getAllAssociatedAssessments(int courseID);

}

