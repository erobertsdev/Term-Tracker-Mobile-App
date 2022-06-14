package dev.eroberts.term_tracker.DAO;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import dev.eroberts.term_tracker.Entities.AssessmentEntity;

import java.util.List;

@Dao
public interface dao_assessments {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AssessmentEntity assessment);

    @Delete
    void delete(AssessmentEntity assessment);

    @Query("DELETE FROM assessment_table")
    void deleteAllAssessments();

    @Query("SELECT * FROM assessment_table ORDER BY assessmentID ASC")
    LiveData<List<AssessmentEntity>> getAllAssessments();

    @Query ("SELECT * FROM assessment_table WHERE courseID= :courseID ORDER BY assessmentName ASC")
    LiveData<List<AssessmentEntity>> getAllAssociatedAssessments(int courseID);

}

